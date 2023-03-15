package com.aom.simplequiz.ui.main.fragment

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.get
import com.aom.simplequiz.Answer
import com.aom.simplequiz.Communicator
import com.aom.simplequiz.Question
import com.aom.simplequiz.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    private lateinit var communicator: Communicator
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentQuestionIndex: Int = 0

    private var questions: ArrayList<Question> = arrayListOf()
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        currentQuestionIndex = 0;
        score = 0;
        this.loadQuestion();
    }

    private fun loadQuestion() {
        score = 0
        questions.clear()
        questions.add(
            Question(
                1,
                "How many colors are there in a rainbow?",
                2,
                arrayListOf(
                    Answer(1, "Six"),
                    Answer(2, "Seven"),
                    Answer(3, "Eight"),
                    Answer(4, "Nine")
                )
            )
        )
        questions.add(
            Question(
                2,
                "What is the hardest natural substance in the world?",
                4,
                arrayListOf(
                    Answer(1, "Rock"),
                    Answer(2, "Brick"),
                    Answer(3, "Metal"),
                    Answer(4, "Diamond")
                )
            )
        )
        questions.add(
            Question(
                3,
                "Where is the Eiffel Tower?",
                1,
                arrayListOf(
                    Answer(1, "Paris"),
                    Answer(2, "Bangkok"),
                    Answer(3, "London"),
                    Answer(4, "Vice City")
                )
            )
        )
        questions.add(
            Question(
                4,
                "What is the world’s largest bird?",
                2,
                arrayListOf(
                    Answer(1, "Parrot"),
                    Answer(2, "Ostrich"),
                    Answer(3, "Owl"),
                    Answer(4, "Eagle")
                )
            )
        )
        questions.add(
            Question(
                5,
                "What are Google Chrome, Safari, Firefox, and Explorer?",
                1,
                arrayListOf(
                    Answer(1, "Web browser"),
                    Answer(2, "Youtube"),
                    Answer(3, "Paint"),
                    Answer(4, "Postman")
                )
            )
        )
        questions.add(
            Question(
                6,
                "What’s the name of Iron Man’s daughter?",
                4,
                arrayListOf(
                    Answer(1, "Clint"),
                    Answer(2, "Lilly"),
                    Answer(3, "Roger"),
                    Answer(4, "Morgan")
                )
            )
        )
        questions.add(
            Question(
                7,
                "How many planets are in our solar system?",
                1,
                arrayListOf(
                    Answer(1, "Eight"),
                    Answer(2, "Nine"),
                    Answer(3, "Ten"),
                    Answer(4, "Eleven")
                )
            )
        )
        questions.add(
            Question(
                8,
                "What is the color of a school bus?",
                3,
                arrayListOf(
                    Answer(1, "Green"),
                    Answer(2, "Red"),
                    Answer(3, "Yellow"),
                    Answer(4, "Blue")
                )
            )
        )
        questions.add(
            Question(
                9,
                "What do you use to write on a blackboard?",
                3,
                arrayListOf(
                    Answer(1, "Brush"),
                    Answer(2, "Pencil"),
                    Answer(3, "Chalk"),
                    Answer(4, "Spray")
                )
            )
        )
        questions.add(
            Question(
                10,
                "How many days are in a year?",
                4,
                arrayListOf(
                    Answer(1, "365"),
                    Answer(2, "361"),
                    Answer(3, "368"),
                    Answer(4, "360")
                )
            )
        )
        questions.shuffle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        this.renderQuestion(view);
        this.renderScore(view);
        communicator = activity as Communicator;
        var answerGroup: RadioGroup = view.findViewById<RadioGroup>(R.id.answerGroup);
        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            this.currentQuestionIndex++;
            if(view.findViewById<RadioButton>(answerGroup.checkedRadioButtonId).tag === "CORRECT") {
                this.score+=1;
            }
            if((currentQuestionIndex < questions.size)) {
                this.renderQuestion(view)
            } else{
                var bundle = Bundle()
                bundle.putInt("score", this.score);
                bundle.putInt("total", questions.size);
                communicator.emit("result", bundle)
            };
        }
        answerGroup.setOnCheckedChangeListener (fun (_: RadioGroup, id: Int) {
            var submitBtn = view.findViewById<Button>(R.id.submitButton)
            if(id > 0) submitBtn.isEnabled = true;
        })

        return view
    }

    private fun renderQuestion(view: View) {
        var question: Question = questions[currentQuestionIndex]
        var answers: List<Answer> = question.answers.shuffled()

        view.findViewById<TextView>(R.id.questionNoLabel).text = (currentQuestionIndex+1).toString()
        view.findViewById<TextView>(R.id.questionMessageLabel).text = (question.question)
        val answerGroup: RadioGroup = view.findViewById<RadioGroup>(R.id.answerGroup);
        for ((index, value) in answers.withIndex()) {
            (answerGroup[index] as RadioButton).text = value.message;
            (answerGroup[index] as RadioButton).tag = "INCORRECT";
            if(value.aid === question.aid) (answerGroup[index] as RadioButton).tag = "CORRECT";
        }
        answerGroup.clearCheck();
        view.findViewById<Button>(R.id.submitButton).isEnabled = false;
        this.renderStatus(view);
        this.renderScore(view);
    }

    private fun renderStatus(view: View){
        view.findViewById<TextView>(R.id.currentQuestionLabel).text = (this.currentQuestionIndex + 1).toString();
        view.findViewById<TextView>(R.id.maxQuestionLabel).text = (this.questions.count()).toString();
    }

    private fun renderScore(view: View){
        view.findViewById<TextView>(R.id.scoreLabel).text = this.score.toString();
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}