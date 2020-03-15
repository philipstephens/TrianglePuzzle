package developer.philip.trianglepuzzle

class Card(val dir: Int, val pegNumber: Int, val source: Int, val destination: Int, val middle: Int ) {

    fun println() {
        var dirString: String

        when(dir) {
            NE -> dirString = "North East"
            NW -> dirString = "North West"
            SE -> dirString = "South East"
            SW -> dirString = "South West"
            EAST -> dirString = "East"
            WEST -> dirString = "West"
            else -> dirString = "DIRECTION ERROR"
        }

        println("Peg Number: $pegNumber  Move $dirString from $source to $destination over $middle")
    }
}