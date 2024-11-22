import PokemonMoveset
import kotlin.math.roundToInt

class PokemonClass(Name: String, elementType: String, level: Int) {
    //Attributes
    val pokemonName = Name
    val pokemonType = elementType
    var pokemonLevel = level
    var pokemonMoveList =  PokemonMoveList<PokemonMoveset>()
    private var status = "OK"

    //Base Stats (Average)
    private val baseHP = (68..90).random()
    private val baseATK = (75..83).random()
    private val baseDEF = (73..83).random()
    private val baseSPA = (69..83).random()
    private val baseSPD = (69..83).random()
    private val baseSPE = (66..78).random()

    //IV values, randomized
    private val IVHP = (0..31).random()
    private val IVATK = (0..31).random()
    private val IVDEF = (0..31).random()
    private val IVSPA = (0..31).random()
    private val IVSPD = (0..31).random()
    private val IVSPE = (0..31).random()

    //Effort values
    private val EVHP = (0..20).random()
    private val EVATK = (0..20).random()
    private val EVDEF = (0..20).random()
    private val EVSPA = (0..20).random()
    private val EVSPD = (0..20).random()
    private val EVSPE = (0..20).random()

    //Stat calculation
    var pokemonMaxHP = ((0.01 * (2 * baseHP + IVHP + (0.25 * EVHP).roundToInt()) * level)+level+10)
    var pokemonCurrentHP = pokemonMaxHP
    var pokemonATK = (0.01 * (2 * baseATK + IVATK + (0.25 * EVATK)) * level).roundToInt() + 5
    var pokemonDEF = (0.01 * (2 * baseDEF + IVDEF + (0.25 * EVDEF)) * level).roundToInt() + 5
    var pokemonSPA = (0.01 * (2 * baseSPA + IVSPA + (0.25 * EVSPA)) * level).roundToInt() + 5
    var pokemonSPD = (0.01 * (2 * baseSPD + IVSPD + (0.25 * EVSPD)) * level).roundToInt() + 5
    var pokemonSPE = (0.01 * (2 * baseSPE + IVSPE + (0.25 * EVSPE)) * level).roundToInt() + 5

    //Pokemon Function
    fun appraisePokemon(){
        println("+----------------+")
        println("The Pokemon's name is ${pokemonName} LV ${getLevel()}")
        println("${pokemonName}'s type is ${pokemonType}")
        println("${pokemonName} knows the following moves: ${listMoves()}")
        println("${pokemonName} is currently at ${pokemonCurrentHP.roundToInt()} / ${pokemonMaxHP.roundToInt()} HP")
        println("+----------------+")
        println("${pokemonName}'s stats are: ")
        println("HP: ${pokemonMaxHP.roundToInt()}")
        println("ATK: ${pokemonATK}")
        println("DEF: ${pokemonDEF}")
        println("SPA: ${pokemonSPA}")
        println("SPD: ${pokemonSPD}")
        println("SPE: ${pokemonSPE}")
        println("+----------------+")
        print("Pokemon Expert's appraisal: ")
        when (val totalIV = IVHP+IVATK+IVDEF+IVSPA+IVSPE+IVSPD){
            in (0..90) -> println("This Pokémon's potential is decent all around. (${totalIV})")
            in (91..120) -> println("This Pokémon's potential is above average overall. (${totalIV})")
            in (121..150) -> println("This Pokémon has relatively superior potential overall. (${totalIV})")
            in (150..186) -> println("This Pokémon has outstanding potential overall. (${totalIV})")
        }
    }
    fun checkHP():pokemonHealthValues{
        return pokemonHealthValues(pokemonCurrentHP.toInt(), pokemonMaxHP.toInt())
    }
    fun checkStatus():String{
        return status
    }
    fun setStatus(newStatus: String){
        status = newStatus
    }
    fun getLevel():Int{
        return pokemonLevel
    }

    fun checkIfFainted(): Boolean{
        return checkHP().currentHP < 1
    }

    fun damageHP(damageValue: Int){
        pokemonCurrentHP -= damageValue
    }
    fun listMoves():String{
        var x = 0
        var preparedString = ""
        if (pokemonMoveList.isEmpty()){
            return ""
        }
        while (x < pokemonMoveList.size()){
            val move = pokemonMoveList.getMove(x)
            preparedString = if(preparedString.isEmpty()){
                "${move.getName()},"
            } else{
                "$preparedString ${move.getName()},"
            }
            x++
        }
        return preparedString
    }
}
data class pokemonHealthValues(var currentHP: Int, var maxHP: Int)
class PokemonMoveList<PokemonMoveset>(vararg moveset: PokemonMoveset){

    private val elements = moveset.toMutableList()

    fun push(element: PokemonMoveset){
        if(size() < 4){
            elements.add(element)
        }else{
            println("PROF. OAK: You can't add more than four moves to a Pokemon!")
        }
    }
    fun getMove(x: Int) = elements[x]

    fun peek(): PokemonMoveset = elements.last()

    fun pop(): PokemonMoveset = elements.removeAt(elements.size - 1)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString() = "MutableStack(${elements.joinToString()})"
}

