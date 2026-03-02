package com.kakuwawawa.customime

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

//===== KeyboardLayout =====//
@Composable
fun KeyboardLayout(keyboardLayout: List<List<List<KeyModel>>>,
                   index: Int,
                   onClick: (KeyModel, State) -> Unit,
                   keyBoardState: MutableState<State>)
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
private val KeyButtonModifier = Modifier
    .background(Color.LightGray)
    .border(width = 1.dp, color = Color.White)
    .width(32.dp)
    .height(40.dp)
    .padding(0.dp)
@Composable
fun KeyButton(
    keyModel: KeyModel,
    keyBoardState: MutableState<State>,
    onClick: (KeyModel, State) -> Unit,
    modifier: Modifier
){
    Box(modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures (onPress = {
                onClick(keyModel, keyBoardState.value)
                delay(500)
                coroutineScope {
                    val job =  launch {
                        while(isActive){
                            onClick(keyModel, keyBoardState.value)
                            delay(50)
                        }
                    }
                tryAwaitRelease()
                job.cancel()
                }
            })
        }) {
        Text(
            keyModel.nowLabel(keyBoardState.value), modifier = Modifier.padding(0.dp).align(Alignment.Center),
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
    data class StateChange(val state: State, val label: String): KeyModel(){
        override fun nowLabel(state: State): String = label
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