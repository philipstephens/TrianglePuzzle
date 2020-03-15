package developer.philip.trianglepuzzle

import kotlin.random.Random
import kotlin.random.nextInt

class Directions() {
    var board = BooleanArray(MAXINDEX)
    var randomHole = 0

    var southEastPeg1 = IntArray(MAXINDEX)
    var southEastPeg2 = IntArray(MAXINDEX)
    var southWestPeg1 = IntArray(MAXINDEX)
    var southWestPeg2 = IntArray(MAXINDEX)
    var northEastPeg1 = IntArray(MAXINDEX)
    var northEastPeg2 = IntArray(MAXINDEX)
    var northWestPeg1 = IntArray(MAXINDEX)
    var northWestPeg2 = IntArray(MAXINDEX)
    
    var westPeg1 = IntArray(MAXINDEX)
    var westPeg2 = IntArray(MAXINDEX)
    var eastPeg1 = IntArray(MAXINDEX)
    var eastPeg2 = IntArray(MAXINDEX)

    var numLevels = NUMLEVELS
    var lastIndex = 0
    var min = 0
    var max = 0
    var spread = 0

    init {
        setupBoard(numLevels)
        setupPegs(numLevels)
    }

    fun restorePegs(dir: Int, holeNumber: Int, pegNumber: Int) {
        val peg1 = findPeg1(dir, holeNumber)
        val peg2 = findPeg2(dir, holeNumber)

        if(peg1 < 0) {
            println("Error restoring peg 1 (peg Number $pegNumber)")
            return
        }

        if(peg2 < 0) {
            println("Error restoring peg 1 (peg Number $pegNumber)")
            return
        }

        if(board[holeNumber] == false) {
            println("Error restoring hole number $holeNumber - Peg not found")
        }

        if(board[peg1] != false) {
            println("Error restoring peg1: hole occupied")
            return
        }

        if(board[peg2] != false) {
            println("Error restoring peg2: hole occupied")
            return
        }

//        println("Restoring peg number $pegNumber : peg2=$peg2  peg1=$peg1  holeNumber=$holeNumber")
        board[holeNumber] = false
        board[peg1] = true
        board[peg2] = true
//        printBoard()
    }

    fun findPeg1(dir: Int, holeNumber: Int): Int {

        if((holeNumber < 0) || (holeNumber > lastIndex)) {
            println("Peg1 index is out of range.")
            return -1
        }

        when(dir) {
            NE -> {
                return northEastPeg1[holeNumber]
            }

            NW -> {
                return northWestPeg1[holeNumber]
            }

            SE -> {
                return southEastPeg1[holeNumber]
            }

            SW -> {
                return southWestPeg1[holeNumber]
            }

            EAST -> {
                return eastPeg1[holeNumber]
            }

            WEST -> {
                return westPeg1[holeNumber]
            }
        }
        return -1
    }

    fun findPeg2(dir: Int, holeNumber: Int): Int {
        if((holeNumber < 0) || (holeNumber > lastIndex)) {
            println("Peg2 Index is out of range.")
            return -1
        }

        when(dir) {
            NE -> {
                return northEastPeg2[holeNumber]
            }

            NW -> {
                return northWestPeg2[holeNumber]
            }

            SE -> {
                return southEastPeg2[holeNumber]
            }

            SW -> {
                return southWestPeg2[holeNumber]
            }

            EAST -> {
                return eastPeg2[holeNumber]
            }

            WEST -> {
                return westPeg2[holeNumber]
            }
        }
        return -1
    }

    fun find(dir: Int, holeNumber: Int): Boolean {
        val peg1: Int
        val peg2: Int

        if(board[holeNumber] == true) return false
        peg1 = findPeg1(dir, holeNumber)
        peg2 = findPeg2(dir, holeNumber)

        if((peg1 >= 0) && (peg2 >= 0)) {
            if(board[peg1] && board[peg2]) return true
        }
        return false
    }

    fun findNextAvailableHole(currentHole: Int): Int {
        var i = currentHole // hole number in puzzle

        while(i <= lastIndex) {
            if(find(NE, i) == true) return i
            if(find(NW, i) == true) return i
            if(find(SE, i) == true) return i
            if(find(SW, i) == true) return i
            if(find(EAST, i) == true) return i
            if(find(WEST, i) == true) return i
            i++
        }
        return -1
    }

