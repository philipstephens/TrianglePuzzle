package developer.philip.trianglepuzzle

class Solver {

   val directions = Directions()
   var nextPegStack = Stack()
   var prevPegStack = Stack()

   fun process(pegNumber: Int) : Boolean {

        var newHole: Int
        var success: Boolean
        var card: Card

        if(pegNumber == 1) {
            return true
        }

        newHole = directions.findNextAvailableHole(0)

        while((newHole >= 0) && (newHole <= directions.lastIndex)) {

            // conditions for failure:  Wrong direction tried or wrong holenumber
            // try every direction for newHole

            if (directions.find(NE, newHole) == true) {
                directions.movePeg(NE, newHole, pegNumber)

                card = Card(NE, pegNumber,
                            directions.findPeg2(NE, newHole),
                            newHole,
                            directions.findPeg1(NE,newHole))

                success = process(pegNumber - 1)

                if (!success) {
                    directions.restorePegs(NE, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            if (directions.find(NW, newHole) == true) {
                directions.movePeg(NW, newHole, pegNumber)

                card = Card(NW, pegNumber,
                    directions.findPeg2(NW, newHole),
                    newHole,
                    directions.findPeg1(NW,newHole))

                success = process(pegNumber - 1)

                if (success == false) {
                    directions.restorePegs(NW, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            if (directions.find(SE, newHole) == true) {
                directions.movePeg(SE, newHole, pegNumber)

                card = Card(SE, pegNumber,
                    directions.findPeg2(SE, newHole),
                    newHole,
                    directions.findPeg1(SE,newHole))

                success = process(pegNumber - 1)

                if (!success) {
                    directions.restorePegs(SE, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            if (directions.find(SW, newHole) == true) {
                directions.movePeg(SW, newHole, pegNumber)

                card = Card(SW, pegNumber,
                    directions.findPeg2(SW, newHole),
                    newHole,
                    directions.findPeg1(SW, newHole))

                success = process(pegNumber - 1)

                if (!success) {
                    directions.restorePegs(SW, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            if (directions.find(EAST, newHole) == true) {
                directions.movePeg(EAST, newHole, pegNumber)

                card = Card(EAST, pegNumber,
                    directions.findPeg2(EAST, newHole),
                    newHole,
                    directions.findPeg1(EAST, newHole))

                success = process(pegNumber - 1)

                if (!success) {
                    directions.restorePegs(EAST, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            if (directions.find(WEST, newHole) == true) {
                directions.movePeg(WEST, newHole, pegNumber)

                card = Card(WEST, pegNumber,
                    directions.findPeg2(WEST, newHole),
                    newHole,
                    directions.findPeg1(WEST,newHole))

                success = process(pegNumber - 1)

                if (!success) {
                    directions.restorePegs(WEST, newHole, pegNumber)
                } else {
                    nextPegStack.push(card)
                    return true
                }
            }

            newHole = directions.findNextAvailableHole(newHole+1)
        }

        return false
    }
    
    fun printStack() {
        var card: Card

        println("**********************************************")
        while(!nextPegStack.isEmpty()) {
            card = nextPegStack.pop() as Card
            card.println()
        }
    }

    fun showSolution() {
        var card: Card
        directions.setupBoard(directions.numLevels)

        while(!nextPegStack.isEmpty()) {
           card = nextPegStack.pop() as Card
           directions.movePeg(card.dir, card.destination, card.pegNumber)
            directions.printBoard()
            println()
        }
    }

    fun getNextMove() : Card? {
        var card: Card
        if(!nextPegStack.isEmpty()) {
            card = nextPegStack.pop() as Card
            prevPegStack.push(card)
            return card
        }
        return null
    }

    fun getPrevMove() : Card? {
        var card: Card
        if(!prevPegStack.isEmpty()) {
            card = prevPegStack.pop() as Card
            nextPegStack.push(card)
            return card
        }
        return null
    }
}
