package ru.netology.androidhw3

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.concurrent.ConcurrentHashMap.KeySetView

fun format(numberView: Int): String {
    return when {
        numberView in 0..999 -> numberView.toString()
        numberView in 1000..9999 -> {
            val oneNumber = numberView/1000
            val twoNumber = (numberView%1000)/100
            if (twoNumber == 0) "${oneNumber}K" else "${oneNumber}.${twoNumber}K"
        }

        numberView in 10000..999999 -> "${numberView/1000}K"
        else -> "${numberView/1_000_000}.${(numberView%1_000_000)/100_000}M"
    }
}
object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}



