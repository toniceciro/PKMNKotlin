

class PokemonMoveset(moveName: String, moveType: String, basePower: Int, powerPoints: Int, accuracyLevel: Int,isSpecialAttack:Boolean = false, modifiesStats: Boolean = false, inflictsStatus: Boolean = false) {
    private var name = moveName
    private var type = moveType
    private var power = basePower
    private var maxPP = powerPoints
    private var accuracy = accuracyLevel
    private var currentPP = maxPP

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
    fun checkIfEmpty():Boolean{
        if(currentPP <= 0){
            name = "Struggle"
            type = "Normal"
            power = 10
            accuracy = 100
            return true
        }
        return false
    }

}
data class movePPValues(var currentPP: Int, var maxPP: Int)