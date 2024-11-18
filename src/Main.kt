//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val endGame = false
    var trainerData = TrainerList<TrainerClass>()
    var currentTrainer = TrainerClass("DEFAULT")
    while (!endGame){
        println("+-------------+")
        println("+---Pokemon Simulator---+")
        println("+-------------+")
        println("Select Game Mode")
        println("0 - Quick Battle")
        println("1 - Custom Battle")
        println("2 - Trainer Editor")
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
            2 -> TODO()
            3 -> stressTest()
            4 -> {
                println("Player 1 Name: ")
                val trainerName = readln()
                println("Player 2 Name: ")
                val trainerName2 = readln()
                multiplayer(TrainerClass(trainerName,false),TrainerClass(trainerName2,false))
            }
        }
    }
    fun listTrainer(){
        var x = 0
        var trainerLoop: TrainerClass
        while (x < trainerData.size()){
            trainerLoop = trainerData.loadTrainer(x)
            println("$x - ${trainerLoop.trainerName}")
            x++
        }
    }
}

fun multiplayer(trainer1:TrainerClass, trainer2:TrainerClass){
    println("+---MULTIPLAYER BATTLE---+")
    println("${trainer1.trainerName} vs. ${trainer2.trainerName}")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    trainer1.generatePokemon(opponentNum,4,true)
    trainer2.generatePokemon(opponentNum,4,true)
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
fun quickBattle(trainer: TrainerClass){
    println("+---QUICK BATTLE---+")
    println("${trainer.trainerName} vs. Rival")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    val rival = TrainerClass("Rival", true)
    trainer.generatePokemon(opponentNum,4,true)
    rival.generatePokemon(opponentNum,4,true)
    println("Rival has: ")
    rival.listPokemon()
    Thread.sleep(5000)
    val battleHandler = BattleHandler()
    battleHandler.battleMain(trainer,rival)
    println("+---QUICK BATTLE END---+")
    return
}

fun stressTest(){
    println("+-------------+")
    println("+---STRESS TEST---+")
    println("+-------------+")
    val neverEnding = false
    var count = 0
    while (!neverEnding){
        println("+-------EPOCH $count------+")
        Thread.sleep(1000)
        val trainer1 = TrainerClass("TEST1", true)
        val trainer2 = TrainerClass("TEST2", true)
        trainer1.generatePokemon(6,4,false)
        trainer2.generatePokemon(6,4,false)
        val battleHandler = BattleHandler()
        battleHandler.battleMain(trainer1,trainer2)
        count++
    }

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



