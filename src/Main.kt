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
        val choice = readln().toInt()
        when (choice){
            0 -> quickBattle(TrainerClass("QUICKPLAY"))
            1 -> {
            if (trainerData.isEmpty() || currentTrainer.trainerName == "DEFAULT"){
                println("You need to make a trainer first...")
            }else{
                quickBattle(currentTrainer)
            }
        }
            2 -> TODO()
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
fun quickBattle(trainer: TrainerClass){
    println("+-------------+")
    println("+---QUICK BATTLE---+")
    println("+-------------+")
    println("${trainer.trainerName} vs. Rival")
    println("How many VS. Pokemon?")
    val opponentNum = readln().toInt()
    val rival = TrainerClass("Rival")
    trainer.generatePokemon(opponentNum,4,true)
    rival.generatePokemon(opponentNum,4,true)
    println("Rival has: ")
    rival.listPokemon()
    println("+-------------+")
    val battleHandler = BattleHandler()
    battleHandler.battleMain(trainer,rival)
    println("+-------------+")
    println("+---QUICK BATTLE END---+")
    println("+-------------+")
    return
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