    fun movePeg(dir: Int, holeNumber: Int, pegNumber: Int) {
        var peg1: Int = 0
        var peg2: Int = 0

        if(board[holeNumber] == true) {
            println("Illegal move: target hole ($holeNumber) already occupied!")
            return
        }

        peg1 = findPeg1(dir, holeNumber)
        peg2 = findPeg2(dir, holeNumber)

        move(pegNumber, peg1, peg2, holeNumber)
        // printBoard()
        return
    }

    fun move(pegNumber: Int, peg1: Int, peg2:Int, holeNumber: Int) {
        if((peg1 >= 0) && (peg2 >= 0)) {
            if(board[peg1] && board[peg2]) {
                board[peg1] = false
                board[peg2] = false
                board[holeNumber] = true
//                println("Moving peg number $pegNumber from position $peg2 to $holeNumber removing $peg1.")
            } else {
                println("Illegal move for peg number $pegNumber.  Hole $peg1 or $peg2 is already occupied!")
            }
        } else {
            println("Peg Number: $pegNumber  Peg1: $peg1  Peg2: $peg2 is out of bounds!")
        }
    }

    fun setupBoard(numLevels: Int) {
        var min = 0
        var max = 0

        for (currentLevel in 0 until numLevels) {
            for (i in min..max) board[i] = true
            min = max + 1
            max = min + currentLevel + 1
        }

        lastIndex = min - 1
//        println("LastIndex = $lastIndex")

        generateRandomHole() // can be retrieved by getStartingHole
        board[randomHole] = false
    }

    fun printBoard() {
        var spread = 1
        var padding = numLevels - 1
        var min = 0
        var max = 0

        while (true) {
            print("..".repeat(padding))
            print(" ".repeat(spread - 1))

            for (i in min..max) {
                if (board[i]) {
                    print("1 ")
                } else print("0 ")
            }
            println()

            min = max + 1
            max = min + spread
            spread += 1
            padding -= 1
            if (min >= lastIndex) {
                break
            }
        }
    }

    fun setupNEPegs(lastLevel: Int) {
        var neTest1: Int
        var neTest2: Int
        var min = 0
        var max = 0

        var inc1 = 1
        var inc2 = 3

        for (currentLevel in 0 until lastLevel) {
            for(i in min..max) {
               // println("cl: $currentLevel min: $min  max: $max")
               neTest1 = min + inc1 + (i-min)
               neTest2 = min + inc2 + (i-min)

               if(currentLevel >= lastLevel-2) {
                   neTest1 =  -1
                   neTest2 = -1
               }

               // println("c1: $currentLevel ll: $lastLevel neTest1: $neTest1 neTest2: $neTest2")
               northEastPeg1[i] = neTest1
               northEastPeg2[i] = neTest2
            }

            inc1 +=1
            inc2 +=2
            min = max + 1
            max = min + currentLevel + 1
        }
    }

    fun setupNWPegs(lastLevel: Int) {
        var nwTest1: Int
        var nwTest2: Int
        var min = 0
        var max = 0

        var inc1 = 2
        var inc2 = 5

        for (currentLevel in 0 until lastLevel) {
            for(i in min..max) {
                // println("cl: $currentLevel min: $min  max: $max")
                nwTest1 = min + inc1 + (i-min)
                nwTest2 = min + inc2 + (i-min)

                if(currentLevel >= lastLevel-2) {
                    nwTest1 = -1
                    nwTest2 = -1
                }

                // println("c1: $currentLevel ll: $lastLevel neTest1: $neTest1 neTest2: $neTest2")
                northWestPeg1[i] = nwTest1
                northWestPeg2[i] = nwTest2
            }

            inc1 += 1
            inc2 += 2
            min = max + 1
            max = min + currentLevel + 1
        }
    }

    fun setupSEPegs(lastLevel: Int) {
        var seTest1: Int
        var seTest2: Int
        var min = 0
        var max = 0
        var min2 = 0
        var inc1 = -3
        var inc2 = -5
        var pos = 0

        for (currentLevel in 0 until lastLevel) {
            min2 = min + 2
            for(i in min..max) {
                // println("cl: $currentLevel min: $min  max: $max")
                pos = min2 - i

                if(i < min + 2) {
                    seTest1 = -1
                    seTest2 = -1
                } else {
                    seTest1 = min2 + inc1 + (i - min2)
                    seTest2 = min2 + inc2 + (i - min2)
                }

                // println("c1: $currentLevel ll: $lastLevel neTest1: $neTest1 neTest2: $neTest2")
                southEastPeg1[i] = seTest1
                southEastPeg2[i] = seTest2
            }

            if(min2 >= 5) {
                inc1 += -1
                inc2 += -2
            }

            min = max + 1
            max = min + currentLevel + 1
        }
    }


