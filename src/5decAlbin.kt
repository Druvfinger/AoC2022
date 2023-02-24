package Inlamning_02

import java.io.File

/** 2022 - Day 5:
 * Rearrange stacks
Part 1 -  After the rearrangement procedure completes, what crate ends up on top of each stack?
 */

val d = File("src/Inlamning_02/data_2022_05.txt").readLines()

fun main() {

    println(getLastItems(reArrange(d)))
    println(reArrange2(d))
}

// Lösning 2 - Efter googling. Skrev om 3 av metoderna men behöll "getMoves":

/** Loopa igenom med längden av nedersta stringen(33) hoppa 4 för att hitta chars
grunden är map (index(var fjärde från 1 per rad) -> rowsWithStacks ( kolla igenom listan) och { get (index)}
map ( index = intereringen på loopen 1-5-9 etc -
getOrNull är bara argument i mapNotNull för att inte få error på null-värden på whitespace (som faktiskt inte är en char)
 */

fun createStacks2(l: List<String>): List<MutableList<Char>> {
    val rowsWithStacks = l.takeWhile { it.contains('[') }                                // Plocka ut stack-raderna
    val listOfStacks = (1..rowsWithStacks.last().length step 4)
        .map { index -> rowsWithStacks.mapNotNull { it.getOrNull(index) }.filter { !it.isWhitespace() }.toMutableList() }
    return listOfStacks
}

// Extensionsfunction - Implementering av interface Iterable
fun Iterable<Iterable<Char>>.getLastItems2(): String = map { it.first() }.joinToString("")

// Flytta funktionellt istället
fun reArrange2 (list: List<String>): String  {
    var l = createStacks2(list)
    getMoves(d).forEach { (amount, source, destination) -> val toBeMoved = l[source].take(amount)
        repeat(amount) { l[source].removeFirst() }
        l[destination].addAll(0, toBeMoved.reversed() ) }
    return l.getLastItems2()
}



// Lösning 1

// Returnera sista elementen i stackarna
fun getLastItems(l: List<List<String>>): String {
    val sb = StringBuilder()
    for (i in l) {
        sb.append(i.last())
    }
    return sb.toString()
}

// Utföra flytt
fun reArrange(l: List<String>): List<List<String>> {
    var moves = getMoves(l)
    var stacks = createStacks(l)

    for (i in 0..moves.size - 1) {
        for (j in 0..moves.get(i).get(0) - 1) {
            var take = moves.get(i).get(1)
            var place = moves.get(i).get(2)
            var itemToTake = stacks.get(take).last()
            stacks.get(take).removeAt(stacks.get(take).size - 1)
            stacks.get(place).add(itemToTake)
        }
    }

    return stacks
}

// Flyttinstruktioner till Lista
fun getMoves(l: List<String>): MutableList<MutableList<Int>> {
    val tempList = mutableListOf<MutableList<Int>>()
    for (s in l) {
        if (s.startsWith("move")) {
            var intList = mutableListOf<Int>()
            val parts = s.split(" ")
            intList.add(parts[1].toInt())
            intList.add(parts[3].toInt() - 1)
            intList.add(parts[5].toInt() - 1)
            tempList.add(intList)
        }
    }
    return tempList
}

// Skapa upp stackar
fun createStacks(l: List<String>): MutableList<MutableList<String>> {
    val stacks = mutableListOf<MutableList<String>>()
    for (y in 1..33 step 4) {
        val tempList = mutableListOf<String>()
        for (i in 7 downTo 0) {
            var s = l.get(i).get(y)
            if (!s.isWhitespace()) {
                tempList.add(s.toString())
            }
        }
        stacks.add(tempList)
    }
    return stacks
}
