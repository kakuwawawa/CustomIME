package com.kakuwawawa.customime

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView

//===== KeyboardData =====//
val NormalKeyboardData = listOf(
    listOf(KeyParts.TabKey, KeyParts.QKey, KeyParts.WKey, KeyParts.EKey, KeyParts.RKey, KeyParts.TKey, KeyParts.YKey, KeyParts.UKey, KeyParts.IKey, KeyParts.OKey, KeyParts.PKey, KeyParts.BSKey),
    listOf(KeyParts.AKey, KeyParts.SKey, KeyParts.DKey, KeyParts.FKey, KeyParts.GKey, KeyParts.HKey, KeyParts.JKey, KeyParts.KKey, KeyParts.LKey, KeyParts.N7Key, KeyParts.N8Key, KeyParts.N9Key),
    listOf(KeyParts.ShiftKey, KeyParts.ZKey, KeyParts.XKey, KeyParts.CKey, KeyParts.VKey, KeyParts.BKey, KeyParts.NKey, KeyParts.MKey, KeyParts.EnterKey, KeyParts.N4Key, KeyParts.N5Key, KeyParts.N6Key),
    listOf(KeyParts.LeftKey, KeyParts.UpKey, KeyParts.DownKey, KeyParts.RightKey, KeyParts.CtrlKey, KeyParts.AltKey, KeyParts.GoSymbolKey, KeyParts.SpaceKey, KeyParts.N0Key, KeyParts.N1Key, KeyParts.N2Key, KeyParts.N3Key)
)
val SymbolKeyboardData = listOf(
    listOf(KeyParts.ExclamationKey, KeyParts.DQuotationKey, KeyParts.HashKey, KeyParts.DollarKey, KeyParts.PercentKey, KeyParts.AndKey, KeyParts.SQuotationKey, KeyParts.OpenBracketKey, KeyParts.CloseBracketKey, KeyParts.EqualKey, KeyParts.MinusKey),
    listOf(KeyParts.CaretKey, KeyParts.TildeKey, KeyParts.BackSlashKey, KeyParts.PipeKey, KeyParts.AtKey, KeyParts.BacktickKey, KeyParts.OpenSBracketKey, KeyParts.OpenCBracketKey, KeyParts.SemicolonKey, KeyParts.PlusKey, KeyParts.ColonKey),
    listOf(KeyParts.AsteriskKey, KeyParts.CloseSBracketKey, KeyParts.CloseCBracketKey, KeyParts.CommaKey, KeyParts.OpenABracketKey, KeyParts.DotKey, KeyParts.CloseABracketKey, KeyParts.SlashKey, KeyParts.QuestionKey, KeyParts.UnderbarKey),
    listOf(KeyParts.LeftKey, KeyParts.RightKey, KeyParts.ShiftKey, KeyParts.CtrlKey, KeyParts.AltKey, KeyParts.GoNormalKey, KeyParts.SpaceKey, KeyParts.S1Sps, KeyParts.EnterKey)
)
val KeyboardLayout = listOf(
    NormalKeyboardData,
    SymbolKeyboardData
)

class ComposeKeyboardView(
    context: Context
) : AbstractComposeView(context) {
    var onKeyEvent: (KeyModel, State) -> Unit = { keyModel, state ->  }
    var keyboardState: State by mutableStateOf(State.DEFAULT)
    fun changeKeyboardState(state: State){
        keyboardState = state
    }

    var keyboardPage: Int by mutableIntStateOf(0)
    fun changeKeyboardPage(page: Int){
        keyboardPage = page
    }

    @Composable
    override fun Content() {
        // MockKeyboard()
        key(keyboardPage){
            KeyboardLayout(KeyboardLayout, keyboardPage, onKeyEvent, keyboardState)
        }
    }
}