import java.io.File

//Part 1
//Rugsack has 2 compartments
//varje "compartment" har ett hem till specifika typer av items
//compartments kan betraktas med hälften av itemsen så från [middle] upp är compartment 2
//1 item per rugsack is wrong item type id is litet A eller Stort A
//a-z = 1-26 A-Z = 27-56
//hitta doubletter sen konvertera till priopäng och summera

//DEL 2
//find "item" that 3 elves have in common(aka badge)
//every 3 lines is 1 group
// get score


fun main(){
    val list = File("src/3decInput.txt").readLines()
    println(calculateSumOfPrioScoreAllRugsacks(list))//part1
    println(calculateSumOfPrioScoreAllGroups(list))//part2

    println()

    println(part1(list))
    println(part2(list))
}

fun splitRugsackCompartments(s: String):List<String>{
    val stringLenght: Int = s.count()
    val returnList: MutableList<String> = mutableListOf()
    val a: String = s.substring(stringLenght/2)
    val b: String = s.substring(0,stringLenght/2)
    returnList.add(b)
    returnList.add(a)
    return returnList
}//part1
fun findCommonCharacterBetweenCompartments(list:List<String>):Char{
    val a: CharArray = list[0].toCharArray()
    val b: CharArray = list[1].toCharArray()
    for (c in a){
        if (b.contains(c)){
            return c}
    }
    return 'X'
}//part1
fun determinePrioScore(c:Char):Int{
    val bigLetter = 26
    var counter = 0
    if (c.isLowerCase()) {
        for (x in 'a'..'z') {
            counter++
            if (x == c)
                return counter
        }
    }else if(c.isUpperCase()){
        for (x in 'A'..'Z') {
            counter++
            if (x == c)
                return counter + bigLetter
        }
    }
    return -1
}//part1
fun calculateSumOfPrioScoreAllRugsacks(list: List<String>):Int{
    val end: Int = list.size-1
    var sum = 0
    for (i in 0..end){
        sum +=determinePrioScore(findCommonCharacterBetweenCompartments(splitRugsackCompartments(list[i])))
    }
    return sum
}//part1

fun findGroupsBadge(list: List<String>, group:Int): Char {
    val tempList: MutableList<String> = mutableListOf()
    val keyList: List<Char> = createKeys()
    for (i in group until group+3){
     tempList.add(list[i])
    }
    val a: CharArray = tempList[0].toCharArray()
    val b: CharArray = tempList[1].toCharArray()
    val c: CharArray = tempList[2].toCharArray()
    for (x in keyList){
        if (a.contains(x) && b.contains(x) && c.contains(x)){
            return x
        }
    }
    return 'X'
}//part2
fun createKeys():List<Char>{
    val returnList: MutableList<Char> = mutableListOf()
    for (c in 'a'..'z'){
        returnList.add(c)}
    for (c in 'A'..'Z'){
        returnList.add(c)
    }
    return returnList
}//part2
fun calculateSumOfPrioScoreAllGroups(list: List<String>):Int{
    var sum = 0
    val end = list.size-1
    for (i in 0 until end step 3){
        sum += determinePrioScore(findGroupsBadge(list,i))
    }
    return sum
}//part2

//--------------------------------------------------------------------------------------
//IMPROVED SOLUTION
//https://medium.com/@inzuael/advent-of-code-2022-using-kotlin-3fec4c0b23c6

fun Char.calculatePriority(): Int {
    return if (this.isUpperCase()) (this - 'A' + 27) else (this - 'a' + 1)
}
fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val (part1, part2) = line.chunked(line.length / 2).map { it.toSet() }
        part1.intersect(part2).first().calculatePriority()
    }
}
fun part2(input: List<String>): Int {
    return input.chunked(3).sumOf { group ->
        val (elv1, elv2, elv3) = group.map { it.toSet() }   //går det att mappa det till charArray?? och har charArray intersect??
        elv1.intersect(elv2).intersect(elv3).first().calculatePriority()
    }
}

/* PART 1
* Första metoden är rätt intressant på ett sätt att det är en extension på klassen Char som du pratade om
* det vi gör är i princip att 'a' har ett numeriskt värde bakom sig att det är på 1 (plats 1 i alfabetet)
* och vi tar egentligen bort bokstaven och lägger till lika mycket i värde så att numret vi får ut korresponderar
* med numret char skulle vart på men är en int
*
* Andra metoden är rätt komplicerad och tog mig ett tag att förstå mig på
* 1. input för att vi ska gå igenom vår lista
*
* 2. sumOf kommer efteråt eftersom att vi vill ha summan av de två prioriteter vi
* försöker lista ut
*
* 3. Vi får gå lite bakåt på nästa rad i funktionen, vi delar upp strängen vi itererar på två delat på mitten
* där jag lärde mig att chunked är typ som split men restriktionerna för det är storleken på var del och inte en
* delimiter som i split så då har vi gått från mitten av raden vidare till att vi mappar våra två "chunks" vilket
* lätt uttryckt gör det till en lista och till ett set av char it är strängen som kommer från chunked
*
* 4. Nästa rad använder vi oss av intersect som är ny för mig men ger tillbaka värdet är lika i det båda set:en
* sen använder vi oss av .first() som ger oss tillbaka de första elementet vilket vi i denna uppgift vet att det bara är
* exakt en så det kommer alltid finnas där och sen kallar vi vår funktion calculatePriority på den då vi vet att den är
* en char och det vi kommer få tillbaka då är värdet av char där de är lika i båda
*
* PART 2
* Andra metoden är lättare att förstå tycker jag
* 1. Vi börjar med att dela upp vår lista i delar av 3 raders "chunks" och kallar som i förra på sumOf då vi vill ha summan
* för alla grupper
*
* 2. group i detta fall är våra "chunks" av listan uppdelat i 3 vi tar här en grupp i taget och delar upp den gruppen i
* tre stycken sets av char genom map och toSet
* elv1 = är först strängen i vår grupp osv.
*
* 3. Sen använder vi oss återigen av intersect för att hitta vad det finns för gemensamma nämnare
* här lärde jag mig att man kan kedja ihop dessa vilket var lite coolt
*
* 4. Vi fortsätter med logiken från förra metoden och plockar ut första char som kommer vara det vi vill ha
* och kallar på funktion att räkna ut prioriteten på grupperna
* */