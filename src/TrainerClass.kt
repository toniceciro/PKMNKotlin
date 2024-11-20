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


        val pokemonNameFireList = listOf(
            "Litten", "Torracat", "Incineroar", "Salandit", "Salazzle", "Oricorio (Baile Style)", "Turtonator",
            "Torkoal", "Alolan Marowak", "Heat Rotom", "Charmander", "Charmeleon", "Charizard",
            "Vulpix", "Ninetales", "Growlithe", "Arcanine", "Magmar", "Flareon", "Cyndaquil",
            "Quilava", "Typhlosion", "Torchic", "Combusken", "Blaziken", "Pansear", "Simisear",
            "Fennekin", "Braixen", "Delphox"
        )
        val pokemonMoveTypeFireList = listOf(
            "Fire", "Fire", "Fire|Dark", "Fire|Poison", "Fire|Poison", "Fire|Flying", "Fire|Dragon",
            "Fire", "Fire|Ghost", "Fire|Electric", "Fire", "Fire", "Fire|Flying",
            "Fire", "Fire", "Fire", "Fire", "Fire", "Fire", "Fire",
            "Fire", "Fire", "Fire", "Fire|Fighting", "Fire|Fighting", "Fire", "Fire",
            "Fire", "Fire", "Fire|Psychic"
        )

        val pokemonMoveFireList = listOf("Ember", "Flame Burst", "Flamethrower", "Fire Blast", "Flare Blitz", "Heat Wave", "Inferno", "Blaze Kick", "Blue Flare", "Eruption")
        val pokemonPowerFireList = listOf(40, 70, 90, 110, 120, 95, 100, 85, 130, 150)
        val pokemonMoveFirePPList = listOf(25, 15, 15, 5, 15, 10, 5, 10, 5, 5)
        val pokemonMoveFireAccuracyList = listOf(100, 100, 100, 85, 100, 90, 50, 90, 85, 100)
        val FirePokemonList = listOf(pokemonNameFireList,pokemonMoveFireList,pokemonPowerFireList,pokemonMoveFirePPList,pokemonMoveFireAccuracyList,pokemonMoveTypeFireList)

        //Water
        val pokemonNameWaterList = listOf(
            "Popplio", "Brionne", "Primarina", "Wingull", "Pelipper", "Wishiwashi", "Mareanie",
            "Toxapex", "Dewpider", "Araquanid", "Squirtle", "Wartortle", "Blastoise",
            "Psyduck", "Golduck", "Poliwag", "Poliwhirl", "Poliwrath", "Tentacool", "Tentacruel",
            "Seel", "Dewgong", "Shellder", "Cloyster", "Krabby", "Kingler", "Horsea",
            "Seadra", "Goldeen", "Seaking"
        )
        val pokemonMoveTypeWaterList = listOf(
            "Water", "Water", "Water|Fairy", "Water|Flying", "Water|Flying", "Water", "Water|Poison",
            "Water|Poison", "Water|Bug", "Water|Bug", "Water", "Water", "Water",
            "Water", "Water", "Water", "Water", "Water|Fighting", "Water|Poison", "Water|Poison",
            "Water", "Water|Ice", "Water", "Water|Ice", "Water", "Water", "Water",
            "Water", "Water", "Water"
        )
        val pokemonMoveWaterList = listOf("Water Gun", "Hydro Pump", "Surf", "Scald", "Waterfall", "Aqua Tail", "Aqua Jet", "Liquidation", "Origin Pulse", "Bubble Beam")
        val pokemonPowerWaterList = listOf(40, 110, 90, 80, 80, 90, 40, 85, 110, 65)
        val pokemonMoveWaterPPList = listOf(25, 5, 15, 15, 15, 10, 20, 10, 10, 20)
        val pokemonMoveWaterAccuracyList = listOf(100, 80, 100, 100, 100, 90, 100, 100, 85, 100)
        val WaterPokemonList = listOf(pokemonNameWaterList,pokemonMoveWaterList,pokemonPowerWaterList,pokemonMoveWaterPPList,pokemonMoveWaterAccuracyList,pokemonMoveTypeWaterList)

        // Normal
        val pokemonNameNormalList = listOf(
            "Yungoos", "Gumshoos", "Pikipek", "Trumbeak", "Toucannon", "Stufful", "Bewear",
            "Type: Null", "Silvally", "Komala", "Meowth", "Persian", "Jigglypuff",
            "Wigglytuff", "Rattata", "Raticate", "Pidgey", "Pidgeotto", "Pidgeot",
            "Eevee", "Ditto", "Snorlax", "Kangaskhan", "Tauros", "Doduo",
            "Dodrio", "Farfetch'd", "Lickitung", "Chansey", "Porygon"
        )
        val pokemonTypeNormalList = listOf(
            "Normal", "Normal", "Normal|Flying", "Normal|Flying", "Normal|Flying", "Normal|Fighting", "Normal|Fighting",
            "Normal", "Normal", "Normal", "Normal", "Normal", "Normal|Fairy",
            "Normal|Fairy", "Normal", "Normal", "Normal|Flying", "Normal|Flying", "Normal|Flying",
            "Normal", "Normal", "Normal", "Normal", "Normal", "Normal|Flying",
            "Normal|Flying", "Normal|Flying", "Normal", "Normal", "Normal"
        )
        val pokemonMoveNormalList = listOf("Tackle", "Hyper Beam", "Double-Edge", "Body Slam", "Take Down", "Swift", "Echoed Voice", "Hidden Power (Normal)", "Giga Impact", "Quick Attack")
        val pokemonPowerNormalList = listOf(40, 150, 120, 85, 90, 60, 40, 60, 150, 40)
        val pokemonMoveNormalPPList = listOf(35, 5, 15, 15, 20, 20, 15, 15, 5, 30)
        val pokemonMoveNormalAccuracyList = listOf(100, 90, 100, 100, 85, 100, 100, 100, 90, 100)
        val NormalPokemonList = listOf(pokemonNameNormalList, pokemonMoveNormalList, pokemonPowerNormalList, pokemonMoveNormalPPList, pokemonMoveNormalAccuracyList, pokemonTypeNormalList)


        // Grass
        val pokemonNameGrassList = listOf(
            "Bulbasaur", "Ivysaur", "Venusaur", "Oddish", "Gloom", "Vileplume", "Bellsprout",
            "Weepinbell", "Victreebel", "Exeggcute", "Exeggutor", "Tangela", "Chikorita",
            "Bayleef", "Meganium", "Treecko", "Grovyle", "Sceptile", "Shroomish", "Breloom",
            "Roselia", "Cacnea", "Cacturne", "Lileep", "Cradily", "Turtwig", "Grotle",
            "Torterra", "Snover", "Abomasnow"
        )
        val pokemonTypeGrassList = listOf(
            "Grass|Poison", "Grass|Poison", "Grass|Poison", "Grass|Poison", "Grass|Poison", "Grass|Poison", "Grass|Poison",
            "Grass|Poison", "Grass|Poison", "Grass|Psychic", "Grass|Psychic", "Grass", "Grass",
            "Grass", "Grass", "Grass", "Grass", "Grass|Dragon", "Grass", "Grass|Fighting",
            "Grass|Poison", "Grass", "Grass|Dark", "Grass|Rock", "Grass|Rock", "Grass", "Grass",
            "Grass|Ground", "Grass|Ice", "Grass|Ice"
        )
        val pokemonMoveGrassList = listOf("Vine Whip", "Razor Leaf", "Leaf Blade", "Solar Beam", "Energy Ball", "Giga Drain", "Leaf Storm", "Seed Bomb", "Mega Drain", "Grass Knot")
        val pokemonPowerGrassList = listOf(45, 55, 90, 120, 90, 75, 130, 80, 40, 60)
        val pokemonMoveGrassPPList = listOf(25, 25, 15, 10, 10, 10, 5, 15, 15, 20)
        val pokemonMoveGrassAccuracyList = listOf(100, 95, 100, 100, 100, 100, 90, 100, 100, 100)
        val GrassPokemonList = listOf(pokemonNameGrassList, pokemonMoveGrassList, pokemonPowerGrassList, pokemonMoveGrassPPList, pokemonMoveGrassAccuracyList, pokemonTypeGrassList)


        val pokemonNameBugList = listOf(
            "Caterpie", "Metapod", "Butterfree", "Weedle", "Kakuna", "Beedrill", "Paras",
            "Parasect", "Venonat", "Venomoth", "Scyther", "Pinsir", "Ledyba",
            "Ledian", "Spinarak", "Ariados", "Yanma", "Pineco", "Forretress",
            "Wurmple", "Silcoon", "Beautifly", "Cascoon", "Dustox", "Surskit",
            "Masquerain", "Nincada", "Ninjask", "Shedinja", "Kricketot"
        )
        val pokemonTypeBugList = listOf(
            "Bug", "Bug", "Bug|Flying", "Bug|Poison", "Bug|Poison", "Bug|Poison", "Bug|Grass",
            "Bug|Grass", "Bug|Poison", "Bug|Poison", "Bug|Flying", "Bug", "Bug|Flying",
            "Bug|Flying", "Bug|Poison", "Bug|Poison", "Bug|Flying", "Bug", "Bug|Steel",
            "Bug", "Bug", "Bug|Flying", "Bug|Poison", "Bug|Poison", "Bug|Water",
            "Bug|Flying", "Bug|Ground", "Bug|Flying", "Bug|Ghost", "Bug"
        )
        val pokemonMoveBugList = listOf("Bug Bite", "X-Scissor", "Leech Life", "Signal Beam", "Struggle Bug", "Infestation", "Bug Buzz", "Lunge", "Fury Cutter", "U-turn")
        val pokemonPowerBugList = listOf(60, 80, 80, 75, 50, 20, 90, 80, 40, 70)
        val pokemonMoveBugPPList = listOf(20, 15, 15, 15, 20, 20, 10, 15, 20, 20)
        val pokemonMoveBugAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 95, 100)
        val BugPokemonList = listOf(pokemonNameBugList, pokemonMoveBugList, pokemonPowerBugList, pokemonMoveBugPPList, pokemonMoveBugAccuracyList, pokemonTypeBugList)


        val pokemonNameRockList = listOf(
            "Geodude", "Graveler", "Golem", "Onix", "Rhyhorn", "Rhydon", "Omanyte",
            "Omastar", "Kabuto", "Kabutops", "Aerodactyl", "Sudowoodo", "Lileep",
            "Cradily", "Anorith", "Armaldo", "Cranidos", "Rampardos", "Shieldon",
            "Bastiodon", "Roggenrola", "Boldore", "Gigalith", "Tirtouga", "Carracosta",
            "Archen", "Archeops", "Tyrunt", "Tyrantrum", "Amaura"
        )
        val pokemonTypeRockList = listOf(
            "Rock|Ground", "Rock|Ground", "Rock|Ground", "Rock|Ground", "Rock|Ground", "Rock|Ground", "Rock|Water",
            "Rock|Water", "Rock|Water", "Rock|Water", "Rock|Flying", "Rock", "Rock|Grass",
            "Rock|Grass", "Rock|Bug", "Rock|Bug", "Rock", "Rock", "Rock|Steel",
            "Rock|Steel", "Rock", "Rock", "Rock", "Rock|Water", "Rock|Water",
            "Rock|Flying", "Rock|Flying", "Rock|Dragon", "Rock|Dragon", "Rock|Ice"
        )
        val pokemonMoveRockList = listOf("Rock Throw", "Rock Slide", "Stone Edge", "Rock Tomb", "Power Gem", "Ancient Power", "Smack Down", "Rock Blast", "Head Smash", "Rock Polish")
        val pokemonPowerRockList = listOf(50, 75, 100, 60, 80, 60, 50, 25, 150, 0)
        val pokemonMoveRockPPList = listOf(15, 10, 5, 15, 20, 5, 15, 10, 5, 20)
        val pokemonMoveRockAccuracyList = listOf(90, 90, 80, 95, 100, 100, 100, 90, 80, 100)
        val RockPokemonList = listOf(pokemonNameRockList, pokemonMoveRockList, pokemonPowerRockList, pokemonMoveRockPPList, pokemonMoveRockAccuracyList, pokemonTypeRockList)


        val pokemonNameFlyingList = listOf(
            "Pidgey", "Pidgeotto", "Pidgeot", "Spearow", "Fearow", "Zubat", "Golbat",
            "Farfetch'd", "Doduo", "Dodrio", "Scyther", "Aerodactyl", "Hoothoot",
            "Noctowl", "Ledyba", "Ledian", "Crobat", "Togetic", "Natu", "Xatu",
            "Murkrow", "Gligar", "Mantine", "Skarmory", "Delibird", "Mantyke",
            "Starly", "Staravia", "Staraptor", "Wingull"
        )
        val pokemonTypeFlyingList = listOf(
            "Normal|Flying", "Normal|Flying", "Normal|Flying", "Normal|Flying", "Normal|Flying", "Poison|Flying", "Poison|Flying",
            "Normal|Flying", "Normal|Flying", "Normal|Flying", "Bug|Flying", "Rock|Flying", "Normal|Flying",
            "Normal|Flying", "Bug|Flying", "Bug|Flying", "Poison|Flying", "Fairy|Flying", "Psychic|Flying", "Psychic|Flying",
            "Dark|Flying", "Ground|Flying", "Water|Flying", "Steel|Flying", "Ice|Flying", "Water|Flying",
            "Normal|Flying", "Normal|Flying", "Normal|Flying", "Water|Flying"
        )
        val pokemonMoveFlyingList = listOf("Peck", "Pluck", "Wing Attack", "Aerial Ace", "Sky Attack", "Air Cutter", "Hurricane", "Fly", "Brave Bird", "Roost")
        val pokemonPowerFlyingList = listOf(35, 60, 60, 60, 140, 60, 110, 90, 120, 0)
        val pokemonMoveFlyingPPList = listOf(35, 20, 35, 20, 5, 25, 10, 15, 15, 10)
        val pokemonMoveFlyingAccuracyList = listOf(100, 100, 100, 100, 90, 95, 70, 95, 100, 0)
        val FlyingPokemonList = listOf(pokemonNameFlyingList, pokemonMoveFlyingList, pokemonPowerFlyingList, pokemonMoveFlyingPPList, pokemonMoveFlyingAccuracyList, pokemonTypeFlyingList)


        val pokemonNameSteelList = listOf(
            "Magnemite", "Magneton", "Magnezone", "Steelix", "Skarmory", "Mawile", "Aron",
            "Lairon", "Aggron", "Beldum", "Metang", "Metagross", "Registeel",
            "Jirachi", "Empoleon", "Lucario", "Bronzor", "Bronzong", "Excadrill",
            "Escavalier", "Ferroseed", "Ferrothorn", "Klink", "Klang", "Klinklang",
            "Togedemaru", "Solgaleo", "Celesteela", "Kartana", "Stakataka"
        )
        val pokemonTypeSteelList = listOf(
            "Electric|Steel", "Electric|Steel", "Electric|Steel", "Steel|Ground", "Steel|Flying", "Steel|Fairy", "Steel|Rock",
            "Steel|Rock", "Steel|Rock", "Steel|Psychic", "Steel|Psychic", "Steel|Psychic", "Steel",
            "Steel|Psychic", "Water|Steel", "Fighting|Steel", "Steel|Psychic", "Steel|Psychic", "Ground|Steel",
            "Bug|Steel", "Grass|Steel", "Grass|Steel", "Steel", "Steel", "Steel",
            "Electric|Steel", "Psychic|Steel", "Steel|Flying", "Grass|Steel", "Rock|Steel"
        )
        val pokemonMoveSteelList = listOf("Iron Tail", "Iron Head", "Steel Wing", "Flash Cannon", "Heavy Slam", "Gyro Ball", "Meteor Mash", "Bullet Punch", "Steel Beam", "Autotomize")
        val pokemonPowerSteelList = listOf(100, 80, 70, 80, 50, 50, 90, 40, 140, 0)
        val pokemonMoveSteelPPList = listOf(15, 15, 25, 10, 10, 5, 10, 30, 5, 15)
        val pokemonMoveSteelAccuracyList = listOf(75, 100, 90, 100, 100, 100, 90, 100, 95, 0)
        val SteelPokemonList = listOf(pokemonNameSteelList, pokemonMoveSteelList, pokemonPowerSteelList, pokemonMoveSteelPPList, pokemonMoveSteelAccuracyList, pokemonTypeSteelList)

