import java.io.File
import java.nio.file.Files

//find max value in file
fun main() {
    val list: List<String> = File("src/1decInput.txt").readLines()

    println(findElfWithMaxValue(createElfValues(list))) // part1
    println(findTop3ElfsMaxValues(createElfValues(list))) //part2

    println()

    println(findElfWithMaxValueImproved(list)) //  part1 improved
    println(findTop3ElfsMaxValuesImproved(list)) // part2 improved
}

fun createElfValues(list: List<String>): List<Int>{
    var currentZero = 0                                       //agerar counter för vilken plats i listan jag är på
    val listOfZeroes = mutableListOf(0)         //skapar en lista så jag kan placera de summerade värdena i
    for (line in list){                                 //går igenom varje sträng i in-parameter listan
        if (line == ""){                                       //är strängen en tom sträng så vet vi att det kommande linje är en ny grupp
            listOfZeroes.add(0)                                //lägger till en entry i listan så vi har nåt att lägga värdet till
            currentZero++                                      //inkrementerar var vi är i listan så att vi lägger till på rätt ställe
        }else {listOfZeroes[currentZero] += line.toInt()}      //får vi in nåt som inte är en tom sträng så tar vi värdet på det indexet och sumerar det till vår nuvarande entry i listan
    }
    return listOfZeroes                                        //skickar tillbaka vår lista av summerade värden
} // part1
fun findElfWithMaxValue(list: List<Int>):Int{
    var temp: Int = 0
    for (num in list){
        if (temp < num){temp = num}
    }
    return temp
} // part1
fun findTop3ElfsMaxValues(list: List<Int>):Int{
    val tempList: List<Int> = list.sortedDescending()
    return tempList[0]+tempList[1]+tempList[2]
} // part2


//------------------------------------------------------------------------------------------------------------------------------------------
//IMPROVED SOLUTION
//https://medium.com/@inzuael/advent-of-code-2022-using-kotlin-3fec4c0b23c6

fun createElfValuesImproved(list: List<String>): List<Int> {
    val elvesAmount = mutableListOf(0)
    list.forEach{if (it.isNotBlank()){elvesAmount[elvesAmount.lastIndex] += it.toInt()}else{elvesAmount.add(0)}}
    return  elvesAmount.toList()
}
fun findElfWithMaxValueImproved(list: List<String>):Int{ //aka part1
    val elvesAmount = createElfValuesImproved(list)
    return elvesAmount.max()
}
fun findTop3ElfsMaxValuesImproved(list: List<String>):Int{
    val elvesAmount = createElfValuesImproved(list)
    return elvesAmount.sortedDescending().take(3).sum()
}

/* PART 1
* Ganska lik förra lösningen men elegantare skrivet medans jag gjorde en for-loop så gör detta istället en forEach
* och eftersom det vi går igenom varje element i listan så är det bara en sak i taget och it är vad vi för tilfället
* går igenom så är det min variabel currentZero i tidigare försöket så om it inte är en tom sträng så lägger vi till värdet
* i vår nuvarande rad i listan annars lägger vi till en rad i listan
*
* nästa metod i del 1 gjorde jag en for loop för att jämföra alla värden och i förbättringen så finns det än färdig funktion för det .max()
*
* PART 2
* Denna har också samma tanke som jag hade fast gör det återigen på ett elegantare sätt så istället för att plocka ut de tre värdena för "hand"
* så fanns funktionen .take() vilket jag inte kände till innan och summerade han dem 3 värdena
* exakt samma idé över hur det är löst men en tar bättre användning av funktionerna som redan finns
* */