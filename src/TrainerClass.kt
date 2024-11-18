
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

        val pokemonNameWaterList = listOf("Popplio", "Brionne", "Primarina", "Wingull", "Pelipper", "Wishiwashi", "Mareanie", "Toxapex", "Dewpider", "Araquanid")
        val pokemonNameGrassList = listOf("Rowlet", "Dartrix", "Decidueye", "Fomantis", "Lurantis", "Morelull", "Shiinotic", "Bounsweet", "Steenee", "Tsareena")
        val pokemonNameNormalList = listOf("Yungoos", "Gumshoos", "Pikipek", "Trumbeak", "Toucannon", "Stufful", "Bewear", "Type: Null", "Silvally", "Komala")

        val pokemonNameFireList = listOf("Litten", "Torracat", "Incineroar", "Salandit", "Salazzle", "Oricorio (Baile Style)", "Turtonator", "Torkoal", "Alolan Marowak", "Heat Rotom")
        val pokemonMoveFireList = listOf("Ember", "Flame Burst", "Flamethrower", "Fire Blast", "Flare Blitz", "Heat Wave", "Inferno", "Blaze Kick", "Blue Flare", "Eruption")
        val pokemonPowerFireList = listOf(40, 70, 90, 110, 120, 95, 100, 85, 130, 150)
        val pokemonMoveFirePPList = listOf(25, 15, 15, 5, 15, 10, 5, 10, 5, 5)
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

        // Bug
        val pokemonNameBugList = listOf("Grubbin", "Charjabug", "Vikavolt", "Cutiefly", "Ribombee", "Dewpider", "Araquanid", "Wimpod", "Golisopod", "Fomantis")
        val pokemonMoveBugList = listOf("Bug Bite", "X-Scissor", "Leech Life", "Signal Beam", "Struggle Bug", "Infestation", "Bug Buzz", "Lunge", "Fury Cutter", "U-turn")
        val pokemonPowerBugList = listOf(60, 80, 80, 75, 50, 20, 90, 80, 40, 70)
        val pokemonMoveBugPPList = listOf(20, 15, 15, 15, 20, 20, 10, 15, 20, 20)
        val pokemonMoveBugAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 95, 100)

// Rock
        val pokemonNameRockList = listOf("Rockruff", "Lycanroc (Midday Form)", "Lycanroc (Midnight Form)", "Roggenrola", "Boldore", "Gigalith", "Carbink", "Minior", "Dwebble", "Crustle")
        val pokemonMoveRockList = listOf("Rock Throw", "Rock Slide", "Stone Edge", "Rock Tomb", "Power Gem", "Ancient Power", "Smack Down", "Rock Blast", "Head Smash", "Rock Polish")
        val pokemonPowerRockList = listOf(50, 75, 100, 60, 80, 60, 50, 25, 150, 0)
        val pokemonMoveRockPPList = listOf(15, 10, 5, 15, 20, 5, 15, 10, 5, 20)
        val pokemonMoveRockAccuracyList = listOf(90, 90, 80, 95, 100, 100, 100, 90, 80, 100)

// Flying
        val pokemonNameFlyingList = listOf("Pikipek", "Trumbeak", "Toucannon", "Oricorio (Pom-Pom Style)", "Oricorio (Pa'u Style)", "Oricorio (Sensu Style)", "Rowlet", "Dartrix", "Decidueye", "Wingull")
        val pokemonMoveFlyingList = listOf("Peck", "Pluck", "Wing Attack", "Aerial Ace", "Sky Attack", "Air Cutter", "Hurricane", "Fly", "Brave Bird", "Roost")
        val pokemonPowerFlyingList = listOf(35, 60, 60, 60, 140, 60, 110, 90, 120, 0)
        val pokemonMoveFlyingPPList = listOf(35, 20, 35, 20, 5, 25, 10, 15, 15, 10)
        val pokemonMoveFlyingAccuracyList = listOf(100, 100, 100, 100, 90, 95, 70, 95, 100, 0)