//Electric
        val pokemonNameElectricList = listOf(
            "Pichu", "Pikachu", "Raichu", "Alolan Raichu", "Magnemite", "Magneton", "Magnezone",
            "Voltorb", "Electrode", "Electabuzz", "Jolteon", "Zapdos", "Chinchou",
            "Lanturn", "Mareep", "Flaaffy", "Ampharos", "Blitzle", "Zebstrika",
            "Emolga", "Joltik", "Galvantula", "Stunfisk", "Helioptile", "Heliolisk",
            "Tynamo", "Eelektrik", "Eelektross", "Togedemaru", "Zeraora"
        )
        val pokemonTypeElectricList = listOf(
            "Electric", "Electric", "Electric", "Electric|Psychic", "Electric|Steel", "Electric|Steel", "Electric|Steel",
            "Electric", "Electric", "Electric", "Electric", "Electric|Flying", "Water|Electric",
            "Water|Electric", "Electric", "Electric", "Electric", "Electric", "Electric",
            "Electric|Flying", "Bug|Electric", "Bug|Electric", "Ground|Electric", "Normal|Electric", "Normal|Electric",
            "Electric", "Electric", "Electric", "Electric|Steel", "Electric"
        )
        val pokemonMoveElectricList = listOf("Thunder Shock", "Thunderbolt", "Thunder", "Volt Switch", "Wild Charge", "Zap Cannon", "Electro Ball", "Discharge", "Thunder Punch", "Volt Tackle")
        val pokemonPowerElectricList = listOf(40, 90, 110, 70, 90, 120, 100, 80, 75, 120)
        val pokemonMoveElectricPPList = listOf(30, 15, 10, 20, 15, 5, 10, 15, 15, 15)
        val pokemonMoveElectricAccuracyList = listOf(100, 100, 70, 100, 100, 50, 100, 100, 100, 100)
        val ElectricPokemonList = listOf(pokemonNameElectricList, pokemonMoveElectricList, pokemonPowerElectricList, pokemonMoveElectricPPList, pokemonMoveElectricAccuracyList, pokemonTypeElectricList)


