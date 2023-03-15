package com.aom.simplequiz

data class Question(

    var qid: Int,
    var question: String,
    var aid: Int,
    var answers: ArrayList<Answer> = arrayListOf()

)

data class Answer(

    var aid: Int,
    var message: String

)
