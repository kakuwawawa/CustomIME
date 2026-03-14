package com.kakuwawawa.customime

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

//===== KeyboardLayout =====//
@Composable
fun KeyboardLayout(keyboardLayout: List<List<List<KeyModel>>>,
                   index: Int,
                   onClick: (KeyModel, State) -> Unit,
                   keyBoardState: State)
    {
    val selectKeyboardLayout = keyboardLayout[index]
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        selectKeyboardLayout.forEach { row ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterHorizontally)
            ) {
                row.forEach { keyModel ->
                    if(keyModel is KeyModel.Spacer){
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                    else{
                        KeyButton(keyModel, keyBoardState, onClick, Modifier
                            .width(30.dp)
                            .height(50.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                            .border(color = Color.White, width = 1.dp, shape = RoundedCornerShape(5.dp)))
                    }
                }
            }
        }
    }
}
//===== KeyboardButton =====//
@Composable
fun KeyButton(
    keyModel: KeyModel,
    keyBoardState: State,
    onClick: (KeyModel, State) -> Unit,
    modifier: Modifier
){
    val lastState: State by rememberUpdatedState(keyBoardState)
    Box(modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures (onPress = {
                onClick(keyModel, lastState)
                delay(500)
                coroutineScope {
                    val job =  launch {
                        while(isActive){
                            onClick(keyModel, lastState)
                            delay(50)
                        }
                    }
                tryAwaitRelease()
                job.cancel()
                }
            })
        }) {
        Text(
            keyModel.nowLabel(keyBoardState), modifier = Modifier.padding(0.dp).align(Alignment.Center),
            fontSize = 20.sp, color = Color.Black, textAlign = TextAlign.Left, overflow = TextOverflow.Clip
        )
    }
}
sealed class KeyModel{
    abstract fun nowLabel(state: State): String
    data class InputValue(val value: String, val label: String, val shiftValue: String, val shiftLabel: String): KeyModel(){
        constructor(value: String) : this(value, value, value, value)
        companion object{
            fun valueLabelThis(value: String, label: String) = InputValue(value, label, value, label)
            fun valueShiftValueThis(value: String, shiftValue: String) = InputValue(value, value, shiftValue, shiftValue)
        }
        override fun nowLabel(state: State) = if(state == State.SHIFT) {shiftLabel} else {label}
    }
    data class Spacer(@FloatRange val space: Float, val label: String = ""): KeyModel(){
        override fun nowLabel(state: State): String = label
    }
    data class CursorMove(val cursorDirection: CursorDirection, val label: String): KeyModel(){
        override fun nowLabel(state: State): String = label
    }
    data class StateChange(val state: State, val label: String, val modeLabel: String): KeyModel(){
        constructor(state: State, label: String): this(state, label, label)
        override fun nowLabel(state: State): String = if(state == this.state) modeLabel else label
    }
    data class DeleteStr(val delete: Delete, val label: String): KeyModel(){
        override fun nowLabel(state: State): String = label
    }
    data class Enter(val label: String = "\u21B5"): KeyModel(){
        override fun nowLabel(state: State): String = label
    }
    data class PageChange(val page: Int, val label: String): KeyModel(){
        override fun nowLabel(state: State): String = label
    }
    data class CodeInput(val code: Int, val label: String, val shiftLabel: String, val metaEventKind: MetaEventKind): KeyModel(){
        constructor(code: Int, label: String) : this(code, label, label, MetaEventKind.Inheart)
        constructor(code: Int, label: String, shiftLabel: String) : this(code, label, shiftLabel, MetaEventKind.Inheart)
        constructor(code: Int, label: String, metaEventKind: MetaEventKind) : this(code, label, label, metaEventKind)
        override fun nowLabel(state: State): String = if (state == State.SHIFT) { shiftLabel } else { label }
    }

}
sealed class MetaEventKind{
    object Inheart: MetaEventKind()
    object Default: MetaEventKind()
    data class State(val metaState: Int): MetaEventKind()
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
enum class Delete(val before: Int, val after: Int){
    BACKSPACE(1, 0),
    DELETE(0, 1)
}