var battleSpeed: Long = 250
var showMessage: Boolean = true
private val ANSI_RESET = "\u001B[0m";
private val ANSI_BLACK = "\u001B[30m";
private val ANSI_RED = "\u001B[31m";
private val ANSI_GREEN = "\u001B[32m";
private val ANSI_YELLOW = "\u001B[33m";
private val ANSI_BLUE = "\u001B[34m";
private val ANSI_PURPLE = "\u001B[35m";
private val ANSI_CYAN = "\u001B[36m";
private val ANSI_WHITE = "\u001B[37m";
class BattleHandler {

    fun battleMain(player:TrainerClass, player2:TrainerClass):battleData{
        val isBattleFinished = false
        var turnCount = 0
        Thread.sleep(battleSpeed)
        if(showMessage)println("+---BATTLE START---+")
        if(showMessage)println("${player.trainerName} vs. ${player2.trainerName}")
        Thread.sleep(battleSpeed)
        //Randomize who gets to send out pokemon first
        val initialPlayerPokemonIndex: Int?
        val initialOpponentPokemonIndex: Int?
        var playerChoice = playerChoiceData(null,0,null)
        var opponentChoice = playerChoiceData(null,0,null)
        when((0..1).random()){
            0 ->{
                //Send out Pokemon
                if(player.isAI){initialPlayerPokemonIndex = (0..<player.currentPokemon.size()).random()}
                else{initialPlayerPokemonIndex = switchSelector(player,true)}
                playerChoice = playerChoiceData(null,initialPlayerPokemonIndex!!,initialPlayerPokemonIndex,false,player.isAI)
                //Send out opponent Pokemon
                if(player2.isAI){initialOpponentPokemonIndex = opponentSwitchSelector(player,player2,playerChoice)}
                else{initialOpponentPokemonIndex = switchSelector(player2)}
                opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex!!,initialOpponentPokemonIndex,false,player2.isAI)
                playerChoice = battleSwitchHandler(player,playerChoice,true)
                opponentChoice = battleSwitchHandler(player2,opponentChoice,true)
            }
            1 ->{
                //Send out opponent Pokemon
                if(player2.isAI){initialOpponentPokemonIndex = (0..<player2.currentPokemon.size()).random()}
                else{initialOpponentPokemonIndex = switchSelector(player2)}
                opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex!!,initialOpponentPokemonIndex,false,player2.isAI)
                //Send out Pokemon
                if(player.isAI){initialPlayerPokemonIndex = opponentSwitchSelector(player2,player,opponentChoice)}
                else{initialPlayerPokemonIndex = switchSelector(player,true)}
                playerChoice = playerChoiceData(null,initialPlayerPokemonIndex!!,initialPlayerPokemonIndex,false,player.isAI)
                playerChoice = battleSwitchHandler(player,playerChoice,true)
                opponentChoice = battleSwitchHandler(player2,opponentChoice,true)
            }
        }
        while (!isBattleFinished){
            //Reset States
            playerChoice.chosenMove = null; playerChoice.switchToIndex = null
            opponentChoice.chosenMove = null; opponentChoice.switchToIndex = null
            //Check if player pokemon fainted
            playerChoice = battleFaintHandler(player,player2,playerChoice, opponentChoice)
            //Check and end battle if any trainer is defeated
            if (playerChoice.isTrainerDefeated || opponentChoice.isTrainerDefeated) break
            //Check if player switched pokemon
            playerChoice = battleSwitchHandler(player,playerChoice,true)
            //Check if opponent pokemon fainted
            opponentChoice = battleFaintHandler(player2,player,opponentChoice, playerChoice)
            //Check and end battle if any trainer is defeated
            if (playerChoice.isTrainerDefeated || opponentChoice.isTrainerDefeated) break
            //Check if opponent switch pokemon
            opponentChoice = battleSwitchHandler(player2,opponentChoice,true)
            if (!isBattleFinished){
                //Prompts the trainer with the fastest pokemon first, else randomly choose
                val player1Speed = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonSPD
                val player2Speed = player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).pokemonSPD
                when{
                    player1Speed > player2Speed -> {
                        playerChoice = choiceHandler(player,player2,playerChoice,opponentChoice)
                        //Check if player switched pokemon
                        playerChoice = battleSwitchHandler(player,playerChoice,false)
                        opponentChoice = choiceHandler(player2,player,opponentChoice,playerChoice)
                        //Check if opponent switch pokemon
                        opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                    }
                    player1Speed < player2Speed -> {
                        opponentChoice = choiceHandler(player2,player,opponentChoice,playerChoice)
                        //Check if opponent switch pokemon
                        opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                        playerChoice = choiceHandler(player,player2,playerChoice,opponentChoice)
                        //Check if player switched pokemon
                        playerChoice = battleSwitchHandler(player,playerChoice,false)
                    }
                    else ->{
                        when((0..1).random()){
                            0 -> {
                                playerChoice = choiceHandler(player,player2,playerChoice,opponentChoice)
                                //Check if player switched pokemon
                                playerChoice = battleSwitchHandler(player,playerChoice,false)
                                opponentChoice = choiceHandler(player2,player,opponentChoice,playerChoice)
                                //Check if opponent switch pokemon
                                opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                            }
                            1 -> {
                                opponentChoice = choiceHandler(player2,player,opponentChoice,playerChoice)
                                //Check if opponent switch pokemon
                                opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                                playerChoice = choiceHandler(player,player2,playerChoice,opponentChoice)
                                //Check if player switched pokemon
                                playerChoice = battleSwitchHandler(player,playerChoice,false)
                            }
                        }
                    }
                }
                if(showMessage)println(ANSI_RED + "+$ANSI_YELLOW-$ANSI_GREEN-$ANSI_CYAN-${ANSI_BLUE}T${ANSI_PURPLE}U${ANSI_RED}R${ANSI_YELLOW}N${ANSI_GREEN}-$ANSI_CYAN${turnCount}$ANSI_BLUE-$ANSI_PURPLE-$ANSI_RED-$ANSI_YELLOW+" + ANSI_RESET)
                mainFightHandler(player,player2,playerChoice,opponentChoice)
                Thread.sleep(battleSpeed)
                turnCount++
            }
        }
        if(showMessage)println("+-----------------------+")
        when{
            playerChoice.isTrainerDefeated -> if(showMessage)println(ANSI_GREEN + "${player2.trainerName} won the match!" + ANSI_RESET)
            opponentChoice.isTrainerDefeated -> if(showMessage)println(ANSI_GREEN + "${player.trainerName} won the match!"+ ANSI_RESET)
        }
        if(showMessage)println("+-----------------------+")
        Thread.sleep(battleSpeed)
        return battleData(!playerChoice.isTrainerDefeated,!opponentChoice.isTrainerDefeated,turnCount)
    }
    //Main battle functions
    private fun battleFaintHandler(sourceTrainer:TrainerClass, targetTrainer:TrainerClass, playerChoice:playerChoiceData, opponentChoice: playerChoiceData):playerChoiceData{
        if(sourceTrainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkIfFainted()){
            if(showMessage)println("$ANSI_RED${sourceTrainer.trainerName}'s ${sourceTrainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getName()}$ANSI_RED fainted!$ANSI_RESET")
            sourceTrainer.currentPokemon.removePokemon(playerChoice.currentPokemonIndex)
            if(!sourceTrainer.currentPokemon.isEmpty()){
                if (playerChoice.isAI == true) {
                    val switchPlayerPokemonIndex = opponentSwitchSelector(targetTrainer,sourceTrainer,opponentChoice)
                    return playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex,playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
                if (playerChoice.isAI == false){
                    val switchPlayerPokemonIndex = switchSelector(sourceTrainer,true)
                    return playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex, playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
            }
            else{
                if(showMessage)println(ANSI_RED + "${sourceTrainer.trainerName} has no more usable Pokemon" + ANSI_RESET)
                if(showMessage)println(ANSI_RED + "${sourceTrainer.trainerName} whited out!" + ANSI_RESET)
                playerChoice.isTrainerDefeated = true
                return playerChoice
            }
        }
        return playerChoice
    }
    private fun mainFightHandler(player:TrainerClass, player2:TrainerClass, playerChoice:playerChoiceData, playerChoice2:playerChoiceData){
        val playerPokemon = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex)
        val opponentPokemon = player2.currentPokemon.getPokemon(playerChoice2.currentPokemonIndex)
        var faintCheck = false
        //Compare pokemon speed stat
        val pokemonSpeed1 = playerPokemon.pokemonSPD
        val pokemonSpeed2 = opponentPokemon.pokemonSPD
        if (pokemonSpeed2 > pokemonSpeed1){
            if (playerChoice2.chosenMove != null) damageHandler(opponentPokemon,playerPokemon,playerChoice2)
            if(playerPokemon.checkIfFainted() || opponentPokemon.checkIfFainted()) return
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            return
        }
        if (pokemonSpeed2 < pokemonSpeed1){
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            if(playerPokemon.checkIfFainted() || opponentPokemon.checkIfFainted()) return
            if (playerChoice2.chosenMove != null) damageHandler(opponentPokemon,playerPokemon,playerChoice2)
            return
        }
        else{
            when((0..1).random()){
                0 -> {
                    if (playerChoice2.chosenMove != null) damageHandler(opponentPokemon,playerPokemon,playerChoice2)
                    faintCheck = playerPokemon.checkIfFainted()
                    if(faintCheck) return
                    if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
                    return
                }
                1 -> {
                    if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
                    faintCheck = opponentPokemon.checkIfFainted()
                    if(faintCheck) return
                    if (playerChoice2.chosenMove != null)damageHandler(opponentPokemon,playerPokemon,playerChoice2)
                    return
                }
            }
        }
    }
    private fun damageHandler(sourcePokemon: PokemonClass, targetPokemon: PokemonClass,sourceChoice: playerChoiceData){
        sourceChoice.chosenMove?.removePP()
        if(showMessage)println(ANSI_PURPLE + "${sourcePokemon.getName()}$ANSI_PURPLE used ${sourceChoice.chosenMove?.getName()}!" + ANSI_RESET)
        val battleResult = calculateDamage(sourcePokemon,sourceChoice.chosenMove!!,targetPokemon)
        Thread.sleep(battleSpeed)
        if(battleResult.isCritical && battleResult.damageAmount > 0) {
            if(showMessage)println(ANSI_YELLOW + "Critical hit!" + ANSI_RESET)
            Thread.sleep(battleSpeed)
        }
        when{
            battleResult.effectiveVal > 1.0F && battleResult.damageAmount > 0 -> {
                if(showMessage)println(ANSI_GREEN + "It's super effective!" + ANSI_RESET)
                Thread.sleep(battleSpeed)
            }
            battleResult.effectiveVal < 1.0F && battleResult.damageAmount > 0 -> {
                if(showMessage)println(ANSI_RED + "It's not very effective..." + ANSI_RESET)
                Thread.sleep(battleSpeed)
            }
        }
        when (battleResult.damageAmount <= 0){
            false -> {
                if(showMessage)println(ANSI_PURPLE + "Dealt ${battleResult.damageAmount} HP of damage to ${targetPokemon.getName()}! ($ANSI_GREEN${targetPokemon.pokemonCurrentHP.toInt()}$ANSI_YELLOW ->$ANSI_RED ${if (targetPokemon.pokemonCurrentHP - battleResult.damageAmount < 1){0}else{(targetPokemon.pokemonCurrentHP - battleResult.damageAmount).toInt()}}$ANSI_YELLOW)$ANSI_RESET")
                Thread.sleep(battleSpeed)
            }
            (battleResult.isNotAffected) -> {
                if(showMessage)println(ANSI_CYAN + "It doesn't affect ${targetPokemon.getName()}..." + ANSI_RESET)
                Thread.sleep(battleSpeed)
            }
            else -> {
                if(showMessage)println(ANSI_RED + "${sourcePokemon.getName()}'s attack missed!" + ANSI_RESET)
                Thread.sleep(battleSpeed)
            }
        }
        //EDGE CASE FOR SELF_DESTRUCT
        val faintList = listOf("Self-Destruct","Explosion","Memento","Healing Wish","Lunar Dance","Final Gambit","Misty Explosion")
        if(faintList.contains(sourceChoice.chosenMove!!.getName())){
            sourcePokemon.damageHP(sourcePokemon.pokemonMaxHP.toInt())
        }
        //Reduce HP of target Pokemon
        targetPokemon.damageHP(battleResult.damageAmount)
    }
    private fun battleSwitchHandler(trainer:TrainerClass, playerChoice:playerChoiceData, forcedSwitch: Boolean = false):playerChoiceData{
        if(playerChoice.switchToIndex != null){
            if(!forcedSwitch){
                if(showMessage)println(ANSI_CYAN + "${trainer.trainerName} withdrew ${trainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getName()}!" + ANSI_RESET)
            }
            playerChoice.currentPokemonIndex = playerChoice.switchToIndex!!
            playerChoice.switchToIndex = null
            if(showMessage)println(ANSI_GREEN + "${trainer.trainerName} sent out ${trainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getName()}!" + ANSI_RESET)
            Thread.sleep(battleSpeed)
            return playerChoice
        }
        return playerChoice
    }
    //Input from player/AI Handlers
    private fun choiceHandler(sourcePlayer:TrainerClass, opponentPlayer:TrainerClass, sourcePlayerChoice:playerChoiceData, opponentPlayerChoice:playerChoiceData): playerChoiceData{
        if (sourcePlayer.isAI){
            val choiceResult = opponentFightHandler(opponentPlayer,sourcePlayer,opponentPlayerChoice,sourcePlayerChoice)
            return choiceResult
        }else{
            val choiceResult = playerHandler(sourcePlayer,sourcePlayerChoice)
            return choiceResult
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
    private fun opponentFightHandler(player:TrainerClass, player2:TrainerClass, playerChoice:playerChoiceData, opponentChoice:playerChoiceData):playerChoiceData{

        val opponentPokemonIndex = opponentChoice.currentPokemonIndex
        val currentHP = player2.currentPokemon.getPokemon(opponentPokemonIndex).checkHP().currentHP
        val maxHP = player2.currentPokemon.getPokemon(opponentPokemonIndex).checkHP().maxHP
        val playerPokemonType = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonType
        var x = 0
        val effectivityList = mutableListOf<Float>()
        var switchIndex: Int? = null
        var didOpponentSwitch = false
        //If opponent's Pokemon is less than 30%, switch to type that is most effective against player 50% of the time, or 25% of the time regardless of HP%
        if(  (currentHP <= (.30 * maxHP) && (0..50).contains((0..100).random())  ) || (0..25).contains((0..100).random())){
            switchIndex = opponentSwitchSelector(player,player2,playerChoice)
            didOpponentSwitch = true
            //If opponent switches to the same pokemon, cancel intent to switch
            if (switchIndex == opponentPokemonIndex){switchIndex = null; didOpponentSwitch = false}
        }

        while (x < 4 && !didOpponentSwitch){
            when(player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).checkIfEmpty()){
                false -> effectivityList.add((effectivenessValue(playerPokemonType, player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getType())) * player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getPower() * (player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getAccuracy() * 0.25F))
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
        if(didOpponentSwitch){return playerChoiceData(null,opponentPokemonIndex,switchIndex,opponentChoice.isTrainerDefeated,opponentChoice.isAI)}
        else{return playerChoiceData(player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(bestMoveIndex),opponentPokemonIndex,null,opponentChoice.isTrainerDefeated,opponentChoice.isAI)}
    }
    private fun opponentSwitchSelector(player:TrainerClass, opponentTrainer: TrainerClass, playerChoice:playerChoiceData):Int{
        val playerPokemonType = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonType
        var x = 0
        val effectivityList = mutableListOf<Float>()
        while (x < opponentTrainer.currentPokemon.size()){
            effectivityList.add((effectivenessValue(opponentTrainer.currentPokemon.getPokemon(x).pokemonType, playerPokemonType)))
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
    //Damage Calculations
    private fun calculateDamage(sourcePokemon: PokemonClass, sourceMove: PokemonMoveset, targetPokemon: PokemonClass): battleResult{
        val STABvalue =
            when (isStab(sourceMove.getType(),sourcePokemon.pokemonType)){
            true -> 1.5F
            false -> 1.0F
        }
        val critStatus = isCritical()
        val critValue =
            when (critStatus){
            true -> 1.5F
            false -> 1.0F
        }
        //calculate effectiveness value
        var isNotAffected: Boolean = false
        val effectivenessValue = effectivenessValue(sourceMove.getType(), targetPokemon.pokemonType)
        if (effectivenessValue == 0F){isNotAffected = true}
        //Check if it misses
        val basePower: Int
        when( ((0..sourceMove.getAccuracy()).contains((0..100).random())) ){
            true -> {basePower = sourceMove.getPower()}
            false -> {basePower = 0}
        }
        //Start Damage calculation
        val modifier1 = (((2F * sourcePokemon.pokemonLevel.toFloat()) + 10F) / 250F)
        //Special or Physical Check
        var modifier2: Float
        if (sourceMove.getAttackType() == "Physical"){
            modifier2 = (sourcePokemon.pokemonATK.toFloat() / targetPokemon.pokemonDEF.toFloat())
        }else{
            modifier2 = (sourcePokemon.pokemonSPA.toFloat() / targetPokemon.pokemonSPD.toFloat())
        }
        val baseDamage = basePower * STABvalue * effectivenessValue * critValue * listOf(0.85F,1F).random()
        val totalDamage = (modifier1 * modifier2) * baseDamage
        //Add to battleresult calc
        val result = battleResult(totalDamage.toInt(), critStatus, effectivenessValue,sourcePokemon.getName(),targetPokemon.getName(),isNotAffected)
        return result
    }
    private fun isStab(sourceMoveType: String, sourcePokemonType:String): Boolean{
        val moveType = sourceMoveType.substringBefore('|')
        val pokemonType = sourcePokemonType.substringBefore('|')
        val pokemonType2 = sourcePokemonType.substringAfter('|')
        return moveType == pokemonType || moveType == pokemonType2
    }
    private fun isCritical(): Boolean{
        val chance = (0..100).random()
        if ((0..4).contains(chance)) return true
        else return false
    }
    private fun effectivenessValue(type1: String, type2: String): Float{
        val sourceType = type1.substringBefore('|')
        val targetType = type2.substringBefore('|')
        val targetType2 = type2.substringAfter('|')
        fun calculateValue(type1: String, type2:String):Float{
            return when {
                // Fire
                type1 == "Fire" && type2 == "Water" -> 0.5F
                type1 == "Fire" && type2 == "Grass" -> 2F
                type1 == "Fire" && type2 == "Bug" -> 2F
                type1 == "Fire" && type2 == "Rock" -> 0.5F
                type1 == "Fire" && type2 == "Ice" -> 2F
                type1 == "Fire" && type2 == "Steel" -> 2F
                type1 == "Fire" && type2 == "Fire" -> 0.5F
                type1 == "Fire" && type2 == "Dragon" -> 0.5F

                // Water
                type1 == "Water" && type2 == "Fire" -> 2F
                type1 == "Water" && type2 == "Grass" -> 0.5F
                type1 == "Water" && type2 == "Ground" -> 2F
                type1 == "Water" && type2 == "Rock" -> 2F
                type1 == "Water" && type2 == "Water" -> 0.5F

                // Grass
                type1 == "Grass" && type2 == "Fire" -> 0.5F
                type1 == "Grass" && type2 == "Water" -> 2F
                type1 == "Grass" && type2 == "Ground" -> 2F
                type1 == "Grass" && type2 == "Rock" -> 2F
                type1 == "Grass" && type2 == "Bug" -> 0.5F
                type1 == "Grass" && type2 == "Flying" -> 0.5F
                type1 == "Grass" && type2 == "Poison" -> 0.5F
                type1 == "Grass" && type2 == "Grass" -> 0.5F
                type1 == "Grass" && type2 == "Dragon" -> 0.5F
                type1 == "Grass" && type2 == "Steel" -> 0.5F

                // Normal
                type1 == "Normal" && type2 == "Rock" -> 0.5F
                type1 == "Normal" && type2 == "Ghost" -> 0F
                type1 == "Normal" && type2 == "Steel" -> 0.5F
                type1 == "Normal" && type2 == "Normal" -> 1F

                // Bug
                type1 == "Bug" && type2 == "Fire" -> 0.5F
                type1 == "Bug" && type2 == "Grass" -> 2F
                type1 == "Bug" && type2 == "Fighting" -> 0.5F
                type1 == "Bug" && type2 == "Flying" -> 0.5F
                type1 == "Bug" && type2 == "Poison" -> 0.5F
                type1 == "Bug" && type2 == "Rock" -> 0.5F
                type1 == "Bug" && type2 == "Ghost" -> 0.5F
                type1 == "Bug" && type2 == "Steel" -> 0.5F
                type1 == "Bug" && type2 == "Fairy" -> 0.5F
                type1 == "Bug" && type2 == "Psychic" -> 2F
                type1 == "Bug" && type2 == "Dark" -> 2F

                // Rock
                type1 == "Rock" && type2 == "Fire" -> 2F
                type1 == "Rock" && type2 == "Ice" -> 2F
                type1 == "Rock" && type2 == "Fighting" -> 0.5F
                type1 == "Rock" && type2 == "Flying" -> 2F
                type1 == "Rock" && type2 == "Bug" -> 2F
                type1 == "Rock" && type2 == "Rock" -> 1F
                type1 == "Rock" && type2 == "Steel" -> 0.5F

                // Flying
                type1 == "Flying" && type2 == "Grass" -> 2F
                type1 == "Flying" && type2 == "Fighting" -> 2F
                type1 == "Flying" && type2 == "Bug" -> 2F
                type1 == "Flying" && type2 == "Rock" -> 0.5F
                type1 == "Flying" && type2 == "Steel" -> 0.5F
                type1 == "Flying" && type2 == "Electric" -> 0.5F

                // Steel
                type1 == "Steel" && type2 == "Fire" -> 0.5F
                type1 == "Steel" && type2 == "Water" -> 0.5F
                type1 == "Steel" && type2 == "Electric" -> 0.5F
                type1 == "Steel" && type2 == "Ice" -> 2F
                type1 == "Steel" && type2 == "Rock" -> 2F
                type1 == "Steel" && type2 == "Steel" -> 0.5F
                type1 == "Steel" && type2 == "Fairy" -> 2F

                // Electric
                type1 == "Electric" && type2 == "Water" -> 2F
                type1 == "Electric" && type2 == "Grass" -> 0.5F
                type1 == "Electric" && type2 == "Ground" -> 0F
                type1 == "Electric" && type2 == "Flying" -> 2F
                type1 == "Electric" && type2 == "Electric" -> 0.5F
                type1 == "Electric" && type2 == "Dragon" -> 0.5F

                // Psychic
                type1 == "Psychic" && type2 == "Fighting" -> 2F
                type1 == "Psychic" && type2 == "Poison" -> 2F
                type1 == "Psychic" && type2 == "Steel" -> 0.5F
                type1 == "Psychic" && type2 == "Psychic" -> 0.5F
                type1 == "Psychic" && type2 == "Dark" -> 0F

                // Dark
                type1 == "Dark" && type2 == "Fighting" -> 0.5F
                type1 == "Dark" && type2 == "Psychic" -> 2F
                type1 == "Dark" && type2 == "Ghost" -> 2F
                type1 == "Dark" && type2 == "Dark" -> 0.5F
                type1 == "Dark" && type2 == "Fairy" -> 0.5F

                // Ghost
                type1 == "Ghost" && type2 == "Normal" -> 0F
                type1 == "Ghost" && type2 == "Psychic" -> 2F
                type1 == "Ghost" && type2 == "Ghost" -> 2F
                type1 == "Ghost" && type2 == "Dark" -> 0.5F

                // Fairy
                type1 == "Fairy" && type2 == "Fire" -> 0.5F
                type1 == "Fairy" && type2 == "Fighting" -> 2F
                type1 == "Fairy" && type2 == "Dragon" -> 2F
                type1 == "Fairy" && type2 == "Dark" -> 2F
                type1 == "Fairy" && type2 == "Poison" -> 0.5F
                type1 == "Fairy" && type2 == "Steel" -> 0.5F

                // Ground
                type1 == "Ground" && type2 == "Fire" -> 2F
                type1 == "Ground" && type2 == "Electric" -> 2F
                type1 == "Ground" && type2 == "Grass" -> 0.5F
                type1 == "Ground" && type2 == "Ice" -> 0.5F
                type1 == "Ground" && type2 == "Poison" -> 2F
                type1 == "Ground" && type2 == "Rock" -> 2F
                type1 == "Ground" && type2 == "Bug" -> 0.5F
                type1 == "Ground" && type2 == "Steel" -> 2F
                type1 == "Ground" && type2 == "Flying" -> 0F

                // Dragon
                type1 == "Dragon" && type2 == "Dragon" -> 2F
                type1 == "Dragon" && type2 == "Steel" -> 0.5F
                type1 == "Dragon" && type2 == "Fairy" -> 0.5F
                type1 == "Dragon" && type2 == "Fire" -> 0.5F
                type1 == "Dragon" && type2 == "Water" -> 0.5F
                type1 == "Dragon" && type2 == "Electric" -> 0.5F

                // Ice
                type1 == "Ice" && type2 == "Water" -> 0.5F
                type1 == "Ice" && type2 == "Grass" -> 2F
                type1 == "Ice" && type2 == "Ground" -> 2F
                type1 == "Ice" && type2 == "Flying" -> 2F
                type1 == "Ice" && type2 == "Dragon" -> 2F
                type1 == "Ice" && type2 == "Steel" -> 0.5F
                type1 == "Ice" && type2 == "Fire" -> 0.5F
                type1 == "Ice" && type2 == "Water" -> 0.5F
                type1 == "Ice" && type2 == "Ice" -> 0.5F
                type1 == "Ice" && type2 == "Rock" -> 0.5F

                // Poison
                type1 == "Poison" && type2 == "Grass" -> 2F
                type1 == "Poison" && type2 == "Fairy" -> 2F
                type1 == "Poison" && type2 == "Poison" -> 0.5F
                type1 == "Poison" && type2 == "Ground" -> 0.5F
                type1 == "Poison" && type2 == "Rock" -> 0.5F
                type1 == "Poison" && type2 == "Ghost" -> 0.5F
                type1 == "Poison" && type2 == "Steel" -> 0F
                type1 == "Poison" && type2 == "Poison" -> 0.5F
                type1 == "Poison" && type2 == "Bug" -> 1F
                type1 == "Poison" && type2 == "Flying" -> 1F
                type1 == "Poison" && type2 == "Dragon" -> 1F
                type1 == "Poison" && type2 == "Fire" -> 1F
                type1 == "Poison" && type2 == "Water" -> 1F
                type1 == "Poison" && type2 == "Ice" -> 1F
                type1 == "Poison" && type2 == "Psychic" -> 1F

                // Fighting
                type1 == "Fighting" && type2 == "Normal" -> 2F
                type1 == "Fighting" && type2 == "Rock" -> 2F
                type1 == "Fighting" && type2 == "Steel" -> 2F
                type1 == "Fighting" && type2 == "Ice" -> 2F
                type1 == "Fighting" && type2 == "Dark" -> 2F
                type1 == "Fighting" && type2 == "Poison" -> 0.5F
                type1 == "Fighting" && type2 == "Flying" -> 0.5F
                type1 == "Fighting" && type2 == "Psychic" -> 0.5F
                type1 == "Fighting" && type2 == "Bug" -> 0.5F
                type1 == "Fighting" && type2 == "Fairy" -> 0.5F
                type1 == "Fighting" && type2 == "Ghost" -> 0F
                type1 == "Fighting" && type2 == "Water" -> 1F
                type1 == "Fighting" && type2 == "Grass" -> 1F
                type1 == "Fighting" && type2 == "Electric" -> 1F
                type1 == "Fighting" && type2 == "Dragon" -> 1F

                else -> 1F
            }
        } //List of values for effectivity
        when(targetType2 == targetType){
            true -> {
                //Target pokemon is single type
                val result = calculateValue(sourceType, targetType)
                return result
            }
            false -> {
                //Target pokemon is dual type
                val result = calculateValue(sourceType,targetType) * calculateValue(sourceType,targetType2)
                return result
            }
        }

    }

}
data class battleResult(var damageAmount: Int, var isCritical: Boolean, var effectiveVal: Float,var sourcePokemon: String, var targetPokemon:String, var isNotAffected: Boolean = false)
data class playerChoiceData(var chosenMove: PokemonMoveset?, var currentPokemonIndex: Int, var switchToIndex: Int?, var isTrainerDefeated: Boolean = false, var isAI: Boolean = false)
data class battleData(var playerWin: Boolean, var opponentWin: Boolean, var totalTurns: Int)