// Psychic
        val pokemonNamePsychicList = listOf(
            "Abra", "Kadabra", "Alakazam", "Slowpoke", "Slowbro", "Drowzee", "Hypno",
            "Exeggcute", "Exeggutor", "Starmie", "Mr. Mime", "Jynx", "Espeon",
            "Natu", "Xatu", "Smoochum", "Wobbuffet", "Girafarig", "Mewtwo", "Mew",
            "Lugia", "Celebi", "Ralts", "Kirlia", "Gardevoir", "Baltoy", "Claydol",
            "Chimecho", "Wynaut", "Metagross"
        )
        val pokemonTypePsychicList = listOf(
            "Psychic", "Psychic", "Psychic", "Water|Psychic", "Water|Psychic", "Psychic", "Psychic",
            "Grass|Psychic", "Grass|Psychic", "Water|Psychic", "Psychic|Fairy", "Ice|Psychic", "Psychic",
            "Psychic|Flying", "Psychic|Flying", "Ice|Psychic", "Psychic", "Normal|Psychic", "Psychic", "Psychic",
            "Psychic|Flying", "Psychic|Grass", "Psychic|Fairy", "Psychic|Fairy", "Psychic|Fairy", "Ground|Psychic",
            "Ground|Psychic", "Psychic", "Psychic", "Steel|Psychic"
        )
        val pokemonMovePsychicList = listOf("Confusion", "Psybeam", "Psychic", "Psyshock", "Zen Headbutt", "Psychic Fangs", "Psystrike", "Future Sight", "Hidden Power (Psychic)", "Expanding Force")
        val pokemonPowerPsychicList = listOf(50, 65, 90, 80, 80, 85, 100, 120, 60, 80)
        val pokemonMovePsychicPPList = listOf(25, 20, 10, 10, 15, 10, 5, 10, 15, 10)
        val pokemonMovePsychicAccuracyList = listOf(100, 100, 100, 100, 90, 100, 100, 100, 100, 100)
        val PsychicPokemonList = listOf(pokemonNamePsychicList, pokemonMovePsychicList, pokemonPowerPsychicList, pokemonMovePsychicPPList, pokemonMovePsychicAccuracyList, pokemonTypePsychicList)


