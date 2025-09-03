package com.example.rps

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var playerChoice: ImageView
    private lateinit var computerChoice: ImageView
    private lateinit var resultText: TextView
    private lateinit var scoreText: TextView

    private var playerScore = 0
    private var computerScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerChoice = findViewById(R.id.playerChoice)
        computerChoice = findViewById(R.id.computerChoice)
        resultText = findViewById(R.id.resultText)
        scoreText = findViewById(R.id.scoreText)

        val rockButton: Button = findViewById(R.id.rockButton)
        val paperButton: Button = findViewById(R.id.paperButton)
        val scissorsButton: Button = findViewById(R.id.scissorsButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        rockButton.setOnClickListener { playGame("rock") }
        paperButton.setOnClickListener { playGame("paper") }
        scissorsButton.setOnClickListener { playGame("scissors") }
        resetButton.setOnClickListener { resetGame() }
    }

    private fun playGame(playerMove: String) {
        val moves = listOf("rock", "paper", "scissors")
        val computerMove = moves[Random.nextInt(moves.size)]

        // Show player’s choice immediately
        when (playerMove) {
            "rock" -> playerChoice.setImageResource(R.drawable.rock)
            "paper" -> playerChoice.setImageResource(R.drawable.paper)
            "scissors" -> playerChoice.setImageResource(R.drawable.secissor)
        }

        resultText.text = "Computer is thinking..."

        // Delay computer’s choice and result by 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            when (computerMove) {
                "rock" -> computerChoice.setImageResource(R.drawable.rock)
                "paper" -> computerChoice.setImageResource(R.drawable.paper)
                "scissors" -> computerChoice.setImageResource(R.drawable.secissor)
            }

            // Decide winner
            val result = when {
                playerMove == computerMove -> "It's a Draw!"
                (playerMove == "rock" && computerMove == "scissors") ||
                        (playerMove == "paper" && computerMove == "rock") ||
                        (playerMove == "scissors" && computerMove == "paper") -> {
                    playerScore++
                    if(playerScore==5){
                        AlertDialog.Builder(this)
                            .setTitle("Result")
                            .setMessage("Congratulations 🎉, you won.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss() // closes the dialog when OK is pressed
                            }
                            .show()
                        resetGame()
                    }
                    "You Win! 🎉"
                }
                else -> {
                    computerScore++
                    if(computerScore==5){
                        AlertDialog.Builder(this)
                            .setTitle("Result")
                            .setMessage("Better luck next time.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss() // closes the dialog when OK is pressed
                            }
                            .show()
                        resetGame()
                    }
                    "Computer Wins! 💻"
                }
            }

            resultText.text = result
            scoreText.text = "Player: $playerScore | Computer: $computerScore"

        }, 1000) // 1-second delay
    }

    private fun resetGame() {
        playerScore = 0
        computerScore = 0
        resultText.text = "Choose your move!"
        scoreText.text = "Player: 0 | Computer: 0"
        playerChoice.setImageResource(android.R.drawable.ic_menu_help)
        computerChoice.setImageResource(android.R.drawable.ic_menu_help)
    }
}
