import java.io.File
import kotlin.math.max
import kotlin.math.min

//Part 1
// id number is a section
// num range is what that elf should clean up
// pairs so 2-4,6-8 is one pair of elves
// find out where there are ranges of numbers that fully contain another in pairs


fun main(){
    val list = File("src/4decInput.txt").readLines()

    println(splitListAndCheckIfContained(list)) //Part 1
    println(getOverlapOccurrences(list))


}

fun isFullyContained(list: List<String>): Boolean{
    //tar in split list
    val lowNumInRange1 =list[0].split("-").first().toInt()
    val highNumInRange1 = list[0].split("-").last().toInt()
    val lowNumInRange2 = list[1].split("-").first().toInt()
    val highNumInRange2 = list[1].split("-").last().toInt()
    fun is2ContainedIn1(lowNumInRange1:Int, highNumInRange1:Int, lowNumInRange2:Int, highNumInRange2:Int):Boolean{
        val tempList = mutableListOf<Int>()
        for (i in lowNumInRange1 .. highNumInRange1){
            tempList.add(i)
        }
        if (tempList.contains(lowNumInRange2) && tempList.contains(highNumInRange2)){return true}
        return false
    }
    fun is1ContainedIn2(lowNumInRange1:Int, highNumInRange1:Int, lowNumInRange2:Int, highNumInRange2:Int):Boolean {
        val tempList = mutableListOf<Int>()
        for (i in lowNumInRange2..highNumInRange2) {
            tempList.add(i)
        }
        if (tempList.contains(lowNumInRange1) && tempList.contains(highNumInRange1)) {return true}
        return false
    }
    val is1In2 = is1ContainedIn2(lowNumInRange1, highNumInRange1, lowNumInRange2, highNumInRange2)
    val is2In1 = is2ContainedIn1(lowNumInRange1, highNumInRange1, lowNumInRange2, highNumInRange2)
    if (is1In2 || is2In1){return true}
    return false
}//Part 1
fun splitListAndCheckIfContained(list: List<String>):Int{
    var occurrences = 0
    for (i in list.indices){
        if (isFullyContained(list[i].split(","))){occurrences++}
    }
    return occurrences
}//Part 1
fun checkIfPairIsOverLapping(list: List<String>):Boolean{
    //tar in split list
    val lowNumInRange1 =list[0].split("-").first().toInt()
    val highNumInRange1 = list[0].split("-").last().toInt()
    val lowNumInRange2 = list[1].split("-").first().toInt()
    val highNumInRange2 = list[1].split("-").last().toInt()
    if (max(lowNumInRange1,lowNumInRange2) - min(highNumInRange1,highNumInRange2) <= 0){return true}
    return false
}
fun getOverlapOccurrences(list: List<String>):Int{
    var occurrences = 0
   for (i in list.indices){
       if (checkIfPairIsOverLapping(list[i].split(","))){occurrences++}
   }
    return occurrences
}

