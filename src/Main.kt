import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var masterPokemonList: List<List<PokemonClass>> = listOf(listOf(PokemonClass("MissingNo.","Bird",0)))
    try{
        masterPokemonList = PokemonDataGenerator().generatePokemonFromCSV()
    }
    catch(e:Exception){
        println("ERROR: Failed to load CSV data")
    }
    finally{
        println("Starting game...")
    }
    val endGame = false
    var trainerData = TrainerList<TrainerClass>()
    var currentTrainer = TrainerClass("DEFAULT")
    while (!endGame){
        println("+---Pokemon Simulator---+")
        println("Select Game Mode")
        println("0 - Quick Battle")
        println("1 - Custom Battle")
        println("2 - Battle Speed")
        println("3 - Stress Test")
        println("4 - Multiplayer")
        val choice: String = readLine() ?: "0"
        when (choice.toInt()){
            0 -> quickBattle(TrainerClass("QUICKPLAY",false))
            1 -> {
            if (trainerData.isEmpty() || currentTrainer.trainerName == "DEFAULT"){
                println("You need to make a trainer first...")
            }else{
                quickBattle(currentTrainer)
            }
        }
            2 -> {
                println("Battle Speed:")
                println("0 - Normal")
                println("1 - Fast (Default)")
                println("2 - Instant")
                val choice = readln().toInt()
                when (choice){
                    0 -> battleSpeed = 500
                    1 -> battleSpeed = 250
                    2 -> battleSpeed = 0
                    else -> battleSpeed = 500
                }
            }
            3 -> stressTest(readln().toInt())
            4 -> {
                println("Player 1 Name: ")
                val trainerName = readln()
                println("Player 2 Name: ")
                val trainerName2 = readln()
                multiplayer(TrainerClass(trainerName,false),TrainerClass(trainerName2,false))
            }
            5 -> {
                PokemonDataGenerator().generatePokemonFromCSV()
                }
            6->{
                println("Appraise from master List")
                var trainer = TrainerClass("TEST", false)
                trainer.currentPokemon.addToParty(masterPokemonList[0][0])
                trainer.currentPokemon.addToParty(masterPokemonList[0][40])
                trainer.appraisePokemon(0)
                trainer.appraisePokemon(1)

            }
            7 -> {
                println("Test Case")
                var trainer = TrainerClass("TEST", false)
                trainer = PokemonDataGenerator().generatePokemon(trainer,6,4,false)
                var x = 0
                while (x < trainer.currentPokemon.size()){
                    trainer.appraisePokemon(x)
                    x++
                }
            }
            }
        }
    }


