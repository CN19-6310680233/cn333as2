package com.aom.simplequiz

import android.os.Bundle

interface Communicator {
    fun emit(action: String, params: Bundle = Bundle())
}