    fun setupSWPegs(lastLevel: Int) {
        var swTest1: Int
        var swTest2: Int
        var min = 0
        var max = 0

        var inc1 = -2
        var inc2 = -3


        for (currentLevel in 0 until lastLevel) {
            for(i in min..max) {
                // println("cl: $currentLevel min: $min  max: $max")
                if(i > max - 2) {
                    swTest1 = -1
                    swTest2 = -1
                } else {
                    swTest1 = min + inc1 + (i - min)
                    swTest2 = min + inc2 + (i - min)
                }

                // println("c1: $currentLevel ll: $lastLevel neTest1: $neTest1 neTest2: $neTest2")
                southWestPeg1[i] = swTest1
                southWestPeg2[i] = swTest2
            }

            if(currentLevel >= 2) {
                inc1 += -1
                inc2 += -2
            }

            min = max + 1
            max = min + currentLevel + 1
        }
    }


    fun setupWestPegs(lastLevel: Int) {
        var westTest1: Int
        var westTest2: Int
        var min = 0
        var max = 0

        for (currentLevel in 0 until lastLevel) {
            for(i in min..max) {
                westTest1 = i + 1
                westTest2 = i + 2

                if(i > max - 2) {
                    westTest1 = -1
                    westTest2 = -1
                }

                westPeg1[i] = westTest1
                westPeg2[i] = westTest2
            }

            min = max + 1
            max = min + currentLevel + 1
        }
    }

    fun setupEastPegs(lastLevel: Int) {
        var eastTest1: Int
        var eastTest2: Int
        var min = 0
        var max = 0

        for (currentLevel in 0 until lastLevel) {
            for(i in min..max) {
                eastTest1 = i - 1
                eastTest2 = i - 2

                if(i < min + 2) {
                    eastTest1 = -1
                    eastTest2 = -1
                }

                eastPeg1[i] = eastTest1
                eastPeg2[i] = eastTest2
            }

            min = max + 1
            max = min + currentLevel + 1
        }
    }

    fun setupPegs(lastLevel: Int) {
        setupNEPegs(lastLevel)
        setupNWPegs(lastLevel)
        setupSEPegs(lastLevel)
        setupSWPegs(lastLevel)
        setupEastPegs(lastLevel)
        setupWestPegs(lastLevel)

    }

    fun printDirection(dir: Int, peg: Int, lastLevel: Int) {
        var spread = 1
        var padding = lastLevel
        var min = 0
        var max = 0

        println()

        for (currentLevel in 0 until lastLevel) {
            print("..".repeat(padding))
            print(" ".repeat(spread - 1))

            for (i in min..max) {
               when(dir) {
                    NE -> {
                        if(peg == 1) print(northEastPeg1[i].toString()) else print(northEastPeg2[i].toString())
                        print(" ")
                    }

                    NW -> {
                        if(peg == 1) print(northWestPeg1[i].toString()) else print(northWestPeg2[i].toString())
                        print(" ")
                    }

                    SW -> {
                        if(peg == 1) print(southWestPeg1[i].toString()) else print(southWestPeg2[i].toString())
                        print(" ")
                    }

                    SE -> {
                        if(peg == 1) print(southEastPeg1[i].toString()) else print(southEastPeg2[i].toString())
                        print(" ")
                    }

                    WEST -> {
                        if(peg == 1) print(westPeg1[i].toString()) else print(westPeg2[i].toString())
                        print(" ")
                    }

                    EAST -> {
                        if(peg == 1) print(eastPeg1[i].toString()) else print(eastPeg2[i].toString())
                        print(" ")
                    }
                }
            }
            println()

            min = max + 1
            max = min + spread
            spread += 1
            padding -= 1
        }
    }

    fun generateRandomHole() {
        randomHole = Random.nextInt(0..14)
    }

    fun getStartingHole(): Int {
        return randomHole
    }
}

//  SouthEast values                   Peg1 Array               Peg2 Array
//
//               0                         -1                          5
//            1     2                    -1    2                     8    9
//          3    4    5                -1    4    5                12   13  14
//        6   7     8   9            -1    7    8   9            -1   -1  -1   -1
//      10  11   12   13  14       -1  -1   -1   -1   -1       -1   -1  -1   -1   -1