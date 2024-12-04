import com.google.gson.Gson
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun main() {

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
        println("5 - Serialization Test")
        println("6 - Appraise Test")
        println("7 - Appraise Test2")
        println("8 - UI Bar Test")
        val choice: String = readLine() ?: "0"
        when (choice.toInt()){
            0 -> quickBattle(TrainerClass("QUICKPLAY",false),true)
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
                println("Play with official Pokemon Stats?")
                val choice = readln()
                when(choice){
                    "Y" -> multiplayer(TrainerClass(trainerName,false),TrainerClass(trainerName2,false),true)
                    else -> multiplayer(TrainerClass(trainerName,false),TrainerClass(trainerName2,false),false)
                }

            }
            5 -> {
                println("Serialization Test")
                var trainer = TrainerClass("CompressMe", false)
                trainer = PokemonDataGenerator().generatePokemon(trainer,6,4,true,true)
                trainer.listPokemon()
                val base64 = Base64.encode(trainer.serializeToJSON().toByteArray())
                println("${base64}")
                println("base64 charset has ${base64.length} characters")
                println("Compression Test")
                val gzipped = gzip(base64)
                println(gzipped.toString())
                println("Commpressed version has ${gzipped.size}")
                println("Verify unzipped is equal to original")
                val ungzipped = ungzip(gzipped)
                if(ungzipped.toString() == base64){println("It's the same!")}
                println("deseralize back to TrainerClass")
                val decoded = String(Base64.decode(ungzipped))
                println("BASE64 DECODE OK")
                val deserTrainer = Gson().fromJson(decoded, TrainerClass::class.java)
                println("${deserTrainer.trainerName} has been decoded!")
                deserTrainer.listPokemon()
                }
            6->{
                val trainer1 = TrainerClass("TEST2", true)
                PokemonDataGenerator().generatePokemon(trainer1,6,4,false,true)
                trainer1.appraisePokemon(0)
                trainer1.appraisePokemon(1)
                trainer1.appraisePokemon(2)
                trainer1.appraisePokemon(3)
                trainer1.appraisePokemon(4)
                trainer1.appraisePokemon(5)


            }
            7 -> {
                println("Test Case")
                var trainer = TrainerClass("TEST", false)
                trainer = PokemonDataGenerator().generatePokemon(trainer,6,4,false,true)
                var x = 0
                while (x < trainer.currentPokemon.size()){
                    trainer.appraisePokemon(x)
                    x++
                }
            }
            8 -> {
                var x = 0
                while (x < 500){
                    InputHandler().proportionalBarPrinter(x,500)
                    println("$x / 500")
                    Thread.sleep(5)
                    x++
                }
                while (x > 0){
                    InputHandler().proportionalBarPrinter(x,500)
                    println("$x / 500")
                    Thread.sleep(5)
                    x--
                }
                while (x < 6){
                    InputHandler().pokemonBarPrinter(x,6)
                    Thread.sleep(5)
                    x++
                }
                while (x > 0){
                    InputHandler().pokemonBarPrinter(x,6)
                    Thread.sleep(5)
                    x--
                }
            }
            }
        }
    }


fun multiplayer(trainer1:TrainerClass, trainer2:TrainerClass, gameMode:Boolean = true){
    println("+---MULTIPLAYER BATTLE---+")
    println("${trainer1.trainerName} vs. ${trainer2.trainerName}")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    PokemonDataGenerator().generatePokemon(trainer1,opponentNum,4,true,gameMode)
    PokemonDataGenerator().generatePokemon(trainer2,opponentNum,4,true,gameMode)
    println("${trainer1.trainerName} has: ")
    trainer1.listPokemon()
    Thread.sleep(5000)
    println("${trainer2.trainerName} has: ")
    trainer2.listPokemon()
    inferenceOak(trainer1,trainer2)
    Thread.sleep(5000)
    val battleHandler = BattleHandler()
    battleHandler.battleMain(trainer1,trainer2)
    println("+---MULTIPLAYER BATTLE END---+")
    return
}
fun inferenceOak(trainer1:TrainerClass,trainer2:TrainerClass){
    //Prof. Oak inference
    val infTrainer1 = trainer1.deepCopy()
    val infTrainer2 = trainer2.deepCopy()
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
        infBattleStats.playerWin -> println("PROF OAK: You're foe's weak! Get'm ${infTrainer2.trainerName}!")
        infBattleStats.opponentWin -> println("PROF OAK: PROF OAK: You're foe's weak! Get'm ${infTrainer1.trainerName}!")
    }
}
fun quickBattle(trainer: TrainerClass, gameMode:Boolean = true){
    println("+---QUICK BATTLE---+")
    println("${trainer.trainerName} vs. Rival")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    val rival = TrainerClass("Rival", true)
    PokemonDataGenerator().generatePokemon(trainer,opponentNum,4,true,gameMode)
    PokemonDataGenerator().generatePokemon(rival,opponentNum,4,true,gameMode)
    println("Rival has: ")
    rival.listPokemon()
    inferenceOak(trainer,rival)
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
        PokemonDataGenerator().generatePokemon(trainer1,6,4,false,true)
        PokemonDataGenerator().generatePokemon(trainer2,6,4,false,true)
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
        val randomPokemonAmount = 6
        PokemonDataGenerator().generatePokemon(trainer1,randomPokemonAmount,4,false,false)
        PokemonDataGenerator().generatePokemon(trainer2,randomPokemonAmount,4,false,false)
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
    println("Battle Stats (CSV):")
    println("Player 1 Wins: $winStat1")
    println("Player 2 Wins: $winStat2")
    var ratio: Float = winStat1.toFloat()/winStat2.toFloat()
    println("Ratio: ${ratio}")
    println("Average Turn Count: ${totalTurnCount.average()}")
    println("Min Turn Count: ${totalTurnCount.min()}")
    println("Max Turn Count: ${totalTurnCount.max()}")

    println("+----+")
    println("Battle Stats (Classic):")
    println("Player 1 Wins: $winRandStat1")
    println("Player 2 Wins: $winRandStat2")
    var ratio2: Float = winRandStat1.toFloat()/winRandStat2.toFloat()
    println("Ratio: ${ratio2}")
    println("Average Turn Count: ${totalRandTurnCount.average()}")
    println("Min Turn Count: ${totalRandTurnCount.min()}")
    println("Max Turn Count: ${totalRandTurnCount.max()}")

    println("+----+")
    println("Execution Time: ${endMillis-startMillis}")
    println("+----+")
    try{
        println("Battles/second: ${(turnCounts.toLong() * 2L)/((endMillis-startMillis)/100L)}")
    }
    catch(e: Exception){
        println("Battles/second: N/A")
    }

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



