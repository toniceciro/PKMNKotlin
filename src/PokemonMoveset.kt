private val ANSI_RESET = "\u001B[0m";
private val ANSI_BLACK = "\u001B[30m";
private val ANSI_RED = "\u001B[31m";
private val ANSI_GREEN = "\u001B[32m";
private val ANSI_YELLOW = "\u001B[33m";
private val ANSI_BLUE = "\u001B[34m";
private val ANSI_PURPLE = "\u001B[35m";
private val ANSI_CYAN = "\u001B[36m";
private val ANSI_WHITE = "\u001B[37m";
private val ANSI_GRAY = "\u001B[90m";
private val ANSI_BRED = "\u001B[91m";
private val ANSI_BGREEN = "\u001B[92m";
private val ANSI_BYELLOW = "\u001B[93m";
private val ANSI_BBLUE = "\u001B[94m";
private val ANSI_BPURPLE = "\u001B[95m";
private val ANSI_BCYAN = "\u001B[96m";
private val ANSI_BWHITE = "\u001B[97m";

class PokemonMoveset(moveName: String, moveType: String, basePower: Int, powerPoints: Int, accuracyLevel: Int,attackType:String = "Physical", modifiesStats: Int = 0, inflictsStatus: Int = 0, multiplier: Float = 0F) {
    private var name = moveName
    private var type = moveType
    private var power = basePower
    private var maxPP = powerPoints
    private var accuracy = accuracyLevel
    private var currentPP = maxPP
    private var attackAtr = attackType

    fun addPP(){
        currentPP++
    }
    fun removePP(){
        if(!checkIfEmpty()){
            currentPP--
        }
    }
    fun getPP():movePPValues{
        val value = movePPValues(currentPP,maxPP)
        return value
    }
    fun getName():String{
        var ANSI_TYPE = ANSI_RESET
        when(type){
            "Bug" -> ANSI_TYPE = ANSI_YELLOW
            "Dark" -> ANSI_TYPE = ANSI_GRAY
            "Dragon" -> ANSI_TYPE = ANSI_PURPLE
            "Electric" -> ANSI_TYPE = ANSI_BYELLOW
            "Fighting" -> ANSI_TYPE = ANSI_BYELLOW
            "Fire" -> ANSI_TYPE = ANSI_BRED
            "Flying" -> ANSI_TYPE = ANSI_BLUE
            "Ghost" -> ANSI_TYPE = ANSI_BWHITE
            "Grass" -> ANSI_TYPE = ANSI_BGREEN
            "Ground" -> ANSI_TYPE = ANSI_YELLOW
            "Poison" -> ANSI_TYPE = ANSI_PURPLE
            "Psychic" -> ANSI_TYPE = ANSI_BPURPLE
            "Rock" -> ANSI_TYPE = ANSI_GRAY
            "Steel" -> ANSI_TYPE = ANSI_WHITE
            "Water" -> ANSI_TYPE = ANSI_BBLUE
            "Fairy" -> ANSI_TYPE = ANSI_BPURPLE
            "Ice" -> ANSI_TYPE = ANSI_BCYAN
            "Normal" -> ANSI_TYPE = ANSI_RESET
            else -> ANSI_TYPE = ANSI_WHITE
        }
        return ANSI_TYPE + name + ANSI_RESET
    }
    fun getRawName():String{
        return name
    }
    fun getType(): String {
        return type
    }
    fun getPower():Int{
        return power
    }
    fun getAccuracy():Int{
        return accuracy
    }
    fun getAttackType():String{
        return attackAtr
    }
    fun checkIfEmpty():Boolean{
        if(currentPP <= 0){
            name = "Struggle"
            type = "--"
            power = 10
            accuracy = 100
            return true
        }
        return false
    }

}
data class movePPValues(var currentPP: Int, var maxPP: Int)