// Dark
        val pokemonNameDarkList = listOf(
            "Murkrow", "Honchkrow", "Sneasel", "Weavile", "Houndour", "Houndoom", "Poochyena",
            "Mightyena", "Nuzleaf", "Shiftry", "Sableye", "Carvanha", "Sharpedo",
            "Cacturne", "Crawdaunt", "Stunky", "Skuntank", "Spiritomb", "Drapion",
            "Sandile", "Krokorok", "Krookodile", "Scraggy", "Scrafty", "Pawniard",
            "Bisharp", "Vullaby", "Mandibuzz", "Deino", "Zweilous"
        )
        val pokemonTypeDarkList = listOf(
            "Dark|Flying", "Dark|Flying", "Dark|Ice", "Dark|Ice", "Dark|Fire", "Dark|Fire", "Dark",
            "Dark", "Grass|Dark", "Grass|Dark", "Dark|Ghost", "Water|Dark", "Water|Dark",
            "Grass|Dark", "Water|Dark", "Poison|Dark", "Poison|Dark", "Ghost|Dark", "Poison|Dark",
            "Ground|Dark", "Ground|Dark", "Ground|Dark", "Dark|Fighting", "Dark|Fighting", "Dark|Steel",
            "Dark|Steel", "Dark|Flying", "Dark|Flying", "Dark|Dragon", "Dark|Dragon"
        )
        val pokemonMoveDarkList = listOf("Bite", "Crunch", "Dark Pulse", "Night Slash", "Foul Play", "Sucker Punch", "Feint Attack", "Knock Off", "Thief", "Assurance")
        val pokemonPowerDarkList = listOf(60, 80, 80, 70, 95, 70, 60, 65, 60, 60)
        val pokemonMoveDarkPPList = listOf(25, 15, 15, 15, 15, 20, 20, 20, 25, 10)
        val pokemonMoveDarkAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 100)
        val DarkPokemonList = listOf(pokemonNameDarkList, pokemonMoveDarkList, pokemonPowerDarkList, pokemonMoveDarkPPList, pokemonMoveDarkAccuracyList, pokemonTypeDarkList)


