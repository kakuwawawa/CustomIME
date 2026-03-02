package com.kakuwawawa.customime

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView

//===== KeyboardData =====//
val NormalKeyboardData = listOf(
    listOf(KeyParts.TabKey, KeyParts.QKey, KeyParts.WKey, KeyParts.EKey, KeyParts.RKey, KeyParts.TKey, KeyParts.YKey, KeyParts.UKey, KeyParts.IKey, KeyParts.OKey, KeyParts.PKey, KeyParts.BSKey),
    listOf(KeyParts.AKey, KeyParts.SKey, KeyParts.DKey, KeyParts.FKey, KeyParts.GKey, KeyParts.HKey, KeyParts.JKey, KeyParts.KKey, KeyParts.LKey, KeyParts.N7Key, KeyParts.N8Key, KeyParts.N9Key),
    listOf(KeyParts.ShiftKey, KeyParts.ZKey, KeyParts.XKey, KeyParts.CKey, KeyParts.VKey, KeyParts.BKey, KeyParts.NKey, KeyParts.MKey, KeyParts.EnterKey, KeyParts.N4Key, KeyParts.N5Key, KeyParts.N6Key),
    listOf(KeyParts.LeftKey, KeyParts.UpKey, KeyParts.DownKey, KeyParts.RightKey, KeyParts.CtrlKey, KeyParts.AltKey, KeyParts.GoSymbolKey, KeyParts.SpaceKey, KeyParts.N0Key, KeyParts.N1Key, KeyParts.N2Key, KeyParts.N3Key)
)
val SymbolKeyboardData = listOf(
    listOf(KeyParts.Exclamation_QuestionKey, KeyParts.DoubleQKey, KeyParts.Hash_PlusKey, KeyParts.Dollar_AtKey, KeyParts.Percent_AsteriskKey, KeyParts.And_PipeKey, KeyParts.SingleQKey, KeyParts.OpenBracket_BacktickKey, KeyParts.CloseBracket_CaretKey, KeyParts.Equal_MinusKey, KeyParts.Slash_BackSlashKey, KeyParts.Underbar_TildeKey,),
    listOf(KeyParts.Comma_OpenAngleBracketKey, KeyParts.Dot_CloseAngleBracketKey,KeyParts.OpenSquare_CurlyBracketKey, KeyParts.CloseSquare_CurlyBracketKey, KeyParts.Colon_SemicolonKey),
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
    var keyboardState: () -> MutableState<State> = { mutableStateOf(State.DEFAULT) }
    var keyboardPage: () -> Int = { 0 }

    @Composable
    override fun Content() {
        // MockKeyboard()
        KeyboardLayout(KeyboardLayout, keyboardPage(), onKeyEvent, keyboardState())
    }
}