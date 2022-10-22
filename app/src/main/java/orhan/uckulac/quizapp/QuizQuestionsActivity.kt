package orhan.uckulac.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import orhan.uckulac.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectanswers : Int = 0
    private var mUserName: String? = null

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    private var submitBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        progressBar = binding.progressBar
        tvProgress = binding.tvProgress
        tvQuestion = binding.tvQuestion
        ivImage = binding.ivImage

        tvOptionOne = binding.optionOne
        tvOptionOne?.setOnClickListener(this)

        tvOptionTwo = binding.optionTwo
        tvOptionTwo?.setOnClickListener(this)

        tvOptionThree = binding.optionThree
        tvOptionThree?.setOnClickListener(this)

        tvOptionFour = binding.optionFour
        tvOptionFour?.setOnClickListener(this)

        submitBtn = binding.submitbtn

        setQuestion()
        defaultOptionsView()
    }

    private fun setQuestion(){
        defaultOptionsView()
        setClickable(true)
        // make submit button not clickable to avoid skipping questions by the user
        submitBtn?.isClickable = false

        val question : Question = mQuestionsList!![mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if (mCurrentPosition == mQuestionsList!!.size){
            submitBtn?.text = "FINISH"
        } else {
            submitBtn?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        // since Options are nullable, use let
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv:TextView, selectedOptionNum: Int){
        // onclick, make everything default and then change selected option purple bg
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        // make submit button clickable
        submitBtn?.isClickable = true
        submitBtn?.setOnClickListener(this)

        tv.setTextColor((Color.parseColor("#363A43")))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    // set selected option numbers onclick to answer buttons
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne-> {
                tvOptionOne?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.optionTwo-> {
                tvOptionTwo?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.optionThree-> {
                tvOptionThree?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.optionFour-> {
                tvOptionFour?.let{
                    selectedOptionView(it, 4)
                }
            }

            R.id.submitbtn-> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }
                        else ->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectanswers)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectanswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        submitBtn?.text = "FINISH"
                    }else{
                        submitBtn?.text = "NEXT QUESTION"
                        setClickable(false)
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1->{
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2->{
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3->{
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4->{
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

    // make options disabled to avoid re-clicking
    private fun setClickable(status: Boolean){
        if (status){
            tvOptionOne?.isClickable = true
            tvOptionTwo?.isClickable = true
            tvOptionThree?.isClickable = true
            tvOptionFour?.isClickable = true
        } else{
            tvOptionOne?.isClickable = false
            tvOptionTwo?.isClickable = false
            tvOptionThree?.isClickable = false
            tvOptionFour?.isClickable = false
        }
    }
}