fun multiplayer(trainer1:TrainerClass, trainer2:TrainerClass){
    println("+---MULTIPLAYER BATTLE---+")
    println("${trainer1.trainerName} vs. ${trainer2.trainerName}")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    PokemonDataGenerator().generatePokemon(trainer1,opponentNum,4,true)
    PokemonDataGenerator().generatePokemon(trainer2,opponentNum,4,true)
    println("${trainer1.trainerName} has: ")
    trainer1.listPokemon()
    Thread.sleep(5000)
    println("${trainer2.trainerName} has: ")
    trainer2.listPokemon()
    Thread.sleep(5000)
    val battleHandler = BattleHandler()
    battleHandler.battleMain(trainer1,trainer2)
    println("+---MULTIPLAYER BATTLE END---+")
    return
}
fun inferenceOak(trainer1:TrainerClass,trainer2:TrainerClass){
    //Prof. Oak inference
    val infTrainer1 = trainer1
    val infTrainer2 = trainer2
    infTrainer1.setAIFlag(true)
    infTrainer2.setAIFlag(true)
    val orgBattleSpeed = battleSpeed
    val orgShowMessage = showMessage
    battleSpeed = 0
    showMessage = false
    val infBattleStats = BattleHandler().battleMain(infTrainer1,infTrainer2)
    battleSpeed = orgBattleSpeed
    showMessage = orgShowMessage
    when{
        infBattleStats.playerWin -> println("PROF OAK: ${infTrainer1.trainerName} is likely to win...")
        infBattleStats.opponentWin -> println("PROF OAK: ${infTrainer2.trainerName} is likely to win...")
    }
    println("Trainer 1 has ${trainer1.currentPokemon.size()}, ${infTrainer1.currentPokemon.size()}")
}
fun quickBattle(trainer: TrainerClass){
    println("+---QUICK BATTLE---+")
    println("${trainer.trainerName} vs. Rival")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    val rival = TrainerClass("Rival", true)
    PokemonDataGenerator().generatePokemon(trainer,6,4,true)
    PokemonDataGenerator().generatePokemon(rival,6,4,true)
    println("Rival has: ")
    rival.listPokemon()
    Thread.sleep(5000)
    val battleHandler = BattleHandler()
    battleHandler.battleMain(trainer,rival)
    println("+---QUICK BATTLE END---+")
    return
}
fun stressTest(turnCounts:Int){
    battleSpeed = 0
    val startMillis = System.currentTimeMillis()
    println("+-------------+")
    println("+---STRESS TEST---+")
    println("+-------------+")
    var winStat1 = 0
    var winStat2 = 0
    var winRandStat1 = 0
    var winRandStat2 = 0
    val totalTurnCount = mutableListOf<Int>()
    val totalRandTurnCount = mutableListOf<Int>()
    var count = 0
    //Fixed 6 Pokemon
    while (count < turnCounts){
        println("+-------EPOCH $count------+")
        val trainer1 = TrainerClass("TEST1", true)
        val trainer2 = TrainerClass("TEST2", true)
        PokemonDataGenerator().generatePokemon(trainer1,6,4,false)
        PokemonDataGenerator().generatePokemon(trainer2,6,4,false)
        trainer1.listPokemon()
        trainer2.listPokemon()
        val battleHandler = BattleHandler()
        val battleStats = battleHandler.battleMain(trainer1,trainer2)
        when(battleStats.playerWin){
            true -> winStat1++
            false -> winStat2++
        }
        totalTurnCount.add(battleStats.totalTurns)
        count++
    }
    //Rand 6 Pokemon
    count = 0
    while (count < turnCounts){
        println("+-------EPOCH $count------+")
        val trainer1 = TrainerClass("TEST1", true)
        val trainer2 = TrainerClass("TEST2", true)
        val randomPokemonAmount = (1..6).random()
        PokemonDataGenerator().generatePokemon(trainer1,randomPokemonAmount,4,false)
        PokemonDataGenerator().generatePokemon(trainer2,randomPokemonAmount,4,false)
        trainer1.listPokemon()
        trainer2.listPokemon()
        val battleHandler = BattleHandler()
        val battleStats = battleHandler.battleMain(trainer1,trainer2)
        when(battleStats.playerWin){
            true -> winRandStat1++
            false -> winRandStat2++
        }
        totalRandTurnCount.add(battleStats.totalTurns)
        count++
    }
    val endMillis = System.currentTimeMillis()
    println("+----+")
    println("Battle Stats (Fixed):")
    println("Player 1 Wins: $winStat1")
    println("Player 2 Wins: $winStat2")
    var ratio: Float = winStat1.toFloat()/winStat2.toFloat()
    println("Ratio: ${ratio}")
    println("Average Turn Count: ${totalTurnCount.average()}")
    println("Min Turn Count: ${totalTurnCount.min()}")
    println("Max Turn Count: ${totalTurnCount.max()}")
    println("+----+")
    println("Battle Stats (Random # Pokemon):")
    println("Player 1 Wins: $winRandStat1")
    println("Player 2 Wins: $winRandStat2")
    var ratio2: Float = winRandStat1.toFloat()/winRandStat2.toFloat()
    println("Ratio: ${ratio2}")
    println("Average Turn Count: ${totalRandTurnCount.average()}")
    println("Min Turn Count: ${totalRandTurnCount.min()}")
    println("Max Turn Count: ${totalRandTurnCount.max()}")
    println("+----+")
    println("Execution Time: ${endMillis-startMillis}")
    battleSpeed = 1000
}

class TrainerList<TrainerClass>(vararg trainer: TrainerClass){

    private val elements = trainer.toMutableList()
    fun addToParty(element: TrainerClass){
            elements.add(element)
    }
    fun loadTrainer(x: Int) = elements[x]

    fun peek(): TrainerClass = elements.last()

    fun removeTrainer(x: Int): TrainerClass = elements.removeAt(x)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString() = "MutableStack(${elements.joinToString()})"
}



