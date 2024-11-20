import com.sun.tools.javac.Main

class TrainerClass(name:String, isAI:Boolean = false) {

    val trainerName = name
    val isAI = isAI
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
        val pokemonMoveFireList = listOf("Ember", "Flame Burst", "Flamethrower", "Fire Blast", "Flare Blitz", "Heat Wave", "Inferno", "Blaze Kick", "Blue Flare", "Eruption")
        val pokemonPowerFireList = listOf(40, 70, 90, 110, 120, 95, 100, 85, 130, 150)
        val pokemonMoveFirePPList = listOf(25, 15, 15, 5, 15, 10, 5, 10, 5, 5)
        val pokemonMoveFireAccuracyList = listOf(100, 100, 100, 85, 100, 90, 50, 90, 85, 100)
        val FirePokemonList = listOf(pokemonNameFireList,pokemonMoveFireList,pokemonPowerFireList,pokemonMoveFirePPList,pokemonMoveFireAccuracyList)

        val pokemonNameWaterList = listOf("Popplio", "Brionne", "Primarina", "Wingull", "Pelipper", "Wishiwashi", "Mareanie", "Toxapex", "Dewpider", "Araquanid")
        val pokemonMoveWaterList = listOf("Water Gun", "Hydro Pump", "Surf", "Scald", "Waterfall", "Aqua Tail", "Aqua Jet", "Liquidation", "Origin Pulse", "Bubble Beam")
        val pokemonPowerWaterList = listOf(40, 110, 90, 80, 80, 90, 40, 85, 110, 65)
        val pokemonMoveWaterPPList = listOf(25, 5, 15, 15, 15, 10, 20, 10, 10, 20)
        val pokemonMoveWaterAccuracyList = listOf(100, 80, 100, 100, 100, 90, 100, 100, 85, 100)
        val WaterPokemonList = listOf(pokemonNameWaterList,pokemonMoveWaterList,pokemonPowerWaterList,pokemonMoveWaterPPList,pokemonMoveWaterAccuracyList)

        val pokemonNameNormalList = listOf("Yungoos", "Gumshoos", "Pikipek", "Trumbeak", "Toucannon", "Stufful", "Bewear", "Type: Null", "Silvally", "Komala")
        val pokemonMoveNormalList = listOf("Tackle", "Hyper Beam", "Double-Edge", "Body Slam", "Take Down", "Swift", "Echoed Voice", "Hidden Power (Normal)", "Giga Impact", "Quick Attack")
        val pokemonPowerNormalList = listOf(40, 150, 120, 85, 90, 60, 40, 60, 150, 40)
        val pokemonMoveNormalPPList = listOf(35, 5, 15, 15, 20, 20, 15, 15, 5, 30)
        val pokemonMoveNormalAccuracyList = listOf(100, 90, 100, 100, 85, 100, 100, 100, 90, 100)
        val NormalPokemonList = listOf(pokemonNameNormalList,pokemonMoveNormalList,pokemonPowerNormalList,pokemonMoveNormalPPList,pokemonMoveNormalAccuracyList)

        val pokemonNameGrassList = listOf("Rowlet", "Dartrix", "Decidueye", "Fomantis", "Lurantis", "Morelull", "Shiinotic", "Bounsweet", "Steenee", "Tsareena")
        val pokemonMoveGrassList = listOf("Vine Whip", "Razor Leaf", "Leaf Blade", "Solar Beam", "Energy Ball", "Giga Drain", "Leaf Storm", "Seed Bomb", "Mega Drain", "Grass Knot")
        val pokemonPowerGrassList = listOf(45, 55, 90, 120, 90, 75, 130, 80, 40, (50..120).random())
        val pokemonMoveGrassPPList = listOf(25, 25, 15, 10, 10, 10, 5, 15, 15, 20)
        val pokemonMoveGrassAccuracyList = listOf(100, 95, 100, 100, 100, 100, 90, 100, 100, 100)
        val GrassPokemonList = listOf(pokemonNameGrassList,pokemonMoveGrassList,pokemonPowerGrassList,pokemonMoveGrassPPList,pokemonMoveGrassAccuracyList)

