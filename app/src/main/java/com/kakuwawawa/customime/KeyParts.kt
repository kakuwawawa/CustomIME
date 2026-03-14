package com.kakuwawawa.customime

import android.view.KeyEvent

typealias ki = KeyModel.InputValue
typealias kc = KeyModel.CursorMove
typealias ks = KeyModel.StateChange
typealias sp = KeyModel.Spacer
typealias kd = KeyModel.DeleteStr
typealias ke = KeyModel.Enter
typealias kp = KeyModel.PageChange
typealias kco = KeyModel.CodeInput

class KeyParts {
    companion object {
        // Char Input KeyButton //
        val AKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_A, "a", "A")
        val BKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_B, "b", "B")
        val CKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_C, "c", "C")
        val DKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_D, "d", "D")
        val EKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_E, "e", "E")
        val FKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_F, "f", "F")
        val GKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_G, "g", "G")
        val HKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_H, "h", "H")
        val IKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_I, "i", "I")
        val JKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_J, "j", "J")
        val KKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_K, "k", "K")
        val LKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_L, "l", "L")
        val MKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_M, "m", "M")
        val NKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_N, "n", "N")
        val OKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_O, "o", "O")
        val PKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_P, "p", "P")
        val QKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_Q, "q", "Q")
        val RKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_R, "r", "R")
        val SKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_S, "s", "S")
        val TKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_T, "t", "T")
        val UKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_U, "u", "U")
        val VKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_V, "v", "V")
        val WKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_W, "w", "W")
        val XKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_X, "x", "X")
        val YKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_Y, "y", "Y")
        val ZKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_Z, "z", "Z")

        val N0Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_0, "0", MetaEventKind.Default)
        val N1Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_1, "1", MetaEventKind.Default)
        val N2Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_2, "2", MetaEventKind.Default)
        val N3Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_3, "3", MetaEventKind.Default)
        val N4Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_4, "4", MetaEventKind.Default)
        val N5Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_5, "5", MetaEventKind.Default)
        val N6Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_6, "6", MetaEventKind.Default)
        val N7Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_7, "7", MetaEventKind.Default)
        val N8Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_8, "8", MetaEventKind.Default)
        val N9Key: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_9, "9", MetaEventKind.Default)

        // Special Input Button //
        val SpaceKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_SPACE, " ")
        val TabKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_TAB, "↹") // u+21B9
        val  ExclamationKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_1, "!", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  DQuotationKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_APOSTROPHE, "\"", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  HashKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_3, "#", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  DollarKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_4, "$", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  PercentKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_5, "%", MetaEventKind.State( KeyEvent.META_SHIFT_ON))
        val  AndKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_7, "&", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  SQuotationKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_APOSTROPHE, "'", MetaEventKind.Default)
        val  OpenBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_9, "(", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val CloseBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_0, ")", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  EqualKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_EQUALS, "=", MetaEventKind.Default)
        val  MinusKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_MINUS, "-", MetaEventKind.Default)
        val  CaretKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_6, "^", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  TildeKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_GRAVE, "~", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  BackSlashKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_BACKSLASH, "\\", MetaEventKind.Default)
        val  PipeKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_BACKSLASH, "|", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  AtKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_2, "@", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  BacktickKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_GRAVE, "`", MetaEventKind.Default)
        val  OpenSBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_LEFT_BRACKET, "[", MetaEventKind.Default)
        val  OpenCBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_LEFT_BRACKET, "{", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  SemicolonKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_SEMICOLON, ";", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  PlusKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_PLUS, "+", MetaEventKind.Default)
        val  ColonKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_SEMICOLON, ":", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  AsteriskKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_STAR, "*", MetaEventKind.Default)
        val  CloseSBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_RIGHT_BRACKET, "]", MetaEventKind.Default)
        val  CloseCBracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_RIGHT_BRACKET, "}", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  CommaKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_COMMA, ",", MetaEventKind.Default)
        val  OpenABracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_COMMA, "<", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  DotKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_PERIOD, ".", MetaEventKind.Default)
        val  CloseABracketKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_PERIOD, ">", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  SlashKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_SLASH, "/", MetaEventKind.Default)
        val  QuestionKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_SLASH, "?", MetaEventKind.State(KeyEvent.META_SHIFT_ON))
        val  UnderbarKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_MINUS, "_", MetaEventKind.State(KeyEvent.META_SHIFT_ON))


        // Direction Button //
        val LeftKey: KeyModel.CodeInput = kco(KeyEvent.KEYCODE_DPAD_LEFT, "←")
        val RightKey: KeyModel.CursorMove = kc(CursorDirection.RIGHT, "→")
        val UpKey: KeyModel.CursorMove = kc(CursorDirection.UP, "↑")
        val DownKey: KeyModel.CursorMove = kc(CursorDirection.DOWN, "↓")

        // State Change Button //
        val ShiftKey: KeyModel.StateChange = ks(State.SHIFT, "⇪") // u+21EA
        val CtrlKey: KeyModel.StateChange = ks(State.CTRL, "ctrl", "onCtrl")
        val AltKey: KeyModel.StateChange = ks(State.ALT, "alt", "onAlt")
        // Spacer //
        val S1Sps: KeyModel.Spacer = sp(1f)
        // Delete Button //
        val BSKey: KeyModel.DeleteStr = kd(Delete.BACKSPACE, "⌫") // u+232b
        val DelKey: KeyModel.DeleteStr = kd(Delete.DELETE, "del")
        // Enter Button //
        val EnterKey: KeyModel.Enter = ke()
        // Page Select Button //
        val GoSymbolKey: KeyModel.PageChange = kp(1, "記号")
        val GoNormalKey: KeyModel.PageChange = kp(0, "戻る")
    }
}