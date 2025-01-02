package com.example.tic_tac_toe_2

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var gameGrid: GridLayout
    private lateinit var statusText: TextView
    private lateinit var playAgainButton: Button

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameGrid = findViewById(R.id.gameGrid)
        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)

        initializeBoard()

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val button = Button(this).apply {
                    textSize = 24f
                    setOnClickListener { onCellClicked(this, i, j) }
                }
                val params = GridLayout.LayoutParams()
                params.rowSpec = GridLayout.spec(i)
                params.columnSpec = GridLayout.spec(j)
                button.layoutParams = params
                gameGrid.addView(button)
            }
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (board[row][col] == null && playAgainButton.visibility == Button.GONE) {
            board[row][col] = currentPlayer
            button.text = currentPlayer
            if (checkWin()) {
                statusText.text = "Player $currentPlayer Wins!"
                playAgainButton.visibility = Button.VISIBLE
            } else if (isBoardFull()) {
                statusText.text = "It's a Draw!"
                playAgainButton.visibility = Button.VISIBLE
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's Turn"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0 until 3) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it != null } }
    }

    private fun resetGame() {
        board.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                board[rowIndex][colIndex] = null
                val button = gameGrid.getChildAt(rowIndex * 3 + colIndex) as Button
                button.text = ""
            }
        }
        currentPlayer = "X"
        statusText.text = "Player $currentPlayer's Turn"
        playAgainButton.visibility = Button.GONE
    }
}