// Ghost
        val pokemonNameGhostList = listOf(
            "Gastly", "Haunter", "Gengar", "Misdreavus", "Mismagius", "Shuppet", "Banette",
            "Duskull", "Dusclops", "Dusknoir", "Drifloon", "Drifblim", "Spiritomb",
            "Yamask", "Cofagrigus", "Frillish", "Jellicent", "Litwick", "Lampent", "Chandelure",
            "Golett", "Golurk", "Phantump", "Trevenant", "Pumpkaboo", "Gourgeist", "Sandygast",
            "Palossand", "Mimikyu", "Dhelmise"
        )
        val pokemonTypeGhostList = listOf(
            "Ghost|Poison", "Ghost|Poison", "Ghost|Poison", "Ghost", "Ghost", "Ghost", "Ghost",
            "Ghost", "Ghost", "Ghost", "Ghost|Flying", "Ghost|Flying", "Ghost|Dark",
            "Ghost", "Ghost", "Water|Ghost", "Water|Ghost", "Ghost|Fire", "Ghost|Fire", "Ghost|Fire",
            "Ground|Ghost", "Ground|Ghost", "Ghost|Grass", "Ghost|Grass", "Ghost|Grass", "Ghost|Grass", "Ghost|Ground",
            "Ghost|Ground", "Ghost|Fairy", "Ghost|Grass"
        )
        val pokemonMoveGhostList = listOf("Shadow Ball", "Shadow Claw", "Hex", "Shadow Punch", "Shadow Sneak", "Night Shade", "Shadow Force", "Phantom Force", "Ominous Wind", "Lick")
        val pokemonPowerGhostList = listOf(80, 70, 65, 60, 40, 100, 120, 90, 60, 30)
        val pokemonMoveGhostPPList = listOf(15, 15, 10, 20, 30, 15, 5, 10, 5, 30)
        val pokemonMoveGhostAccuracyList = listOf(100, 100, 100, 100, 100, 100, 100, 100, 100, 100)
        val GhostPokemonList = listOf(pokemonNameGhostList, pokemonMoveGhostList, pokemonPowerGhostList, pokemonMoveGhostPPList, pokemonMoveGhostAccuracyList, pokemonTypeGhostList)


