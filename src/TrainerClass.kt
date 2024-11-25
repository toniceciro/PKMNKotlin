class TrainerClass(name:String, aIFlag:Boolean = false) {

    val trainerName = name
    val isAI = aIFlag
    var currentPokemon = PokemonList<PokemonClass>()

    fun appraisePokemon(pokemonIndex: Int){
        val selectedPokemon = currentPokemon.getPokemon(pokemonIndex)
        selectedPokemon.appraisePokemon()
    }

    fun listPokemon(){
        var x = 0
        var pokemonLoop: PokemonClass
        while (x < currentPokemon.size()){
            pokemonLoop = currentPokemon.getPokemon(x)
            println("$x - ${pokemonLoop.getName()} (LV ${pokemonLoop.getLevel()}) (${pokemonLoop.pokemonType})")
            x++
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