// Steel
        val pokemonNameSteelList = listOf("Meltan", "Melmetal", "Magnezone", "Skarmory", "Klink", "Klang", "Klinklang", "Honedge", "Doublade", "Aegislash")
        val pokemonMoveSteelList = listOf("Iron Tail", "Iron Head", "Steel Wing", "Flash Cannon", "Heavy Slam", "Gyro Ball", "Meteor Mash", "Bullet Punch", "Steel Beam", "Autotomize")
        val pokemonPowerSteelList = listOf(100, 80, 70, 80, 70, 70, 90, 40, 140, 0)
        val pokemonMoveSteelPPList = listOf(15, 15, 25, 10, 10, 5, 10, 30, 5, 15)
        val pokemonMoveSteelAccuracyList = listOf(75, 100, 90, 100, 100, 100, 90, 100, 95, 0)

// Electric
        val pokemonNameElectricList = listOf("Pichu", "Pikachu", "Raichu", "Alolan Raichu", "Magnemite", "Magneton", "Magnezone", "Voltorb", "Electrode", "Rotom")
        val pokemonMoveElectricList = listOf("Thunder Shock", "Thunderbolt", "Thunder", "Volt Switch", "Wild Charge", "Zap Cannon", "Electro Ball", "Discharge", "Thunder Punch", "Volt Tackle")
        val pokemonPowerElectricList = listOf(40, 90, 110, 70, 90, 120, 70, 80, 75, 120)
        val pokemonMoveElectricPPList = listOf(30, 15, 10, 20, 15, 5, 10, 15, 15, 15)
        val pokemonMoveElectricAccuracyList = listOf(100, 100, 70, 100, 100, 50, 100, 100, 100, 100)

// Psychic
        val pokemonNamePsychicList = listOf("Abra", "Kadabra", "Alakazam", "Espeon", "Mewtwo", "Mew", "Slowpoke", "Slowbro", "Slowking", "Wobbuffet")
        val pokemonMovePsychicList = listOf("Confusion", "Psybeam", "Psychic", "Psyshock", "Zen Headbutt", "Psychic Fangs", "Psystrike", "Future Sight", "Teleport", "Calm Mind")
        val pokemonPowerPsychicList = listOf(50, 65, 90, 80, 80, 85, 100, 120, 0, 0)
        val pokemonMovePsychicPPList = listOf(25, 20, 10, 10, 15, 10, 5, 10, 20, 20)
        val pokemonMovePsychicAccuracyList = listOf(100, 100, 100, 100, 90, 100, 100, 100, 0, 0)

// Dark
        val pokemonNameDarkList = listOf("Murkrow", "Honchkrow", "Sableye", "Carvanha", "Sharpedo", "Poochyena", "Mightyena", "Absol", "Inkay", "Malamar")
        val pokemonMoveDarkList = listOf("Bite", "Crunch", "Dark Pulse", "Night Slash", "Sucker Punch", "Thief", "Pursuit", "Assurance", "Foul Play", "Snarl")
        val pokemonPowerDarkList = listOf(60, 80, 80, 70, 70, 60, 40, 60, 70, 55)
        val pokemonMoveDarkPPList = listOf(25, 15, 15, 15, 5, 25, 20, 10, 15, 15)
        val pokemonMoveDarkAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 95)

// Ghost
        val pokemonNameGhostList = listOf("Gastly", "Haunter", "Gengar", "Misdreavus", "Mismagius", "Shuppet", "Banette", "Duskull", "Dusclops", "Dusknoir")
        val pokemonMoveGhostList = listOf("Lick", "Shadow Ball", "Hex", "Shadow Punch", "Shadow Sneak", "Night Shade", "Shadow Claw", "Ominous Wind", "Phantom Force", "Curse")
        val pokemonPowerGhostList = listOf(30, 80, 65, 60, 40, 70, 70, 60, 90, 0)
        val pokemonMoveGhostPPList = listOf(30, 15, 10, 20, 30, 15, 15, 5, 10, 10)
        val pokemonMoveGhostAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 100)