        // Bug
        val pokemonNameBugList = listOf("Grubbin", "Charjabug", "Vikavolt", "Cutiefly", "Ribombee", "Dewpider", "Araquanid", "Wimpod", "Golisopod", "Fomantis")
        val pokemonMoveBugList = listOf("Bug Bite", "X-Scissor", "Leech Life", "Signal Beam", "Struggle Bug", "Infestation", "Bug Buzz", "Lunge", "Fury Cutter", "U-turn")
        val pokemonPowerBugList = listOf(60, 80, 80, 75, 50, 20, 90, 80, 40, 70)
        val pokemonMoveBugPPList = listOf(20, 15, 15, 15, 20, 20, 10, 15, 20, 20)
        val pokemonMoveBugAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 95, 100)
        val BugPokemonList = listOf(pokemonNameBugList,pokemonMoveBugList,pokemonPowerBugList,pokemonMoveBugPPList,pokemonMoveBugAccuracyList)

// Rock
        val pokemonNameRockList = listOf("Rockruff", "Lycanroc (Midday Form)", "Lycanroc (Midnight Form)", "Roggenrola", "Boldore", "Gigalith", "Carbink", "Minior", "Dwebble", "Crustle")
        val pokemonMoveRockList = listOf("Rock Throw", "Rock Slide", "Stone Edge", "Rock Tomb", "Power Gem", "Ancient Power", "Smack Down", "Rock Blast", "Head Smash", "Rock Polish")
        val pokemonPowerRockList = listOf(50, 75, 100, 60, 80, 60, 50, 25, 150, 0)
        val pokemonMoveRockPPList = listOf(15, 10, 5, 15, 20, 5, 15, 10, 5, 20)
        val pokemonMoveRockAccuracyList = listOf(90, 90, 80, 95, 100, 100, 100, 90, 80, 100)
        val RockPokemonList = listOf(pokemonNameRockList,pokemonMoveRockList,pokemonPowerRockList,pokemonMoveRockPPList,pokemonMoveRockAccuracyList)

// Flying
        val pokemonNameFlyingList = listOf("Pikipek", "Trumbeak", "Toucannon", "Oricorio (Pom-Pom Style)", "Oricorio (Pa'u Style)", "Oricorio (Sensu Style)", "Rowlet", "Dartrix", "Decidueye", "Wingull")
        val pokemonMoveFlyingList = listOf("Peck", "Pluck", "Wing Attack", "Aerial Ace", "Sky Attack", "Air Cutter", "Hurricane", "Fly", "Brave Bird", "Roost")
        val pokemonPowerFlyingList = listOf(35, 60, 60, 60, 140, 60, 110, 90, 120, 0)
        val pokemonMoveFlyingPPList = listOf(35, 20, 35, 20, 5, 25, 10, 15, 15, 10)
        val pokemonMoveFlyingAccuracyList = listOf(100, 100, 100, 100, 90, 95, 70, 95, 100, 0)
        val FlyingPokemonList = listOf(pokemonNameFlyingList,pokemonMoveFlyingList,pokemonPowerFlyingList,pokemonMoveFlyingPPList,pokemonMoveFlyingAccuracyList)

// Steel
        val pokemonNameSteelList = listOf("Meltan", "Melmetal", "Magnezone", "Skarmory", "Klink", "Klang", "Klinklang", "Honedge", "Doublade", "Aegislash")
        val pokemonMoveSteelList = listOf("Iron Tail", "Iron Head", "Steel Wing", "Flash Cannon", "Heavy Slam", "Gyro Ball", "Meteor Mash", "Bullet Punch", "Steel Beam", "Autotomize")
        val pokemonPowerSteelList = listOf(100, 80, 70, 80, 70, 70, 90, 40, 140, 0)
        val pokemonMoveSteelPPList = listOf(15, 15, 25, 10, 10, 5, 10, 30, 5, 15)
        val pokemonMoveSteelAccuracyList = listOf(75, 100, 90, 100, 100, 100, 90, 100, 95, 0)
        val SteelPokemonList = listOf(pokemonNameSteelList,pokemonMoveSteelList,pokemonPowerSteelList,pokemonMoveSteelPPList,pokemonMoveSteelAccuracyList)

