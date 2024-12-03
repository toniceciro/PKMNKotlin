private val ANSI_RESET = "\u001B[0m";
private val ANSI_BLACK = "\u001B[30m";
private val ANSI_RED = "\u001B[31m";
private val ANSI_GREEN = "\u001B[32m";
private val ANSI_YELLOW = "\u001B[33m";
private val ANSI_BLUE = "\u001B[34m";
private val ANSI_PURPLE = "\u001B[35m";
private val ANSI_CYAN = "\u001B[36m";
private val ANSI_WHITE = "\u001B[37m";
class InputHandler {
    //Input from player/AI Handlers
    fun choiceHandler(sourcePlayer:TrainerClass, opponentPlayer:TrainerClass, sourcePlayerChoice:playerChoiceData, opponentPlayerChoice:playerChoiceData): playerChoiceData{
        if (sourcePlayer.isAI){
            val choiceResult = opponentFightHandler(sourcePlayer,opponentPlayer,sourcePlayerChoice,opponentPlayerChoice)
            return choiceResult
        }else{
            val choiceResult = playerHandler(sourcePlayer,sourcePlayerChoice)
            return choiceResult
        }
    }
    fun switchChoiceHandler(sourceTrainer:TrainerClass,targetTrainer:TrainerClass?,targetChoiceData:playerChoiceData?,forcedFlag: Boolean = false):Int?{
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
    private fun playerHandler(trainerData: TrainerClass, playerChoice: playerChoiceData): playerChoiceData{
        val pokemonIndex = playerChoice.currentPokemonIndex
        var isMenu: Boolean = true
        var choiceIndex: Int = 0
        var currentChoice = playerChoice
        currentChoice.chosenMove = null; currentChoice.switchToIndex = null
        while(  (currentChoice.chosenMove == null && currentChoice.switchToIndex == null)  ){
            if(showMessage)println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            if(showMessage)println("${trainerData.trainerName}'s ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getName()} (LV ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getLevel()}) | $ANSI_GREEN HP: ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().currentHP} / ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().maxHP} $ANSI_RESET")
            if(showMessage)println("${trainerData.trainerName}, what will you do?")
            if(showMessage)println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            if(showMessage)println("$ANSI_YELLOW|0 - FIGHT$ANSI_RED | 1 - Pokemon |$ANSI_RESET")
            if(showMessage)println("$ANSI_YELLOW+-----------------+$ANSI_RESET")
            print(ANSI_YELLOW + "CHOICE: "+ ANSI_RESET)
            try{
                choiceIndex = readln().toInt()
                when (choiceIndex){
                    0 -> currentChoice = playerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
                    1 -> currentChoice = playerChoiceData(null, pokemonIndex,switchSelector(trainerData),playerChoice.isTrainerDefeated,playerChoice.isAI)
                    else -> currentChoice = playerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
            }catch(e: Exception){
                print("CHOICE: ")
                continue
            }
        }
        return currentChoice
    }
    private fun fightSelector(trainerData: TrainerClass, pokemonIndex: Int): PokemonMoveset?{
        if(showMessage)println("+--------------------+")
        if(showMessage)println("What should ${trainerData.currentPokemon.getPokemon(pokemonIndex).getName()} do?")
        if(showMessage)println("+-----------------+")
        val moveList = trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList
        if(showMessage)println("| 0 - ${moveList.getMove(0).getName()} {Power: ${moveList.getMove(0).getPower()} || PP: ${moveList.getMove(0).getPP().currentPP}/${moveList.getMove(0).getPP().maxPP}} [${moveList.getMove(0).getType()}] | 1 - ${moveList.getMove(1).getName()} {Power: ${moveList.getMove(1).getPower()} || PP: ${moveList.getMove(1).getPP().currentPP}/${moveList.getMove(1).getPP().maxPP}} [${moveList.getMove(1).getType()}] |")
        if(showMessage)println("| 2 - ${moveList.getMove(2).getName()} {Power: ${moveList.getMove(2).getPower()} || PP: ${moveList.getMove(2).getPP().currentPP}/${moveList.getMove(2).getPP().maxPP}} [${moveList.getMove(2).getType()}] | 3 - ${moveList.getMove(3).getName()} {Power: ${moveList.getMove(3).getPower()} || PP: ${moveList.getMove(3).getPP().currentPP}/${moveList.getMove(3).getPP().maxPP}} [${moveList.getMove(3).getType()}] |")
        if(showMessage)println("+-----------------+")
        if(showMessage)println("4 - BACK")
        print("CHOICE: ")
        var fightChoice = 0
        var isMenu = true
        while(isMenu){
            try{
                fightChoice = readln().toInt()
                if(!(0..4).contains(fightChoice)){isMenu = true; print("CHOICE:")}
                else{isMenu = false}
            }catch(e: Exception){
                if(showMessage)println("Try again")
                print("CHOICE:")
                continue
            }
        }
        if(fightChoice == 4){return null}
        else{return trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList.getMove(fightChoice)}
    }
    private fun opponentFightHandler(sourceTrainer:TrainerClass, targetTrainer:TrainerClass, sourceTrainerChoice:playerChoiceData, targetTrainerChoice:playerChoiceData):playerChoiceData{

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
        if(didOpponentSwitch){return playerChoiceData(null,currentPokemonIndex,switchIndex,sourceTrainerChoice.isTrainerDefeated,sourceTrainerChoice.isAI)}
        else{return playerChoiceData(sourceTrainer.currentPokemon.getPokemon(currentPokemonIndex).pokemonMoveList.getMove(bestMoveIndex),currentPokemonIndex,null,sourceTrainerChoice.isTrainerDefeated,sourceTrainerChoice.isAI)}
    }
    private fun opponentSwitchSelector(sourceTrainer:TrainerClass, targetTrainer: TrainerClass, targetTrainerChoice:playerChoiceData):Int{
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
            if(showMessage)println("$ANSI_PURPLE+--------------------+")
            if(showMessage)println("${trainerData.trainerName}, select a pokemon to switch to: ")
            if(showMessage)println("+-----------------+$ANSI_RESET")
            trainerData.listPokemon()
            if(!forcedFlag){if(showMessage)println("6 - BACK")}
            print("CHOICE:")
            try{
                switchChoice = readln().toInt()
                if(!forcedFlag && switchChoice == 6){return null}
                if((0..5).contains(switchChoice)){return switchChoice}
                else{isMenu = true}
            }catch(e: Exception){
                if(showMessage)println("Try again")
                print("CHOICE:")
                continue
            }
        }
        return switchChoice
    }
}
data class playerChoiceData(var chosenMove: PokemonMoveset?, var currentPokemonIndex: Int, var switchToIndex: Int?, var isTrainerDefeated: Boolean = false, var isAI: Boolean = false)