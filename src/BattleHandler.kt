class BattleHandler {

    fun battleMain(player:TrainerClass, player2:TrainerClass){
        var isBattleFinished = false
        var turnCount = 0
        println("+--------------------+")
        Thread.sleep(50)
        println("BATTLE START")
        Thread.sleep(50)
        println("+--------------------+")
        Thread.sleep(50)
        println("${player.trainerName} vs. ${player2.trainerName}")
        Thread.sleep(50)
        println("+--------------------+")
        Thread.sleep(500)
        //Send out Pokemon
        var initialPlayerPokemonIndex = switchSelector(player)
        var playerChoice = playerChoiceData(null,initialPlayerPokemonIndex,initialPlayerPokemonIndex)
        var initialOpponentPokemonIndex = opponentSwitchSelector(player,player2,playerChoice)
        var opponentChoice = playerChoiceData(null,initialOpponentPokemonIndex,initialOpponentPokemonIndex)
        while (!isBattleFinished){
            //Check if player pokemon fainted
            if(player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).checkIfFainted()){
                println("${player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName} fainted!")
                player.currentPokemon.removePokemon(playerChoice.currentPokemonIndex)
                if(!player.currentPokemon.isEmpty()){
                    val switchPlayerPokemonIndex = switchSelector(player)
                    playerChoice = playerChoiceData(null,playerChoice.currentPokemonIndex,switchPlayerPokemonIndex)
                }
                else{
                    println("+-----------------------+")
                    println("${player.trainerName} has no more usable Pokemon")
                    println("${player.trainerName} whited out!")
                    println("+-----------------------+")
                    isBattleFinished = true
                }
            }
            //Check if player switched pokemon
            if (playerChoice.switchToIndex != null){
                playerChoice.currentPokemonIndex = playerChoice.switchToIndex!!
                playerChoice.switchToIndex = null
                println("+-----------------------+")
                println("${player.trainerName} sent out ${player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName}!")
                println("+-----------------------+")
            }
            //Check if opponent pokemon fainted
            if(player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).checkIfFainted()){
                println("Opponent's ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).pokemonName} fainted!")
                player2.currentPokemon.removePokemon(opponentChoice.currentPokemonIndex)
                if(!player2.currentPokemon.isEmpty()){
                    val switchPlayerPokemonIndex = opponentSwitchSelector(player,player2,playerChoice)
                    opponentChoice = playerChoiceData(null,opponentChoice.currentPokemonIndex,switchPlayerPokemonIndex)
                }
                else{
                    println("+-----------------------+")
                    println("${player2.trainerName} has no more usable Pokemon")
                    println("${player2.trainerName} whited out!")
                    println("+-----------------------+")
                    isBattleFinished = true
                }
            }
            //Check if opponent switch pokemon
            if (opponentChoice.switchToIndex != null){
                opponentChoice.currentPokemonIndex = opponentChoice.switchToIndex!!
                opponentChoice.switchToIndex = null
                println("+-----------------------+")
                println("${player2.trainerName} sent out ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).pokemonName}!")
                println("+-----------------------+")
            }
            if (!isBattleFinished){
                playerChoice = playerHandler(player,playerChoice)
                //Check if player switched pokemon
                if (playerChoice.switchToIndex != null){
                    println("+-----------------------+")
                    println("${player.trainerName} withdrew ${player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName}!")
                    println("+-----------------------+")
                    playerChoice.currentPokemonIndex = playerChoice.switchToIndex!!
                    playerChoice.switchToIndex = null
                    println("+-----------------------+")
                    println("${player.trainerName} sent out ${player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName}!")
                    println("+-----------------------+")
                }
                opponentChoice = opponentFightHandler(player,player2,playerChoice,opponentChoice)
                //Check if opponent switch pokemon
                if (opponentChoice.switchToIndex != null){
                    println("+-----------------------+")
                    println("${player2.trainerName} withdrew ${player2.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonName}!")
                    println("+-----------------------+")
                    opponentChoice.currentPokemonIndex = opponentChoice.switchToIndex!!
                    opponentChoice.switchToIndex = null
                    println("+-----------------------+")
                    println("${player2.trainerName} sent out ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).pokemonName}!")
                    println("+-----------------------+")
                }
                println("+---Turn ${turnCount}---+")
                println("Opponent ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).pokemonName} (LV ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).getLevel()}): ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).checkHP().currentHP} / ${player2.currentPokemon.getPokemon(opponentChoice.currentPokemonIndex).checkHP().maxHP}")
                println("+-----------------------+")
                mainFightHandler(player,player2,playerChoice,opponentChoice)
                turnCount++
            }
        }
    }

     fun playerHandler(trainerData: TrainerClass, playerChoice: playerChoiceData): playerChoiceData{
         val pokemonIndex = playerChoice.currentPokemonIndex
        println("+--------------------+")
        println("What will you do?")
         println("${trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonName}: ${trainerData.currentPokemon.getPokemon(pokemonIndex).checkHP().currentHP} / ${trainerData.currentPokemon.getPokemon(pokemonIndex).checkHP().maxHP} HP")
        println("+-----------------+")
        println("|0 - FIGHT | 1 - Pokemon |")
        println("+-----------------+")
        print("CHOICE:")
        val choiceIndex = readln().toInt()
        when (choiceIndex){
            0 -> return playerChoiceData(fightSelector(trainerData, pokemonIndex), pokemonIndex, null)
            1 -> return playerChoiceData(null, pokemonIndex,switchSelector(trainerData))
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
        val fightChoice = readln().toInt()
        println("+-----------------------+")
        return trainerData.currentPokemon.getPokemon(pokemonIndex).pokemonMoveList.getMove(fightChoice)
    }

    private fun mainFightHandler(player:TrainerClass, player2:TrainerClass, playerChoice:playerChoiceData, playerChoice2:playerChoiceData){
        val playerPokemon = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex)
        val opponentPokemon = player2.currentPokemon.getPokemon(playerChoice2.currentPokemonIndex)
        var faintCheck = false
        //Compare pokemon speed stat
        val pokemonSpeed1 = playerPokemon.pokemonSPD
        val pokemonSpeed2 = opponentPokemon.pokemonSPD
        if (pokemonSpeed2 > pokemonSpeed1){
            damageHandler(opponentPokemon,playerPokemon,playerChoice2)
            faintCheck = playerPokemon.checkIfFainted()
            if(faintCheck) return
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            return
        }
        if (pokemonSpeed2 < pokemonSpeed1){
            if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
            faintCheck = opponentPokemon.checkIfFainted()
            if(faintCheck) return
            damageHandler(opponentPokemon,playerPokemon,playerChoice2)
            return
        }
        else{
            when((0..1).random()){
                0 -> {
                    damageHandler(opponentPokemon,playerPokemon,playerChoice2)
                    faintCheck = playerPokemon.checkIfFainted()
                    if(faintCheck) return
                    if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
                    return
                }
                1 -> {
                    if (playerChoice.chosenMove != null) damageHandler(playerPokemon,opponentPokemon,playerChoice)
                    faintCheck = opponentPokemon.checkIfFainted()
                    if(faintCheck) return
                    damageHandler(opponentPokemon,playerPokemon,playerChoice2)
                    return
                }
            }
        }
    }

    private fun damageHandler(sourcePokemon: PokemonClass, targetPokemon: PokemonClass,sourceChoice: playerChoiceData){
        sourceChoice.chosenMove?.removePP()
        println("+-----------------------+")
        println("${sourcePokemon.pokemonName} used ${sourceChoice.chosenMove?.getName()}!")
        println("+-----------------------+")
        val battleResult = calculateDamage(sourcePokemon,sourceChoice.chosenMove!!,targetPokemon)

        when(battleResult.effectiveVal){
            2.0F -> {
                println("+-----------------------+")
                println("It's super effective!")
                println("+-----------------------+")
            }
            0.5F -> {
                println("+-----------------------+")
                println("It's not very effective...")
                println("+-----------------------+")
            }
        }

        if(battleResult.isCritical) {
            println("+-----------------------+")
            println("Critical hit!")
            println("+-----------------------+")
        }

        when (battleResult.damageAmount <= 0){
            false -> {
                println("+-----------------------+")
                println("Dealt ${battleResult.damageAmount} HP of damage to ${targetPokemon.pokemonName}! (${targetPokemon.pokemonCurrentHP.toInt()} -> ${if (targetPokemon.pokemonCurrentHP - battleResult.damageAmount < 1){0}else{(targetPokemon.pokemonCurrentHP - battleResult.damageAmount).toInt()}})")
                println("+-----------------------+")
            }
            true -> {
                println("+-----------------------+")
                println("${sourcePokemon.pokemonName}'s attack missed!")
                println("+-----------------------+")
            }
        }

        //Reduce HP of target Pokemon
        targetPokemon.damageHP(battleResult.damageAmount)
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

        if(currentHP <= (.50 * maxHP) && (0..30).contains((0..100).random())){
            //If opponent's Pokemon is less than 30%, switch to type that is most effective against player 30% of the time
            switchIndex = opponentSwitchSelector(player,player2,playerChoice)
            didOpponentSwitch = true
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
        val bestMoveChoice = playerChoiceData(player2.currentPokemon.getPokemon(opponentPokemonIndex).pokemonMoveList.getMove(bestMoveIndex),opponentPokemonIndex,switchIndex)
        return bestMoveChoice
    }

    private fun opponentSwitchSelector(player:TrainerClass, player2:TrainerClass, playerChoice:playerChoiceData):Int{
        val playerPokemonType = player.currentPokemon.getPokemon(playerChoice.currentPokemonIndex).pokemonType
        var x = 0
        val effectivityList = mutableListOf<Float>()
        while (x < player2.currentPokemon.size()){
            effectivityList.add((effectivenessValue(player2.currentPokemon.getPokemon(x).pokemonType, playerPokemonType)))
            x++
        }
        val bestPokemonIndex = effectivityList.indexOf(effectivityList.maxOrNull())
        return bestPokemonIndex
    }

    private fun switchSelector(trainerData:TrainerClass): Int{
        println("+--------------------+")
        println("Select a pokemon to switch to: ")
        println("+-----------------+")
        trainerData.listPokemon()
        print("CHOICE:")
        val switchChoice = readln().toInt()
        return switchChoice
    }

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
data class playerChoiceData(var chosenMove: PokemonMoveset?, var currentPokemonIndex: Int, var switchToIndex: Int?)
