import java.io.File

// Part 1
/*
    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
*/
// so for each position in sub list [i] in range of numOfStacks
//sub list[0] would be " ", [D], " " so probably the easiest way to get [D] would be to get sub list[i].replace ("[","")

fun main() {
    val list = File("src/test.txt").readLines()
    val listOfCrates = list.subList(0,getPackingList(list))
    val numberOfColumns = listOfCrates.last().replace(" ", "").count()
    val listWithoutKeys = listOfCrates.take(numberOfColumns)
    val listWithoutKeys1 =  listWithoutKeys.chunked(1)
    println(listOfCrates)
    println(numberOfColumns)
    println(listWithoutKeys)
    println(listWithoutKeys1)
    //println(findOutWhatisInWhichColumn(listWithoutKeys1,numberOfColumns))
}
fun getPackingList(list: List<String>): Int{
    var counter = 0
    for (line in list)
        if (line == ""){return counter}
    else{counter++}
    return counter
}
/*fun findOutWhatisInWhichColumn(listWithoutKeys1: List<List<String>>, numOfColumns:Int){
    var templist = mutableListOf<String>()
    for (i in 0.. numOfColumns){
        val test = listWithoutKeys1[i].to
    }
}*/


