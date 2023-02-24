import java.io.File

//DEL 1
//first column = opponent move A for Rock B for Paper C for Scissors
//second column = respons X for rock Y for Paper and Z for Scissors
// score single round is  1 for Rock 2 for Paper 3 for Scissors + score for round(Loss : 0 || Draw: 3 || Win: 6)
// total score = sum of round scores
// mål ta reda på total score

//DEL 2
//second column  = X go Loose || Y go DRAW || Z go WIN

fun main(){
    val list: List<String> = File("src/2decInput.txt").readLines()
    val riggedList = createRiggedList(list)
    println(getTotalScore(list)) //part1
    println(getTotalScore(riggedList)) //part2

    println()

    println(solvePart1(list))
    println(solvePart2(list))

}
fun getTotalScore(list:List<String>):Int{
    val end: Int = list.size-1
    var counter = 0

    var tempList:List<String>
    var a:String
    var b:String
    var sum = 0

    for (i in 0..end){
        tempList = list[counter].split(" ")
        a = tempList[0]
        b = tempList[1]
        sum += getPointsForRound(a,b)
        counter++

    }
    return sum
} // part1
fun getOutcome(a: String, b:String): String{
    if (a==("A") && b == "X" ||a == ("B") && b ==( "Y") || a == ("C") && b == ("Z")){return "DRAW"}
    else if (a == ("A") && b == ("Y" )|| a == ("B") && b == ("Z") || a == ("C") && b == ("X")){return "VICTORY"}
    else return "DEFEAT"
}//part1
fun getPointsForRound(a:String,b:String):Int{
    // b is what I Play
        when(getOutcome(a,b)){
            // vet att det är onädigt att plussa ihop poängen men hjälper mig visualisera vad som händer
            "DRAW" -> if (b == "X"){return 1+3}
            else if (b == "Y"){return 2+3}
            else{return 3+3}

            "VICTORY" ->if (b == "X"){return 1+6}
            else if (b == "Y"){return 2+6}
            else{return 3+6}

            "DEFEAT" -> if (b == "X"){return 1+0}
        else if (b == "Y"){return 2+0}
        else{return 3+0}
        }
    return -1
}//part1

fun createRiggedList(list:List<String>):List<String>{
    val returnList = mutableListOf<String>()
    val end: Int = list.size-1
    var counter: Int = 0

    var tempList:List<String>
    var a:String
    var b:String
    var stringForNewList:String

    for (i in 0..end){
        tempList = list[counter].split(" ")
        a = tempList[0]
        b = tempList[1]
        stringForNewList = changeLetterAccordingly(a,b)
        returnList.add(stringForNewList)
        counter++

    }
    return returnList
}//part2
fun changeLetterAccordingly(a:String, b: String):String{
    if (b == "X"){ // if it tells me to loose
        if (a == "A"){ return "A Z"}else if (a == "B"){return "B X"}else{return "C Y"}}
    else if (b == "Y") {// if it tells me to draw
        if (a == "A"){return "A X"}else if (a == "B"){return "B Y"}else{return "C Z"}}
    else{
        if (a == "A"){return "A Y"}else if (a == "B"){return "B Z"}else{return "C X"}
    }
}//part2


//------------------------------------------------------------------------
//IMPROVED SOLUTION
//https://todd.ginsberg.com/post/advent-of-code/2022/day2/

fun solvePart1(list: List<String>):Int{
    val part1Scores: Map<String, Int> =
        mapOf(
            "A X" to 1 + 3,
            "A Y" to 2 + 6,
            "A Z" to 3 + 0,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 1 + 6,
            "C Y" to 2 + 0,
            "C Z" to 3 + 3,
        )
    return list.sumOf {part1Scores[it] ?: 0}
}
fun solvePart2(list: List<String>): Int {
    val part2Scores: Map<String, Int> =
        mapOf(
            "A X" to 3 + 0,
            "A Y" to 1 + 3,
            "A Z" to 2 + 6,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 2 + 0,
            "C Y" to 3 + 3,
            "C Z" to 1 + 6,
        )
    return list.sumOf {part2Scores[it] ?: 0}
}

/* PART 1
* Den här Lösningen är otroligt mycket snyggare än min och känns extremt smidig
* så jag bestämde om det var en vinst/förlust/oavgjort först i en egentligen helt onödig funktion
* istället har han mappat de möjliga svaret och värdena till de nycklarna är poängen för det utfallet
* sen eftersom vi nu vet alla möjliga utfall så går vi igenom listan och summerar baserat på varje utfall
* vår input ger oss
* ?: 0 har vi för att kotlin vill att vi gardera oss mot nullPointerExceptions så vi lägger helt enkelt till
* 0 om det skulle vara null eller annat värde vi läser in. Det hade gått att skriva !! också men då fortsätter
* inte programmet om vi skulle få in ett felaktigt värde
*
* skillnaden här från hans lösning är att jag tar in listan i funktionen istället för att göra allt i main
*
* PART 2
* Detta är exakt vad jag beskrev över bara att värdena är ändrade för att reflektera det nya facit
* Overall är list.sumOf {part2Scores[it] ?: 0} lite svårt att förstå vid första glans men när jag gjorde det
* så känns den här lösningen otroligt snygg
* */