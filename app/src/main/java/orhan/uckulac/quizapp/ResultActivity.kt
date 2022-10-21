package orhan.uckulac.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import orhan.uckulac.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mUserName = intent.getStringExtra(Constants.USER_NAME)
        val mTotalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val mCorrectAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        val tvName: TextView = binding.tvName
        val tvScore: TextView = binding.tvScore
        val btnFinish: Button = binding.btnFinish

        tvName.text = mUserName
        tvScore.text = "Your score is $mCorrectAnswers of $mTotalQuestions questions"

        btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}