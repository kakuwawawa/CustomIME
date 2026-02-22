package com.kakuwawawa.customime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//===== KeyboardLayout =====//
@Composable
fun KeyboardLayout(setting: List<List<List<KeyModel>>>,
                   onClick: (KeyModel) -> Unit,
                   keyBoardState: State){
    Row {
        val l1Modifier = Modifier.fillMaxWidth()
        setting.forEach { l1 ->
            Column(modifier = l1Modifier) {
                l1.forEach { l2 ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        l2.forEach { keyModel ->
                            KeyButton(keyModel, onClick, Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
//===== KeyboardButton =====//
private val KeyButtonModifier = Modifier
    .background(Color.LightGray)
    .border(width = 1.dp, color = Color.White)
    .width(32.dp)
    .height(40.dp)
    .padding(0.dp)
@Composable
fun KeyButton(
    keyModel: KeyModel,
    onClick: (KeyModel) -> Unit,
    modifier: Modifier
){
    TextButton(modifier = modifier
        .background(Color.LightGray)
        .border(color = Color.White, width = 1.dp),
        onClick = { onClick(keyModel) }) {
        Text(
            keyModel.label, modifier = Modifier.padding(0.dp).align(Alignment.Top),
            fontSize = 15.sp, color = Color.Black, textAlign = TextAlign.Left
        )
    }
}
sealed class KeyModel{
    abstract val label: String
    data class InputValue(val value: String, override val label: String): KeyModel(){
        constructor(value: String) : this(value, value)
    }
    data class Spacer(val space: Float, override val label: String = ""): KeyModel()
    data class CursorMove(val cursorDirection: CursorDirection, override val label: String): KeyModel()
    data class StateChange(val state: State, override val label: String): KeyModel()
}
enum class CursorDirection{
    LEFT,
    RIGHT,
    UP,
    DOWN
}
enum class State{
    DEFAULT,
    SHIFT,
    CTRL,
    ALT
}