// Fairy
        val pokemonNameFairyList = listOf("Clefairy", "Clefable", "Jigglypuff", "Wigglytuff", "Togepi", "Togetic", "Togekiss", "Snubbull", "Granbull", "Ralts")
        val pokemonMoveFairyList = listOf("Disarming Voice", "Fairy Wind", "Dazzling Gleam", "Moonblast", "Play Rough", "Draining Kiss", "Aromatherapy", "Sweet Kiss", "Charm", "Misty Terrain")
        val pokemonPowerFairyList = listOf(40, 40, 80, 95, 90, 50, 0, 0, 0, 0)
        val pokemonMoveFairyPPList = listOf(15, 30, 10, 10, 10, 10, 5, 10, 20, 10)
        val pokemonMoveFairyAccuracyList = listOf(100, 100, 100, 100, 90, 100, 100, 75, 100, 100)


        //randomize
        //Generate
        var i = 0
        while (i < pokemonAmount){
            var typeRand = (0..12).random()
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
                //Normal
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
            if (typeRand == 4){
                //Bug
                val createdPokemon = PokemonClass(pokemonNameBugList.random(), "Bug", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveBugList.get(rand),"Fire",pokemonPowerBugList.get(rand),pokemonMoveBugPPList.get(rand),pokemonMoveBugAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 5){
                //Rock
                val createdPokemon = PokemonClass(pokemonNameRockList.random(), "Rock", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveRockList.get(rand),"Fire",pokemonPowerRockList.get(rand),pokemonMoveRockPPList.get(rand),pokemonMoveRockAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 6){
                //Flying
                val createdPokemon = PokemonClass(pokemonNameFlyingList.random(), "Flying", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveFlyingList.get(rand),"Fire",pokemonPowerFlyingList.get(rand),pokemonMoveFlyingPPList.get(rand),pokemonMoveFlyingAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 7){
                //Steel
                val createdPokemon = PokemonClass(pokemonNameSteelList.random(), "Steel", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveSteelList.get(rand),"Fire",pokemonPowerSteelList.get(rand),pokemonMoveSteelPPList.get(rand),pokemonMoveSteelAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 8){
                //Electric
                val createdPokemon = PokemonClass(pokemonNameElectricList.random(), "Electric", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveElectricList.get(rand),"Fire",pokemonPowerElectricList.get(rand),pokemonMoveElectricPPList.get(rand),pokemonMoveElectricAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 9){
                //Psychic
                val createdPokemon = PokemonClass(pokemonNamePsychicList.random(), "Psychic", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMovePsychicList.get(rand),"Fire",pokemonPowerPsychicList.get(rand),pokemonMovePsychicPPList.get(rand),pokemonMovePsychicAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 10){
                //Dark
                val createdPokemon = PokemonClass(pokemonNameDarkList.random(), "Dark", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveDarkList.get(rand),"Fire",pokemonPowerDarkList.get(rand),pokemonMoveDarkPPList.get(rand),pokemonMoveDarkAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 11){
                //Ghost
                val createdPokemon = PokemonClass(pokemonNameGhostList.random(), "Ghost", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveGhostList.get(rand),"Fire",pokemonPowerGhostList.get(rand),pokemonMoveGhostPPList.get(rand),pokemonMoveGhostAccuracyList.get(rand)))
                    if (!isSilent) println("${createdPokemon.pokemonName} learned ${createdPokemon.pokemonMoveList.getMove(j).getName()}!")
                    j++
                }
                if (!isSilent) println("${createdPokemon.pokemonName}  is added to the party.")
                currentPokemon.addToParty(createdPokemon)
                i++
            }
            if (typeRand == 12){
                //Fairy
                val createdPokemon = PokemonClass(pokemonNameFairyList.random(), "Fairy", 100)
                if (!isSilent) println("${createdPokemon.pokemonName} at Level ${createdPokemon.getLevel()} is created!")
                var j = 0
                while(j < movesAmount){
                    var rand = (0..9).random()
                    createdPokemon.pokemonMoveList.push(PokemonMoveset(pokemonMoveFairyList.get(rand),"Fire",pokemonPowerFairyList.get(rand),pokemonMoveFairyPPList.get(rand),pokemonMoveFairyAccuracyList.get(rand)))
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