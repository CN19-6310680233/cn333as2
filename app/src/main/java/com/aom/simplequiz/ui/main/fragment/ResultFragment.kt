package com.aom.simplequiz.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.aom.simplequiz.Communicator
import com.aom.simplequiz.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val SCORE_PARAM = "score"
private const val TOTAL_PARAM = "total"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private lateinit var communicator: Communicator
    // TODO: Rename and change types of parameters
    private var score: Int = 0;
    private var total: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE_PARAM)
            total = it.getInt(TOTAL_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        communicator = activity as Communicator;

        view.findViewById<TextView>(R.id.totalScoreLabel).text = this.score.toString() + " / " + this.total.toString();
        view.findViewById<Button>(R.id.homeButton).setOnClickListener {
            communicator.emit("menu")
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(score: Int, total: Int) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(SCORE_PARAM, score)
                    putInt(TOTAL_PARAM, total)
                }
            }
    }
}