// Electric
        val pokemonNameElectricList = listOf("Pichu", "Pikachu", "Raichu", "Alolan Raichu", "Magnemite", "Magneton", "Magnezone", "Voltorb", "Electrode", "Rotom")
        val pokemonMoveElectricList = listOf("Thunder Shock", "Thunderbolt", "Thunder", "Volt Switch", "Wild Charge", "Zap Cannon", "Electro Ball", "Discharge", "Thunder Punch", "Volt Tackle")
        val pokemonPowerElectricList = listOf(40, 90, 110, 70, 90, 120, 70, 80, 75, 120)
        val pokemonMoveElectricPPList = listOf(30, 15, 10, 20, 15, 5, 10, 15, 15, 15)
        val pokemonMoveElectricAccuracyList = listOf(100, 100, 70, 100, 100, 50, 100, 100, 100, 100)
        val ElectricPokemonList = listOf(pokemonNameElectricList,pokemonMoveElectricList,pokemonPowerElectricList,pokemonMoveElectricPPList,pokemonMoveElectricAccuracyList)

// Psychic
        val pokemonNamePsychicList = listOf("Abra", "Kadabra", "Alakazam", "Espeon", "Mewtwo", "Mew", "Slowpoke", "Slowbro", "Slowking", "Wobbuffet")
        val pokemonMovePsychicList = listOf("Confusion", "Psybeam", "Psychic", "Psyshock", "Zen Headbutt", "Psychic Fangs", "Psystrike", "Future Sight", "Teleport", "Calm Mind")
        val pokemonPowerPsychicList = listOf(50, 65, 90, 80, 80, 85, 100, 120, 0, 0)
        val pokemonMovePsychicPPList = listOf(25, 20, 10, 10, 15, 10, 5, 10, 20, 20)
        val pokemonMovePsychicAccuracyList = listOf(100, 100, 100, 100, 90, 100, 100, 100, 0, 0)
        val PsychicPokemonList = listOf(pokemonNamePsychicList,pokemonMovePsychicList,pokemonPowerPsychicList,pokemonMovePsychicPPList,pokemonMovePsychicAccuracyList)

// Dark
        val pokemonNameDarkList = listOf("Murkrow", "Honchkrow", "Sableye", "Carvanha", "Sharpedo", "Poochyena", "Mightyena", "Absol", "Inkay", "Malamar")
        val pokemonMoveDarkList = listOf("Bite", "Crunch", "Dark Pulse", "Night Slash", "Sucker Punch", "Thief", "Pursuit", "Assurance", "Foul Play", "Snarl")
        val pokemonPowerDarkList = listOf(60, 80, 80, 70, 70, 60, 40, 60, 70, 55)
        val pokemonMoveDarkPPList = listOf(25, 15, 15, 15, 5, 25, 20, 10, 15, 15)
        val pokemonMoveDarkAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 95)
        val DarkPokemonList = listOf(pokemonNameDarkList,pokemonMoveDarkList,pokemonPowerDarkList,pokemonMoveDarkPPList,pokemonMoveDarkAccuracyList)

// Ghost
        val pokemonNameGhostList = listOf("Gastly", "Haunter", "Gengar", "Misdreavus", "Mismagius", "Shuppet", "Banette", "Duskull", "Dusclops", "Dusknoir")
        val pokemonMoveGhostList = listOf("Lick", "Shadow Ball", "Hex", "Shadow Punch", "Shadow Sneak", "Night Shade", "Shadow Claw", "Ominous Wind", "Phantom Force", "Curse")
        val pokemonPowerGhostList = listOf(30, 80, 65, 60, 40, 70, 70, 60, 90, 0)
        val pokemonMoveGhostPPList = listOf(30, 15, 10, 20, 30, 15, 15, 5, 10, 10)
        val pokemonMoveGhostAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 100)
        val GhostPokemonList = listOf(pokemonNameGhostList,pokemonMoveGhostList,pokemonPowerGhostList,pokemonMoveGhostPPList,pokemonMoveGhostAccuracyList)

