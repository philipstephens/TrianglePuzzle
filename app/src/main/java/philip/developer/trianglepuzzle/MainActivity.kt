package philip.developer.trianglepuzzle

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import developer.philip.trianglepuzzle.Card
import developer.philip.trianglepuzzle.Solver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val solver = Solver()
    var finishedBlinking: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSolve = findViewById<Button>(R.id.buttonSolve)
        val buttonNext  = findViewById<Button>(R.id.buttonNext)
        val buttonPrev  = findViewById<Button>(R.id.buttonPrev)

        resetPuzzleDisplay()
        buttonSolve.setOnClickListener { solvePuzzle() }
        buttonNext.setOnClickListener { getNextStep() }
        buttonPrev.setOnClickListener { getPreviousStep() }
    }

    fun getNextStep() {
        val buttonSolve = findViewById<Button>(R.id.buttonSolve)
        val buttonNext = findViewById<Button>(R.id.buttonNext)
        val buttonPrev = findViewById<Button>(R.id.buttonPrev)

        val card: Card?
        val fromPeg: ImageView
        val toPeg: ImageView
        val jumpPeg: ImageView

        var counter: Int

        card = solver.getNextMove()
        if (card == null) { // finished puzzle
            buttonNext.setEnabled(false)
            buttonPrev.setEnabled(false)
            buttonSolve.setEnabled(true)
            solver.directions.setupBoard(5)
            resetPuzzleDisplay()
        } else {
            buttonNext.setEnabled(false)
            buttonPrev.setEnabled(false)

            fromPeg = convertID(card.source)
            jumpPeg = convertID(card.middle)
            toPeg   = convertID(card.destination)

            val timer = object: CountDownTimer(3000, 500) {
                var counter = 0

                override fun onTick(millisUntilFinished: Long) {
                    if(fromPeg.getVisibility() == View.INVISIBLE) {
                        fromPeg.setVisibility(View.VISIBLE)
                        toPeg.setVisibility(View.VISIBLE)
                    } else {
                        fromPeg.setVisibility(View.INVISIBLE)
                        toPeg.setVisibility(View.INVISIBLE)
                    }
                }

                override fun onFinish() {
                    fromPeg.setVisibility(View.VISIBLE)
                    toPeg.setVisibility(View.VISIBLE)
                    buttonNext.setEnabled(true)
                    buttonPrev.setEnabled(true)

                    fromPeg.setImageResource(R.drawable.hole)
                    toPeg.setImageResource(R.drawable.tee)
                    jumpPeg.setImageResource(R.drawable.hole)
                }
            }
            timer.start()
        }
    }

    fun getPreviousStep() {
        val buttonSolve = findViewById<Button>(R.id.buttonSolve)
        val buttonNext = findViewById<Button>(R.id.buttonNext)
        val buttonPrev = findViewById<Button>(R.id.buttonPrev)

        val card: Card?
        val fromPeg: ImageView
        val toPeg: ImageView
        val jumpPeg: ImageView

        var counter: Int

        card = solver.getPrevMove()
        if (card == null) { // start of puzzle
            buttonNext.setEnabled(true)
            buttonPrev.setEnabled(false)
            buttonSolve.setEnabled(true)

        } else {
            // reverse card directions

            fromPeg = convertID(card.destination)
            jumpPeg = convertID(card.middle)
            toPeg   = convertID(card.source)

            fromPeg.setImageResource(R.drawable.hole)
            jumpPeg.setImageResource(R.drawable.tee)
            toPeg.setImageResource(R.drawable.tee)

            fromPeg.setVisibility(View.VISIBLE)
            jumpPeg.setVisibility(View.VISIBLE)
            toPeg.setVisibility(View.VISIBLE)

            buttonNext.setEnabled(true)
            buttonPrev.setEnabled(true)
            buttonSolve.setEnabled(false)
        }
    }

    fun convertID(pegID: Int): ImageView {
        when(pegID) {
            0 -> return ivPeg0
            1 -> return ivPeg1
            2 -> return ivPeg2
            3 -> return ivPeg3
            4 -> return ivPeg4
            5 -> return ivPeg5
            6 -> return ivPeg6
            7 -> return ivPeg7
            8 -> return ivPeg8
            9 -> return ivPeg9
            10 -> return ivPeg10
            11 -> return ivPeg11
            12 -> return ivPeg12
            13 -> return ivPeg13
            else -> return ivPeg14
        }
    }

    fun resetPuzzleDisplay() {
        var pegImage: ImageView

        for(i in 0..14) {
            pegImage = convertID(i)
            pegImage.setImageResource(R.drawable.tee)
        }

        pegImage = convertID(solver.directions.getStartingHole())
        pegImage.setImageResource(R.drawable.hole)

    }

    fun solvePuzzle() {
        val buttonSolve = findViewById<Button>(R.id.buttonSolve)
        val buttonNext  = findViewById<Button>(R.id.buttonNext)
        val buttonPrev  = findViewById<Button>(R.id.buttonPrev)

        solver.process(solver.directions.lastIndex)
        buttonNext.setEnabled(true)
        buttonPrev.setEnabled(false)
        buttonSolve.setEnabled(false)
        //solver.showSolution()
    }
}
