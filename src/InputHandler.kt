import kotlin.math.roundToInt

private val ANSI_RESET = "\u001B[0m";
private val ANSI_BLACK = "\u001B[30m";
private val ANSI_RED = "\u001B[31m";
private val ANSI_GREEN = "\u001B[32m";
private val ANSI_YELLOW = "\u001B[33m";
private val ANSI_BLUE = "\u001B[34m";
private val ANSI_PURPLE = "\u001B[35m";
private val ANSI_CYAN = "\u001B[36m";
private val ANSI_WHITE = "\u001B[37m";
private val ANSI_GRAY = "\u001B[90m";
private val ANSI_BRED = "\u001B[91m";
private val ANSI_BGREEN = "\u001B[92m";
private val ANSI_BYELLOW = "\u001B[93m";
private val ANSI_BBLUE = "\u001B[94m";
private val ANSI_BPURPLE = "\u001B[95m";
private val ANSI_BCYAN = "\u001B[96m";
private val ANSI_BWHITE = "\u001B[97m";
class InputHandler {
    //Input from player/AI Handlers
    fun choiceHandler(sourcePlayer:TrainerClass, opponentPlayer:TrainerClass, sourcePlayerChoice:PlayerChoiceData, opponentPlayerChoice:PlayerChoiceData): PlayerChoiceData{
        if (sourcePlayer.isAI){
            val choiceResult = opponentFightHandler(sourcePlayer,opponentPlayer,sourcePlayerChoice,opponentPlayerChoice)
            return choiceResult
        }else{
            val choiceResult = playerHandler(sourcePlayer,sourcePlayerChoice)
            return choiceResult
        }
    }
    fun switchChoiceHandler(sourceTrainer:TrainerClass, targetTrainer:TrainerClass?, targetChoiceData:PlayerChoiceData?, forcedFlag: Boolean = false):Int?{
        if(sourceTrainer.isAI && targetTrainer != null && targetChoiceData != null){
            return opponentSwitchSelector(sourceTrainer, targetTrainer, targetChoiceData)
        }
        else if(sourceTrainer.isAI){
            return (0..<sourceTrainer.currentPokemon.size()).random()
        }
        else{
            return switchSelector(sourceTrainer,true)
        }
    }
    private fun playerHandler(trainerData: TrainerClass, playerChoice: PlayerChoiceData): PlayerChoiceData{
        val pokemonIndex = playerChoice.currentPokemonIndex
        var isMenu: Boolean = true
        var choiceIndex: Int = 0
        var currentChoice = playerChoice
        currentChoice.chosenMove = null; currentChoice.switchToIndex = null
        while(  (currentChoice.chosenMove == null && currentChoice.switchToIndex == null)  ){
            println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            pokemonBarPrinter(trainerData.currentPokemon.size())
            println("${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getName()} (LV ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getLevel()})")
            proportionalBarPrinter(trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().currentHP,trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().maxHP)
            println(" | ${ANSI_GREEN}HP: ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().currentHP} / ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().maxHP} $ANSI_RESET")
            println("${trainerData.trainerName}, what will you do?")
            println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            println("$ANSI_YELLOW|0 - FIGHT$ANSI_RED | 1 - Pokemon |$ANSI_RESET")
            println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            print(ANSI_YELLOW + "CHOICE: "+ ANSI_RESET)
            try{
                choiceIndex = readln().toInt()
                when (choiceIndex){
                    0 -> currentChoice = PlayerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
                    1 -> currentChoice = PlayerChoiceData(null, pokemonIndex,switchSelector(trainerData),playerChoice.isTrainerDefeated,playerChoice.isAI)
                    else -> currentChoice = PlayerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
            }catch(e: Exception){
                continue
            }
        }
        return currentChoice
    }
    private fun fightSelector(trainerData: TrainerClass, pokemonIndex: Int): PokemonMoveset?{
        println("+--------------------+")
        println("What should ${trainerData.currentPokemon.getPokemon(pokemonIndex).getName()} do?")
        println("+-----------------+")
        val moveList = trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList
        println("| 0 - ${moveList.getMove(0).getName()} {Power: ${moveList.getMove(0).getPower()} || PP: ${moveList.getMove(0).getPP().currentPP}/${moveList.getMove(0).getPP().maxPP}} [${moveList.getMove(0).getType()}] | 1 - ${moveList.getMove(1).getName()} {Power: ${moveList.getMove(1).getPower()} || PP: ${moveList.getMove(1).getPP().currentPP}/${moveList.getMove(1).getPP().maxPP}} [${moveList.getMove(1).getType()}] |")
        println("| 2 - ${moveList.getMove(2).getName()} {Power: ${moveList.getMove(2).getPower()} || PP: ${moveList.getMove(2).getPP().currentPP}/${moveList.getMove(2).getPP().maxPP}} [${moveList.getMove(2).getType()}] | 3 - ${moveList.getMove(3).getName()} {Power: ${moveList.getMove(3).getPower()} || PP: ${moveList.getMove(3).getPP().currentPP}/${moveList.getMove(3).getPP().maxPP}} [${moveList.getMove(3).getType()}] |")
        println("+-----------------+")
        println("4 - BACK")
        print("CHOICE: ")
        var fightChoice = 0
        var isMenu = true
        while(isMenu){
            try{
                fightChoice = readln().toInt()
                if(!(0..4).contains(fightChoice)){isMenu = true; print("CHOICE:")}
                else{isMenu = false}
            }catch(e: Exception){
                print("CHOICE:")
                continue
            }
        }
        if(fightChoice == 4){return null}
        else{return trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList.getMove(fightChoice)}
    }
    private fun opponentFightHandler(sourceTrainer:TrainerClass, targetTrainer:TrainerClass, sourceTrainerChoice:PlayerChoiceData, targetTrainerChoice:PlayerChoiceData):PlayerChoiceData{

        val currentPokemonIndex = sourceTrainerChoice.currentPokemonIndex
        val currentHP = sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).checkHP().currentHP
        val maxHP = sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).checkHP().maxHP
        val targetPokemonType = targetTrainer.currentPokemon.getPokemon(targetTrainerChoice.currentPokemonIndex).pokemonType
        var x = 0
        val effectivityList = mutableListOf<Float>()
        var switchIndex: Int? = null
        var didOpponentSwitch = false
        //If opponent's Pokemon is less than 30%, switch to type that is most effective against player 50% of the time, or 25% of the time regardless of HP%
        if(  (currentHP <= (.30 * maxHP) && (0..50).contains((0..100).random())  ) || (0..25).contains((0..100).random())){
            switchIndex = opponentSwitchSelector(sourceTrainer,targetTrainer,targetTrainerChoice)
            didOpponentSwitch = true
            //If opponent switches to the same pokemon, cancel intent to switch
            if (switchIndex == currentPokemonIndex){switchIndex = null; didOpponentSwitch = false}
        }

