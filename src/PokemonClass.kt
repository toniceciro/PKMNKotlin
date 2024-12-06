import kotlin.math.roundToInt
import com.google.gson.*
private val ANSI_RESET = "\u001B[0m";
private val ANSI_BLACK = "\u001B[30m";
private val ANSI_RED = "\u001B[31m";
private val ANSI_GREEN = "\u001B[32m";
private val ANSI_YELLOW = "\u001B[33m";
private val ANSI_BLUE = "\u001B[34m";
private val ANSI_PURPLE = "\u001B[35m";
private val ANSI_CYAN = "\u001B[36m";
private val ANSI_WHITE = "\u001B[37m";
class PokemonClass (Name: String, elementType: String, level: Int,
                    baseInitHP: Int? = null, baseInitATK:Int? = null, baseInitDEF:Int? = null, baseInitSPA:Int? = null, baseInitSPD:Int? = null, baseInitSPE:Int? = null){
    var pokemonName: String = Name
    var pokemonType: String = elementType
    var pokemonLevel: Int = level
    private var baseHP: Int = baseInitHP?:(68..90).random()
    private var baseATK: Int = baseInitATK?:(75..83).random()
    private var baseDEF: Int = baseInitDEF?:(73..83).random()
    private var baseSPA: Int = baseInitSPA?:(69..83).random()
    private var baseSPD: Int = baseInitSPD?:(69..83).random()
    private var baseSPE: Int = baseInitSPE?:(66..78).random()
    private val shinyValue = 4095
    private val shinyIndicator = (0..8191).random()


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

    var pokemonMoveList =  PokemonMoveList<PokemonMoveset>()
    private var status = "OK"
    //Stat calculation
    var pokemonMaxHP = ((0.01 * (2 * baseHP + IVHP + (0.25 * EVHP).roundToInt()) * pokemonLevel)+pokemonLevel+10)
    var pokemonCurrentHP = pokemonMaxHP
    var pokemonATK = (0.01 * (2 * baseATK + IVATK + (0.25 * EVATK)) * pokemonLevel).roundToInt() + 5
    var pokemonDEF = (0.01 * (2 * baseDEF + IVDEF + (0.25 * EVDEF)) * pokemonLevel).roundToInt() + 5
    var pokemonSPA = (0.01 * (2 * baseSPA + IVSPA + (0.25 * EVSPA)) * pokemonLevel).roundToInt() + 5
    var pokemonSPD = (0.01 * (2 * baseSPD + IVSPD + (0.25 * EVSPD)) * pokemonLevel).roundToInt() + 5
    var pokemonSPE = (0.01 * (2 * baseSPE + IVSPE + (0.25 * EVSPE)) * pokemonLevel).roundToInt() + 5

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
    fun getName():String{
        var pkName = pokemonName
        if ((baseHP+baseATK+baseDEF+baseSPA+baseSPD+baseSPE) >= 570){
            pkName = "${ANSI_CYAN}✦$ANSI_RESET"+ pkName + "${ANSI_CYAN}✦$ANSI_RESET"
        }
        if (shinyValue == shinyIndicator){
            pkName = ANSI_YELLOW + ".°˖✩${pkName}✩˖°." + ANSI_RESET
        }
        return pkName
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
    fun deepCopy():PokemonClass {
        val JSON = Gson().toJson(this)
        return Gson().fromJson(JSON, PokemonClass::class.java)
    }
    fun serializeToJSON():String {
        val JSON = Gson().toJson(this)
        return JSON
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