// Fairy
        val pokemonNameFairyList = listOf("Sylveon", "Florges", "Mimikyu", "Togekiss", "Granbull", "Slurpuff", "Aromatisse", "Mawile", "Klefki", "Tapu Koko")
        val pokemonMoveFairyList = listOf("Moonblast", "Dazzling Gleam", "Play Rough", "Draining Kiss", "Fairy Wind", "Sparkling Aria", "Misty Terrain", "Sweet Kiss", "Disarming Voice", "Misty Explosion")
        val pokemonPowerFairyList = listOf(95, 80, 90, 50, 40, 90, 95, 70, 40, 100)
        val pokemonMoveFairyPPList = listOf(15, 10, 10, 10, 30, 10, 10, 10, 15, 5)
        val pokemonMoveFairyAccuracyList = listOf(100, 100, 90, 100, 100, 100, 100, 75, 100, 100)
        val FairyPokemonList = listOf(pokemonNameFairyList,pokemonMoveFairyList,pokemonPowerFairyList,pokemonMoveFairyPPList,pokemonMoveFairyAccuracyList)

        val pokemonNameGroundList = listOf("Diglett", "Dugtrio", "Sandshrew", "Sandslash", "Cubone", "Marowak", "Gligar", "Golem", "Mudbray", "Mudsdale")
        val pokemonMoveGroundList = listOf("Earthquake", "Bulldoze", "Earth Power", "Mud-Slap", "Sand Tomb", "Magnitude", "Dig", "Bonemerang", "Mud Shot", "Precipice Blades")
        val pokemonPowerGroundList = listOf(100, 60, 90, 20, 35, 50, 80, 50, 55, 120)
        val pokemonMoveGroundPPList = listOf(10, 20, 10, 10, 15, 30, 10, 10, 15, 10)
        val pokemonMoveGroundAccuracyList = listOf(100, 100, 100, 100, 85, 100, 100, 90, 95, 85)
        val GroundPokemonList = listOf(pokemonNameGroundList, pokemonMoveGroundList, pokemonPowerGroundList, pokemonMoveGroundPPList, pokemonMoveGroundAccuracyList)

        val pokemonNameDragonList = listOf("Dratini", "Dragonair", "Dragonite", "Bagon", "Shelgon", "Salamence", "Gible", "Gabite", "Garchomp", "Axew")
        val pokemonMoveDragonList = listOf("Dragon Claw", "Dragon Tail", "Dragon Pulse", "Outrage", "Draco Meteor", "Dragon Rush", "Dragon Breath", "Dual Chop", "Twister", "Roar of Time")
        val pokemonPowerDragonList = listOf(80, 60, 85, 120, 130, 100, 60, 40, 40, 150)
        val pokemonMoveDragonPPList = listOf(15, 10, 10, 10, 5, 10, 20, 15, 20, 5)
        val pokemonMoveDragonAccuracyList = listOf(100, 90, 100, 100, 90, 75, 100, 90, 100, 90)
        val DragonPokemonList = listOf(pokemonNameDragonList, pokemonMoveDragonList, pokemonPowerDragonList, pokemonMoveDragonPPList, pokemonMoveDragonAccuracyList)



        val MainPokemonList = listOf(FirePokemonList, WaterPokemonList, GrassPokemonList, NormalPokemonList, BugPokemonList, RockPokemonList, FlyingPokemonList, SteelPokemonList, ElectricPokemonList, PsychicPokemonList, DarkPokemonList, GhostPokemonList, FairyPokemonList, GroundPokemonList, DragonPokemonList)
        val PokemonTypeList = listOf("Fire","Water","Grass","Normal","Bug","Rock","Flying","Steel","Electric","Psychic","Dark","Ghost","Fairy","Ground", "Dragon")

        var i = 0
        while (i < pokemonAmount){
            val typeRand = MainPokemonList.indices.random()
            val nameRand = MainPokemonList[typeRand].indices.random()
            val pokemonName = MainPokemonList[typeRand][0][nameRand].toString()
            val createdPokemon = PokemonClass(pokemonName, PokemonTypeList.get(typeRand), 100)
            if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
            while(createdPokemon.pokemonMoveList.size() < movesAmount){
                var matchFound = false
                val rand = MainPokemonList[typeRand][1].indices.random()
                val createdMove = PokemonMoveset(MainPokemonList[typeRand][1][rand].toString(),PokemonTypeList.get(typeRand),MainPokemonList[typeRand][2][rand].toString().toInt(),MainPokemonList[typeRand][3][rand].toString().toInt(),MainPokemonList[typeRand][4][rand].toString().toInt())
                var k = 0
                while (k < createdPokemon.pokemonMoveList.size()){
                    if (createdPokemon.pokemonMoveList.getMove(k).getName() != createdMove.getName()){
                        k++
                    }
                    else{
                        matchFound = true
                        k++
                    }
                }
                if (matchFound){continue}
                createdPokemon.pokemonMoveList.push(createdMove)
                if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(createdPokemon.pokemonMoveList.size() - 1).getName()}!")
            }
            if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
            currentPokemon.addToParty(createdPokemon)
            i++
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