// Fairy
        val pokemonNameFairyList = listOf(
            "Sylveon", "Florges", "Mimikyu", "Togekiss", "Granbull", "Slurpuff", "Aromatisse",
            "Mawile", "Klefki", "Tapu Koko", "Clefairy", "Clefable", "Jigglypuff",
            "Wigglytuff", "Mr. Mime", "Mime Jr.", "Gardevoir", "Marill", "Azumarill",
            "Ralts", "Kirlia", "Swirlix", "Spritzee", "Togepi", "Togetic",
            "Carbink", "Diancie", "Magearna", "Zacian", "Alcremie"
        )
        val pokemonTypeFairyList = listOf(
            "Fairy", "Fairy", "Ghost|Fairy", "Fairy|Flying", "Fairy", "Fairy", "Fairy",
            "Steel|Fairy", "Steel|Fairy", "Electric|Fairy", "Fairy", "Fairy", "Normal|Fairy",
            "Normal|Fairy", "Psychic|Fairy", "Psychic|Fairy", "Psychic|Fairy", "Water|Fairy", "Water|Fairy",
            "Psychic|Fairy", "Psychic|Fairy", "Fairy", "Fairy", "Fairy", "Fairy|Flying",
            "Rock|Fairy", "Rock|Fairy", "Steel|Fairy", "Fairy", "Fairy"
        )
        val pokemonMoveFairyList = listOf("Moonblast", "Dazzling Gleam", "Play Rough", "Draining Kiss", "Fairy Wind", "Sparkling Aria", "Misty Explosion", "Sweet Kiss", "Disarming Voice", "Misty Terrain")
        val pokemonPowerFairyList = listOf(95, 80, 90, 50, 40, 90, 100, 0, 40, 0)
        val pokemonMoveFairyPPList = listOf(15, 10, 10, 10, 30, 10, 5, 10, 15, 10)
        val pokemonMoveFairyAccuracyList = listOf(100, 100, 90, 100, 100, 100, 100, 75, 100, 100)
        val FairyPokemonList = listOf(pokemonNameFairyList, pokemonMoveFairyList, pokemonPowerFairyList, pokemonMoveFairyPPList, pokemonMoveFairyAccuracyList, pokemonTypeFairyList)


        //Ground
        val pokemonNameGroundList = listOf(
            "Diglett", "Dugtrio", "Sandshrew", "Sandslash", "Cubone", "Marowak", "Gligar",
            "Golem", "Mudbray", "Mudsdale", "Rhyhorn", "Rhydon", "Wooper",
            "Quagsire", "Phanpy", "Donphan", "Larvitar", "Pupitar", "Swampert", "Numel",
            "Camerupt", "Trapinch", "Vibrava", "Flygon", "Barboach", "Whiscash", "Baltoy",
            "Claydol", "Garchomp", "Groudon"
        )
        val pokemonTypeGroundList = listOf(
            "Ground", "Ground", "Ground", "Ground", "Ground", "Ground", "Ground|Flying",
            "Rock|Ground", "Ground", "Ground", "Ground|Rock", "Ground|Rock", "Water|Ground",
            "Water|Ground", "Ground", "Ground", "Rock|Ground", "Rock|Ground", "Water|Ground", "Fire|Ground",
            "Fire|Ground", "Ground", "Ground|Dragon", "Ground|Dragon", "Water|Ground", "Water|Ground", "Ground|Psychic",
            "Ground|Psychic", "Dragon|Ground", "Ground"
        )
        val pokemonMoveGroundList = listOf("Earthquake", "Bulldoze", "Earth Power", "Mud-Slap", "Sand Tomb", "Magnitude", "Dig", "Bonemerang", "Mud Shot", "Precipice Blades")
        val pokemonPowerGroundList = listOf(100, 60, 90, 20, 35, 100, 80, 50, 55, 120)
        val pokemonMoveGroundPPList = listOf(10, 20, 10, 10, 15, 30, 10, 10, 15, 10)
        val pokemonMoveGroundAccuracyList = listOf(100, 100, 100, 100, 85, 100, 100, 90, 95, 85)
        val GroundPokemonList = listOf(pokemonNameGroundList, pokemonMoveGroundList, pokemonPowerGroundList, pokemonMoveGroundPPList, pokemonMoveGroundAccuracyList, pokemonTypeGroundList)

        //Dragon
        val pokemonNameDragonList = listOf(
            "Dratini", "Dragonair", "Dragonite", "Bagon", "Shelgon", "Salamence", "Gible",
            "Gabite", "Garchomp", "Axew", "Fraxure", "Haxorus", "Druddigon",
            "Goomy", "Sliggoo", "Goodra", "Jangmo-o", "Hakamo-o", "Kommo-o", "Turtonator",
            "Dracovish", "Dracozolt", "Duraludon", "Flapple", "Appletun", "Regidrago", "Zygarde",
            "Zacian (Crowned)", "Eternatus", "Rayquaza"
        )
        val pokemonTypeDragonList = listOf(
            "Dragon", "Dragon", "Dragon|Flying", "Dragon", "Dragon", "Dragon|Flying", "Dragon|Ground",
            "Dragon|Ground", "Dragon|Ground", "Dragon", "Dragon", "Dragon", "Dragon",
            "Dragon", "Dragon", "Dragon", "Dragon", "Dragon|Fighting", "Dragon|Fighting", "Fire|Dragon",
            "Water|Dragon", "Electric|Dragon", "Steel|Dragon", "Grass|Dragon", "Grass|Dragon", "Dragon", "Dragon|Ground",
            "Fairy|Steel", "Poison|Dragon", "Dragon|Flying"
        )
        val pokemonMoveDragonList = listOf("Dragon Claw", "Dragon Tail", "Dragon Pulse", "Outrage", "Draco Meteor", "Dragon Rush", "Dragon Breath", "Dual Chop", "Twister", "Roar of Time")
        val pokemonPowerDragonList = listOf(80, 60, 85, 120, 130, 100, 60, 40, 40, 150)
        val pokemonMoveDragonPPList = listOf(15, 10, 10, 10, 5, 10, 20, 15, 20, 5)
        val pokemonMoveDragonAccuracyList = listOf(100, 90, 100, 100, 90, 75, 100, 90, 100, 90)
        val DragonPokemonList = listOf(pokemonNameDragonList, pokemonMoveDragonList, pokemonPowerDragonList, pokemonMoveDragonPPList, pokemonMoveDragonAccuracyList, pokemonTypeDragonList)


        //Poison
        val pokemonNamePoisonList = listOf(
            "Ekans", "Arbok", "Nidoran♀", "Nidorina", "Nidoqueen", "Nidoran♂", "Nidorino",
            "Nidoking", "Zubat", "Golbat", "Oddish", "Gloom", "Vileplume", "Venonat",
            "Venomoth", "Bellsprout", "Weepinbell", "Victreebel", "Grimer", "Muk",
            "Koffing", "Weezing", "Tentacool", "Tentacruel", "Gastly", "Haunter",
            "Gengar", "Stunky", "Skuntank", "Skorupi"
        )
        val pokemonTypePoisonList = listOf(
            "Poison", "Poison", "Poison", "Poison", "Poison|Ground", "Poison", "Poison",
            "Poison|Ground", "Poison|Flying", "Poison|Flying", "Grass|Poison", "Grass|Poison", "Grass|Poison", "Bug|Poison",
            "Bug|Poison", "Grass|Poison", "Grass|Poison", "Grass|Poison", "Poison", "Poison",
            "Poison", "Poison", "Water|Poison", "Water|Poison", "Ghost|Poison", "Ghost|Poison",
            "Ghost|Poison", "Poison|Dark", "Poison|Dark", "Poison|Bug"
        )
        val pokemonMovePoisonList = listOf("Sludge Bomb", "Poison Jab", "Toxic", "Sludge Wave", "Venoshock", "Cross Poison", "Acid", "Belch", "Gunk Shot", "Poison Fang")
        val pokemonPowerPoisonList = listOf(90, 80, 0, 95, 65, 70, 40, 120, 120, 50)
        val pokemonMovePoisonPPList = listOf(10, 20, 10, 10, 10, 20, 30, 10, 5, 15)
        val pokemonMovePoisonAccuracyList = listOf(100, 100, 90, 100, 100, 100, 100, 90, 80, 100)
        val PoisonPokemonList = listOf(pokemonNamePoisonList, pokemonMovePoisonList, pokemonPowerPoisonList, pokemonMovePoisonPPList, pokemonMovePoisonAccuracyList, pokemonTypePoisonList)

        //Ice
        val pokemonNameIceList = listOf(
            "Snorunt", "Glalie", "Froslass", "Spheal", "Sealeo", "Walrein", "Swinub",
            "Piloswine", "Mamoswine", "Sneasel", "Weavile", "Vanillite", "Vanillish",
            "Vanilluxe", "Cubchoo", "Beartic", "Cryogonal", "Bergmite", "Avalugg",
            "Alolan Vulpix", "Alolan Ninetales", "Articuno", "Smoochum", "Jynx", "Regice",
            "Glaceon", "Frosmoth", "Darmanitan (Galarian Zen Mode)", "Mr. Rime", "Arctozolt"
        )
        val pokemonTypeIceList = listOf(
            "Ice", "Ice", "Ice|Ghost", "Ice|Water", "Ice|Water", "Ice|Water", "Ice|Ground",
            "Ice|Ground", "Ice|Ground", "Dark|Ice", "Dark|Ice", "Ice", "Ice",
            "Ice", "Ice", "Ice", "Ice", "Ice", "Ice",
            "Ice", "Ice|Fairy", "Ice|Flying", "Ice|Psychic", "Ice|Psychic", "Ice",
            "Ice", "Ice|Bug", "Ice|Fire", "Ice|Psychic", "Electric|Ice"
        )
        val pokemonMoveIceList = listOf("Ice Beam", "Blizzard", "Avalanche", "Ice Punch", "Freeze-Dry", "Icicle Crash", "Icy Wind", "Aurora Beam", "Frost Breath", "Powder Snow")
        val pokemonPowerIceList = listOf(90, 110, 60, 75, 70, 85, 55, 65, 60, 40)
        val pokemonMoveIcePPList = listOf(10, 5, 10, 15, 20, 10, 15, 20, 10, 25)
        val pokemonMoveIceAccuracyList = listOf(100, 70, 100, 100, 100, 90, 95, 100, 90, 100)
        val IcePokemonList = listOf(pokemonNameIceList, pokemonMoveIceList, pokemonPowerIceList, pokemonMoveIcePPList, pokemonMoveIceAccuracyList, pokemonTypeIceList)

        //Main Lists
        val MainPokemonList = listOf(FirePokemonList, WaterPokemonList, GrassPokemonList, NormalPokemonList, BugPokemonList, RockPokemonList, FlyingPokemonList, SteelPokemonList, ElectricPokemonList, PsychicPokemonList, DarkPokemonList, GhostPokemonList, FairyPokemonList, GroundPokemonList, DragonPokemonList,PoisonPokemonList,IcePokemonList)
        val PokemonTypeList = listOf("Fire","Water","Grass","Normal","Bug","Rock","Flying","Steel","Electric","Psychic","Dark","Ghost","Fairy","Ground", "Dragon","Poison","Ice")

        var i = 0
        while (i < pokemonAmount){
            val typeRand = MainPokemonList.indices.random()
            val nameRand = MainPokemonList[typeRand].indices.random()
            val pokemonName = MainPokemonList[typeRand][0][nameRand].toString()
            val createdPokemon = PokemonClass(pokemonName, MainPokemonList[typeRand][5][nameRand].toString(), 100)
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