package orhan.uckulac.quizapp
import android.content.Intent
import orhan.uckulac.quizapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startBtn: Button = binding.startBtn
        val editTextName: EditText = binding.editTextName

        startBtn.setOnClickListener {
            if (editTextName.text.isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, editTextName.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }
}