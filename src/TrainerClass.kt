
class TrainerClass(name:String) {

    val trainerName = name
    var currentPokemon = PokemonList<PokemonClass>()

    fun appraisePokemon(pokemonIndex: Int){
        val selectedPokemon = currentPokemon.getPokemon(pokemonIndex)
        selectedPokemon.appraisePokemon()
    }

    fun checkIfFainted(pokemonIndex: Int):Boolean{
        val selectedPokemon = currentPokemon.getPokemon(pokemonIndex)
        return selectedPokemon.checkIfFainted()
    }
    fun listPokemon(){
        var x = 0
        var pokemonLoop: PokemonClass
        while (x < currentPokemon.size()){
            pokemonLoop = currentPokemon.getPokemon(x)
            println("${x} - ${pokemonLoop.pokemonName} (LV ${pokemonLoop.getLevel()}) (${pokemonLoop.pokemonType})")
            x++
        }
    }
    fun createPokemon(){
        var isFinished = false
        println("+-----------------+")
        println("Pokemon Maker")
        println("+-----------------+")
        while (!isFinished){
            println("Input Pokemon Name: ")
            val nameInput = readln()
            println("You picked $nameInput, what a great choice!")
            println("Input Pokemon Type:")
            val typeInput = readln()
            println("The $typeInput type, powerful choice")
            println("Enter the level of your Pokemon: ")
            val levelInput = readln().toInt()
            println("You gave $levelInput rare candies to $nameInput. It seems happy.")
            val createdPokemon = PokemonClass(nameInput.toString(), typeInput.toString(),(levelInput))
            currentPokemon.addToParty(createdPokemon)
            println("Continue? (Y/N)")
            val choice = readln()
            if(choice == "N") isFinished = true
        }

    }
    fun generatePokemon(pokemonAmount:Int, movesAmount:Int, isSilent: Boolean){
        val pokemonNameFireList = listOf("Litten", "Torracat", "Incineroar", "Salandit", "Salazzle", "Oricorio (Baile Style)", "Turtonator", "Torkoal", "Alolan Marowak", "Heat Rotom")
        val pokemonNameWaterList = listOf("Popplio", "Brionne", "Primarina", "Wingull", "Pelipper", "Wishiwashi", "Mareanie", "Toxapex", "Dewpider", "Araquanid")
        val pokemonNameGrassList = listOf("Rowlet", "Dartrix", "Decidueye", "Fomantis", "Lurantis", "Morelull", "Shiinotic", "Bounsweet", "Steenee", "Tsareena")
        val pokemonNameNormalList = listOf("Yungoos", "Gumshoos", "Pikipek", "Trumbeak", "Toucannon", "Stufful", "Bewear", "Type: Null", "Silvally", "Komala")

        val pokemonMoveFireList = listOf("Ember", "Flame Burst", "Flamethrower", "Fire Blast", "Flare Blitz", "Heat Wave", "Inferno", "Blaze Kick", "Blue Flare", "Eruption")
        val pokemonPowerFireList = listOf(40, 70, 90, 110, 120, 95, 100, 85, 130, 150)
        val pokemonMoveFirePPList = listOf(25, 15, 15, 5, 15, 10, 5, 10, 5, 5)
//        val pokemonMoveFirePPList = listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val pokemonMoveFireAccuracyList = listOf(100, 100, 100, 85, 100, 90, 50, 90, 85, 100)



        val pokemonMoveWaterList = listOf("Water Gun", "Hydro Pump", "Surf", "Scald", "Waterfall", "Aqua Tail", "Aqua Jet", "Liquidation", "Origin Pulse", "Bubble Beam")
        val pokemonPowerWaterList = listOf(40, 110, 90, 80, 80, 90, 40, 85, 110, 65)
        val pokemonMoveWaterPPList = listOf(25, 5, 15, 15, 15, 10, 20, 10, 10, 20)
//        val pokemonMoveWaterPPList = listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val pokemonMoveWaterAccuracyList = listOf(100, 80, 100, 100, 100, 90, 100, 100, 85, 100)


        val pokemonMoveNormalList = listOf("Tackle", "Hyper Beam", "Double-Edge", "Body Slam", "Take Down", "Swift", "Echoed Voice", "Hidden Power (Normal)", "Giga Impact", "Quick Attack")
        val pokemonPowerNormalList = listOf(40, 150, 120, 85, 90, 60, 40, 60, 150, 40)
        val pokemonMoveNormalPPList = listOf(35, 5, 15, 15, 20, 20, 15, 15, 5, 30)
//        val pokemonMoveNormalPPList = listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val pokemonMoveNormalAccuracyList = listOf(100, 90, 100, 100, 85, 100, 100, 100, 90, 100)
//        val pokemonMoveNormalAccuracyList = listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)


        val pokemonMoveGrassList = listOf("Vine Whip", "Razor Leaf", "Leaf Blade", "Solar Beam", "Energy Ball", "Giga Drain", "Leaf Storm", "Seed Bomb", "Mega Drain", "Grass Knot")
        val pokemonPowerGrassList = listOf(45, 55, 90, 120, 90, 75, 130, 80, 40, (50..120).random())
        val pokemonMoveGrassPPList = listOf(25, 25, 15, 10, 10, 10, 5, 15, 15, 20)
//        val pokemonMoveGrassPPList = listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val pokemonMoveGrassAccuracyList = listOf(100, 95, 100, 100, 100, 100, 90, 100, 100, 100)

        //randomize
        //Generate
        var i = 0
        while (i < pokemonAmount){
            var typeRand = (0..3).random()
            if (typeRand == 0){
                //Fire
                val createdPokemon = PokemonClass(pokemonNameFireList.random(), "Fire", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveFireList.get(rand),"Fire",pokemonPowerFireList.get(rand),pokemonMoveFirePPList.get(rand),pokemonMoveFireAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}! $j")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 1){
                //Water
                val createdPokemon = PokemonClass(pokemonNameWaterList.random(), "Water", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveWaterList.get(rand),"Fire",pokemonPowerWaterList.get(rand),pokemonMoveWaterPPList.get(rand),pokemonMoveWaterAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 2){
                //Grass
                val createdPokemon = PokemonClass(pokemonNameGrassList.random(), "Grass", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveGrassList.get(rand),"Fire",pokemonPowerGrassList.get(rand),pokemonMoveGrassPPList.get(rand),pokemonMoveGrassAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 3){
                //Grass
                val createdPokemon = PokemonClass(pokemonNameNormalList.random(), "Normal", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveNormalList.get(rand),"Fire",pokemonPowerNormalList.get(rand),pokemonMoveNormalPPList.get(rand),pokemonMoveNormalAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }

        }

    }
    fun addMoves(){
        var isFinished = false
        println("+-----------------+")
        println("Move Tutor")
        println("+-----------------+")
        while(!isFinished){
            println("Which Pokemon?")
            listPokemon()
            val pokemonInput = readln().toInt()
            println("Move name: ")
            val moveName = readln()
            println("Move type: ")
            val moveType = readln()
            println("Base power: ")
            val basePower = readln().toInt()
            println("Amount of PP: ")
            val ppAmount = readln().toInt()
            println("Amount of Accuracy: ")
            val accAmount = readln().toInt()
            val selectedPokemon = currentPokemon.getPokemon(pokemonInput)
            selectedPokemon.pokemonMoveList.push(PokemonMoveset(moveName,moveType,basePower,ppAmount,accAmount))
            println("${selectedPokemon.pokemonName} learned ${moveName}!")
            println("Continue? (Y/N)")
            val choice = readln()
            if(choice == "N") isFinished = true
        }

    }
}

class PokemonList<PokemonClass>(vararg pokemon: PokemonClass){

    private val elements = pokemon.toMutableList()
    fun addToParty(element: PokemonClass){
        if(elements.size < 6) {
            elements.add(element)

        }else{
            println("PROF. OAK: You can't add more than 6 Pokemon in your party...")
        }
    }

    fun getPokemon(x: Int) = elements[x]

    fun peek(): PokemonClass = elements.last()

    fun removePokemon(x: Int): PokemonClass = elements.removeAt(x)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString() = "MutableStack(${elements.joinToString()})"
}