        while (x < 4 && !didOpponentSwitch){
            when(sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(x).checkIfEmpty()){
                false -> effectivityList.add((BattleHandler().effectivenessValue(targetPokemonType, sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(x).getType())) * sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(x).getPower() * (sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(x).getAccuracy() * 0.25F))
                true -> effectivityList.add(1F)
            }
            x++
        }
        var bestMoveIndex = 0
        if (!didOpponentSwitch){
            bestMoveIndex = when (effectivityList.maxOrNull()){
                null -> {
                    effectivityList.indexOf(effectivityList.random())
                }
                else -> {
                    effectivityList.indexOf(effectivityList.maxOrNull())
                }
            }
        }
        if(didOpponentSwitch){return PlayerChoiceData(null,currentPokemonIndex,switchIndex,sourceTrainerChoice.isTrainerDefeated,sourceTrainerChoice.isAI)}
        else{return PlayerChoiceData(sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(bestMoveIndex),currentPokemonIndex,null,sourceTrainerChoice.isTrainerDefeated,sourceTrainerChoice.isAI)}
    }
    private fun opponentSwitchSelector(sourceTrainer:TrainerClass, targetTrainer: TrainerClass, targetTrainerChoice:PlayerChoiceData):Int{
        val playerPokemonType = targetTrainer.currentPokemon.getPokemon(targetTrainerChoice.currentPokemonIndex).pokemonType
        var x = 0
        val effectivityList = mutableListOf<Float>()
        while (x < sourceTrainer.currentPokemon.size()){
            effectivityList.add((BattleHandler().effectivenessValue(sourceTrainer.currentPokemon.getPokemon(x).pokemonType, playerPokemonType)))
            x++
        }
        //switchPlayerPokemonIndex = opponentSwitchSelector(targetTrainer,sourceTrainer,playerChoice)
        val bestPokemonIndex = effectivityList.indexOf(effectivityList.maxOrNull())
        return bestPokemonIndex
    }
    private fun switchSelector(trainerData:TrainerClass, forcedFlag: Boolean = false): Int?{
        var switchChoice = 0
        var isMenu = true
        while(isMenu){
            println("$ANSI_PURPLE+--------------------+")
            println("${trainerData.trainerName}, select a pokemon to switch to: ")
            println("+-----------------+$ANSI_RESET")
            trainerData.listPokemon()
            if(!forcedFlag){println("6 - BACK")}
            print("CHOICE:")
            try{
                switchChoice = readln().toInt()
                if(!forcedFlag && switchChoice == 6){return null}
                if((0..5).contains(switchChoice)){return switchChoice}
                else{isMenu = true}
            }catch(e: Exception){
                continue
            }
        }
        return switchChoice
    }
    private fun proportionalBarPrinter(currentValue:Int, maxValue: Int){
        val maxBarCount = 17F
        var proportion = (maxBarCount * (currentValue.toFloat()/maxValue.toFloat())).roundToInt()
        if(proportion == 0){proportion = 1}
        val whiteSpace = maxBarCount - proportion
        var x = 0
        while (x < proportion && proportion >= maxBarCount * 0.50F){
            print("${ANSI_BGREEN}▓")
            x++
        }
        while (x < proportion && (proportion < maxBarCount * 0.50F && proportion > maxBarCount * 0.25F)){
            print("${ANSI_BYELLOW}▓")
            x++
        }
        while (x < proportion && proportion < maxBarCount * 0.25F){
            print("${ANSI_BRED}▓")
            x++
        }
        x = 0
        while (x < whiteSpace){
            print("${ANSI_GREEN}░")
            x++
        }
        print(ANSI_RESET)
    }
    private fun pokemonBarPrinter(currentValue:Int, maxValue: Int = 6){
        val maxBarCount = 6F
        var proportion = (maxBarCount * (currentValue.toFloat()/maxValue.toFloat())).roundToInt()
        if(proportion == 0){proportion = 1}
        val whiteSpace = maxBarCount - proportion
        var x = 0
        print("《")
        while (x < proportion){
            print("${ANSI_BGREEN}℗")
            x++
        }
        x = 0
        while (x < whiteSpace){
            print("${ANSI_BRED}☒")
            x++
        }
        println("$ANSI_RESET》")
    }
}
data class PlayerChoiceData(var chosenMove: PokemonMoveset?, var currentPokemonIndex: Int, var switchToIndex: Int?, var isTrainerDefeated: Boolean = false, var isAI: Boolean = false)