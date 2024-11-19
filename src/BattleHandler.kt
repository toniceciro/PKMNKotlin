public var battleSpeed: Long = 250

class BattleHandler {

    fun battleMain(player:TrainerClass, player2:TrainerClass){
        var isBattleFinished = false
        var turnCount = 0
        println("+--------------------+")
        Thread.sleep(battleSpeed)
        println("+---BATTLE START---+")
        println("${player.trainerName} vs. ${player2.trainerName}")
        Thread.sleep(battleSpeed)
        println("+--------------------+")
        Thread.sleep(battleSpeed)
        //Send out Pokemon
        var initialPlayerPokemonIndex: Int
        if(player.isAI){initialPlayerPokemonIndex = 0}
        else{initialPlayerPokemonIndex = switchSelector(player)}
        var playerChoice = playerChoiceData(null,initialPlayerPokemonIndex,initialPlayerPokemonIndex,false,player.isAI)
        var initialOpponentPokemonIndex: Int
        if(player2.isAI){initialOpponentPokemonIndex = opponentSwitchSelector(player,player2,playerChoice)}
        else{initialOpponentPokemonIndex = switchSelector(player2)}
        var opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex,initialOpponentPokemonIndex,false,player2.isAI)
        playerChoice = battleSwitchHandler(player,playerChoice,true)
        opponentChoice = battleSwitchHandler(player2,opponentChoice,true)
        while (!isBattleFinished){
            //Check if player pokemon fainted
            playerChoice = battleFaintHandler(player,player2,playerChoice, opponentChoice)
            if (playerChoice.isTrainerDefeated) break
            //Check if player switched pokemon
            playerChoice = battleSwitchHandler(player,playerChoice,false)
            //Check if opponent pokemon fainted
            opponentChoice = battleFaintHandler(player2,player,opponentChoice, playerChoice)
            if (opponentChoice.isTrainerDefeated) break
            //Check if opponent switch pokemon
            opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
            if (!isBattleFinished){
                playerChoice = choiceHandler(player,player2,playerChoice,opponentChoice)
                //Check if player switched pokemon
                playerChoice = battleSwitchHandler(player,playerChoice,false)
                opponentChoice = choiceHandler(player2,player,opponentChoice,playerChoice)
                //Check if opponent switch pokemon
                opponentChoice = battleSwitchHandler(player2,opponentChoice,false)
                println("+---Turn ${turnCount}---+")
                mainFightHandler(player,player2,playerChoice,opponentChoice)
                Thread.sleep(battleSpeed)
                turnCount++
            }
        }
        println("+-----------------------+")
        when{
            playerChoice.isTrainerDefeated -> println("${player2.trainerName} won the match!")
            opponentChoice.isTrainerDefeated == true -> println("${player.trainerName} won the match!")
        }
        println("+-----------------------+")
        Thread.sleep(battleSpeed)
    }
    //Main battle functions
    private fun battleFaintHandler(sourceTrainer:TrainerClass, targetTrainer:TrainerClass, playerChoice:playerChoiceData, opponentChoice: playerChoiceData):playerChoiceData{
        if(sourceTrainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkIfFainted()){
            println("${sourceTrainer.trainerName}'s ${sourceTrainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName} fainted!")
            sourceTrainer.currentPokemon.removePokemon(playerChoice.currentPokemonIndex)
            if(!sourceTrainer.currentPokemon.isEmpty()){
                if (playerChoice.isAI == true) {
                    val switchPlayerPokemonIndex = opponentSwitchSelector(targetTrainer,sourceTrainer,opponentChoice)
                    return playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex,playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
                if (playerChoice.isAI == false){
                    val switchPlayerPokemonIndex = switchSelector(sourceTrainer)
                    return playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex, playerChoice.isTrainerDefeated,playerChoice.isAI)
                }
            }
            else{
                println("${sourceTrainer.trainerName} has no more usable Pokemon")
                println("${sourceTrainer.trainerName} whited out!")
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
            faintCheck = playerPokemon.checkIfFainted()
            if(faintCheck) return
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            return
        }
        if (pokemonSpeed2 < pokemonSpeed1){
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            faintCheck = opponentPokemon.checkIfFainted()
            if(faintCheck) return
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
        println("${sourcePokemon.pokemonName} used ${sourceChoice.chosenMove?.getName()}!")
        val battleResult = calculateDamage(sourcePokemon,sourceChoice.chosenMove!!,targetPokemon)
        Thread.sleep(battleSpeed)
        when{
            battleResult.effectiveVal > 1.0F -> {
                println("It's super effective!")
                Thread.sleep(battleSpeed)
            }
            battleResult.effectiveVal < 1.0F -> {
                println("It's not very effective...")
                Thread.sleep(battleSpeed)
            }
        }

        if(battleResult.isCritical && battleResult.damageAmount > 0) {
            println("Critical hit!")
            Thread.sleep(battleSpeed)
        }

        when (battleResult.damageAmount <= 0){
            false -> {
                println("Dealt ${battleResult.damageAmount} HP of damage to ${targetPokemon.pokemonName}! (${targetPokemon.pokemonCurrentHP.toInt()} -> ${if (targetPokemon.pokemonCurrentHP - battleResult.damageAmount < 1){0}else{(targetPokemon.pokemonCurrentHP - battleResult.damageAmount).toInt()}})")
                Thread.sleep(battleSpeed)
            }
            true -> {
                println("${sourcePokemon.pokemonName}'s attack missed!")
                Thread.sleep(battleSpeed)
            }
        }

        //Reduce HP of target Pokemon
        targetPokemon.damageHP(battleResult.damageAmount)
    }private fun battleSwitchHandler(trainer:TrainerClass, playerChoice:playerChoiceData, isInitial: Boolean = false):playerChoiceData{
        if(playerChoice.switchToIndex != null){
            playerChoice.currentPokemonIndex = playerChoice.switchToIndex!!
            playerChoice.switchToIndex = null
            println("${trainer.trainerName} sent out ${trainer.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName}!")
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
        println("+--------------------+")
        println("${trainerData.trainerName}'s ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName} (LV ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).getLevel()}): ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().currentHP} / ${trainerData.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkHP().maxHP}")
        println("${trainerData.trainerName}, what will you do?")
        println("+-----------------+")
        println("|0 - FIGHT | 1 - Pokemon |")
        println("+-----------------+")
        print("CHOICE:")
        var isMenu: Boolean = true
        var choiceIndex: Int = 0
        while(isMenu){
            try{
                choiceIndex = readln().toInt()
                isMenu = false
            }catch(e: Exception){
                println("Try again")
                print("CHOICE:")
                continue
            }finally{
            }
        }
        when (choiceIndex){
            0 -> return playerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
            1 -> return playerChoiceData(null, pokemonIndex,switchSelector(trainerData),playerChoice.isTrainerDefeated,playerChoice.isAI)
            else -> return playerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null,playerChoice.isTrainerDefeated,playerChoice.isAI)
        }

        TODO()
    }
    private fun fightSelector(trainerData: TrainerClass, pokemonIndex: Int): PokemonMoveset{
        println("+--------------------+")
        println("What should ${trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonName} do?")
        println("+-----------------+")
        val moveList = trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList
        println("| 0 - ${moveList.getMove(0).getName()} Power: ${moveList.getMove(0).getPower()} PP: ${moveList.getMove(0).getPP().currentPP}/${moveList.getMove(0).getPP().maxPP} | 1 - ${moveList.getMove(1).getName()} Power: ${moveList.getMove(1).getPower()} PP: ${moveList.getMove(1).getPP().currentPP}/${moveList.getMove(1).getPP().maxPP} |")
        println("| 2 - ${moveList.getMove(2).getName()} Power: ${moveList.getMove(2).getPower()} PP: ${moveList.getMove(2).getPP().currentPP}/${moveList.getMove(2).getPP().maxPP} | 3 - ${moveList.getMove(3).getName()} Power: ${moveList.getMove(3).getPower()} PP: ${moveList.getMove(3).getPP().currentPP}/${moveList.getMove(3).getPP().maxPP} |")
        println("+-----------------+")
        print("CHOICE: ")
        var fightChoice = 0
        var isMenu = true
        while(isMenu){
            try{
                fightChoice = readln().toInt()
                isMenu = false
            }catch(e: Exception){
                println("Try again")
                print("CHOICE:")
                continue
            }finally{

            }
        }
        println("+-----------------------+")
        return trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList.getMove(fightChoice)
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
        if(  (currentHP <= (.50 * maxHP) && (0..50).contains((0..100).random())  ) || (0..20).contains((0..100).random())){
            switchIndex = opponentSwitchSelector(player,player2,playerChoice)
            didOpponentSwitch = true
            //If opponent switches to the same pokemon, cancel intent to switch
            if (switchIndex == opponentPokemonIndex){switchIndex = null; didOpponentSwitch = false}
        }

        while (x < 4 && didOpponentSwitch == false){
            when(player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).checkIfEmpty()){
                false -> effectivityList.add((effectivenessValue(playerPokemonType, player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getType())) * player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getPower())
                true -> effectivityList.add(-1F)
            }
            x++
        }
        while (x < 4 && didOpponentSwitch == true){
            when(player2.currentPokemon.getPokemon(switchIndex!!).pokemonMoveList.getMove(x).checkIfEmpty()){
                false -> effectivityList.add((effectivenessValue(playerPokemonType, player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getType())) * player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(x).getPower())
                true -> effectivityList.add(-1F)
            }
            x++
            didOpponentSwitch = false
        }
        val bestMoveIndex: Int = when (effectivityList.maxOrNull()){
            null -> {
                effectivityList.indexOf(effectivityList.random())
            }
            else -> {
                effectivityList.indexOf(effectivityList.maxOrNull())
            }
        }
        val bestMoveChoice = playerChoiceData(player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(bestMoveIndex),opponentPokemonIndex,switchIndex,opponentChoice.isTrainerDefeated,opponentChoice.isAI)
        return bestMoveChoice
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
    private fun switchSelector(trainerData:TrainerClass): Int{
        println("+--------------------+")
        println("Select a pokemon to switch to: ")
        println("+-----------------+")
        trainerData.listPokemon()
        print("CHOICE:")
        var switchChoice = 0
        var isMenu = true
        while(isMenu){
            try{
                switchChoice = readln().toInt()
                isMenu = false
            }catch(e: Exception){
                println("Try again")
                print("CHOICE:")
                continue
            }finally{

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
        val effectivenessValue =
            effectivenessValue(sourcePokemon.pokemonType, targetPokemon.pokemonType)
        //Check if it misses
        val basePower: Int
        when( ((0..sourceMove.getAccuracy()).contains((0..100).random())) ){
            true -> {basePower = sourceMove.getPower()}
            false -> {basePower = 0}
        }
        //Start Damage calculation
        val modifier1 = (((2F * sourcePokemon.pokemonLevel.toFloat()) + 10F) / 250F)
        val modifier2 = (sourcePokemon.pokemonATK.toFloat() / targetPokemon.pokemonDEF.toFloat())
        val baseDamage = basePower * STABvalue * effectivenessValue * critValue * listOf(0.85F,1F).random()
        val totalDamage = (modifier1 * modifier2) * baseDamage

        //Add to battleresult calc
        var result = battleResult(totalDamage.toInt(), critStatus, effectivenessValue,sourcePokemon.pokemonName,targetPokemon.pokemonName)
        return result
    }
    private fun isStab(type1: String, type2:String): Boolean{
        return type1 == type2
    }
    private fun isCritical(): Boolean{
        val chance = (0..100).random()
        if ((0..4).contains(chance)) return true
        else return false
    }
    private fun effectivenessValue(type1: String, type2: String): Float{
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
            type1 == "Water" && type2 == "Dragon" -> 0.5F

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
            type1 == "Normal" || type2 == "Normal" -> 1F

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

            else -> 1F
        }
    }

}
data class battleResult(var damageAmount: Int, var isCritical: Boolean, var effectiveVal: Float,var sourcePokemon: String, var targetPokemon:String)
data class playerChoiceData(var chosenMove: PokemonMoveset?, var currentPokemonIndex: Int, var switchToIndex: Int?, var isTrainerDefeated: Boolean = false, var isAI: Boolean = false)
