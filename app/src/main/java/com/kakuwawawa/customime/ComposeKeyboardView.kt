package com.kakuwawawa.customime

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import java.util.Dictionary

typealias ki = KeyModel.InputValue
typealias kc = KeyModel.CursorMove
typealias ks = KeyModel.StateChange

//===== KeyboardData =====//
val NormalKeyboardData = listOf(
    listOf(
        listOf(ks(State.SHIFT, "shift"))
    ),
    listOf(
        listOf(ki("q"), ki("w"), ki("e"), ki("r"), ki("t"), ki("y"), ki("u"), ki("i"), ki("o"), ki("p")),
        listOf(ki("a"), ki("s"), ki("d"), ki("f"), ki("g"), ki("h"), ki("j"), ki("k"), ki("l")),
        listOf(ki("z"), ki("x"), ki("c"), ki("v"), ki("b"), ki("n"), ki("m"))
    ),
    listOf(
        listOf(kc(CursorDirection.LEFT, "←"), kc(CursorDirection.RIGHT, "→"))
    )
)

class ComposeKeyboardView(
    context: Context
) : AbstractComposeView(context) {
    var onKeyEvent: (KeyModel) -> Unit = { }
    var keyboardState: () -> State = { State.DEFAULT }

    @Composable
    override fun Content() {
        // MockKeyboard()
        KeyboardLayout(NormalKeyboardData, onKeyEvent, keyboardState())
    }
}