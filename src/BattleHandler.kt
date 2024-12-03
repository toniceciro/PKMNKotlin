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
                initialPlayerPokemonIndex = InputHandler().switchChoiceHandler(player,player2,opponentChoice)
                playerChoice = playerChoiceData(null,initialPlayerPokemonIndex!!,initialPlayerPokemonIndex,false,player.isAI)
                //Send out opponent Pokemon
                initialOpponentPokemonIndex = InputHandler().switchChoiceHandler(player2,player,playerChoice)
                opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex!!,initialOpponentPokemonIndex,false,player2.isAI)

            }
            1 ->{
                //Send out opponent Pokemon
                initialOpponentPokemonIndex = InputHandler().switchChoiceHandler(player2,player,playerChoice)
                opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex!!,initialOpponentPokemonIndex,false,player2.isAI)
                //Send out Pokemon
                initialPlayerPokemonIndex = InputHandler().switchChoiceHandler(player,player2,opponentChoice)
                playerChoice = playerChoiceData(null,initialPlayerPokemonIndex!!,initialPlayerPokemonIndex,false,player.isAI)
            }
        }
        playerChoice = battleSwitchHandler(player,playerChoice,true)
        opponentChoice = battleSwitchHandler(player2,opponentChoice,true)
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
                        playerChoice = InputHandler().choiceHandler(player,player2,playerChoice,opponentChoice)
                        //Check if player switched pokemon
                        playerChoice = battleSwitchHandler(player,playerChoice,false)
                        opponentChoice = InputHandler().choiceHandler(player2,player,opponentChoice,playerChoice)
                        //Check if opponent switch pokemon
                        opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                    }
                    player1Speed < player2Speed -> {
                        opponentChoice = InputHandler().choiceHandler(player2,player,opponentChoice,playerChoice)
                        //Check if opponent switch pokemon
                        opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                        playerChoice = InputHandler().choiceHandler(player,player2,playerChoice,opponentChoice)
                        //Check if player switched pokemon
                        playerChoice = battleSwitchHandler(player,playerChoice,false)
                    }
                    else ->{
                        when((0..1).random()){
                            0 -> {
                                playerChoice = InputHandler().choiceHandler(player,player2,playerChoice,opponentChoice)
                                //Check if player switched pokemon
                                playerChoice = battleSwitchHandler(player,playerChoice,false)
                                opponentChoice = InputHandler().choiceHandler(player2,player,opponentChoice,playerChoice)
                                //Check if opponent switch pokemon
                                opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                            }
                            1 -> {
                                opponentChoice = InputHandler().choiceHandler(player2,player,opponentChoice,playerChoice)
                                //Check if opponent switch pokemon
                                opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                                playerChoice = InputHandler().choiceHandler(player,player2,playerChoice,opponentChoice)
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
                    val switchPlayerPokemonIndex = InputHandler().switchChoiceHandler(sourceTrainer,targetTrainer,opponentChoice)
                    return playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex,playerChoice.isTrainerDefeated,playerChoice.isAI)
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
    fun effectivenessValue(type1: String, type2: String): Float{
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
data class battleData(var playerWin: Boolean, var opponentWin: Boolean, var totalTurns: Int)
