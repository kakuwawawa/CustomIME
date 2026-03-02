package com.kakuwawawa.customime

typealias ki = KeyModel.InputValue
typealias kc = KeyModel.CursorMove
typealias ks = KeyModel.StateChange
typealias sp = KeyModel.Spacer
typealias kd = KeyModel.DeleteStr
typealias ke = KeyModel.Enter
typealias kp = KeyModel.PageChange

class KeyParts {
    companion object {
        // Char Input KeyButton //
        val AKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "a", shiftValue = "A")
        val BKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "b", shiftValue = "B")
        val CKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "c", shiftValue = "C")
        val DKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "d", shiftValue = "D")
        val EKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "e", shiftValue = "E")
        val FKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "f", shiftValue = "F")
        val GKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "g", shiftValue = "G")
        val HKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "h", shiftValue = "H")
        val IKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "i", shiftValue = "I")
        val JKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "j", shiftValue = "J")
        val KKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "k", shiftValue = "K")
        val LKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "l", shiftValue = "L")
        val MKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "m", shiftValue = "M")
        val NKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "n", shiftValue = "N")
        val OKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "o", shiftValue = "O")
        val PKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "p", shiftValue = "P")
        val QKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "q", shiftValue = "Q")
        val RKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "r", shiftValue = "R")
        val SKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "s", shiftValue = "S")
        val TKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "t", shiftValue = "T")
        val UKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "u", shiftValue = "U")
        val VKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "v", shiftValue = "V")
        val WKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "w", shiftValue = "W")
        val XKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "x", shiftValue = "X")
        val YKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "y", shiftValue = "Y")
        val ZKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "z", shiftValue = "Z")
        val N0Key: KeyModel.InputValue = ki(value = "0")
        val N1Key: KeyModel.InputValue = ki(value = "1")
        val N2Key: KeyModel.InputValue = ki(value = "2")
        val N3Key: KeyModel.InputValue = ki(value = "3")
        val N4Key: KeyModel.InputValue = ki(value = "4")
        val N5Key: KeyModel.InputValue = ki(value = "5")
        val N6Key: KeyModel.InputValue = ki(value = "6")
        val N7Key: KeyModel.InputValue = ki(value = "7")
        val N8Key: KeyModel.InputValue = ki(value = "8")
        val N9Key: KeyModel.InputValue = ki(value = "9")

        // Special Input Button //
        val TabKey: KeyModel.InputValue = ki.valueLabelThis(value = "\t", label = "↹") // u+21B9
        val SpaceKey: KeyModel.InputValue = ki.valueLabelThis(value = " ", label = " ")
        val Slash_BackSlashKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "/", shiftValue = "\\")
        val OpenSquare_CurlyBracketKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "[", shiftValue = "{")
        val CloseSquare_CurlyBracketKey: KeyModel.InputValue = ki.valueShiftValueThis("]", shiftValue = "}")
        val Exclamation_QuestionKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "!", shiftValue = "?")
        val DoubleQKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "\"", shiftValue = "\"\"")
        val Hash_PlusKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "#", shiftValue = "+")
        val Dollar_AtKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "$", shiftValue = "@")
        val Percent_AsteriskKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "%", shiftValue = "*")
        val And_PipeKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "&", shiftValue = "|")
        val SingleQKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "'", shiftValue = "''")
        val OpenBracket_BacktickKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "(", shiftValue = "`")
        val CloseBracket_CaretKey: KeyModel.InputValue = ki.valueShiftValueThis(value = ")", shiftValue = "^")
        val Equal_MinusKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "=", shiftValue = "-")
        val Colon_SemicolonKey: KeyModel.InputValue = ki.valueShiftValueThis(value = ":", shiftValue = ";")
        val Comma_OpenAngleBracketKey: KeyModel.InputValue = ki.valueShiftValueThis(value = ",", shiftValue = "<")
        val Dot_CloseAngleBracketKey: KeyModel.InputValue = ki.valueShiftValueThis(value = ".", shiftValue = ">")
        val Underbar_TildeKey: KeyModel.InputValue = ki.valueShiftValueThis(value = "_", shiftValue = "~")

        // Direction Button //
        val LeftKey: KeyModel.CursorMove = kc(CursorDirection.LEFT, "←")
        val RightKey: KeyModel.CursorMove = kc(CursorDirection.RIGHT, "→")
        val UpKey: KeyModel.CursorMove = kc(CursorDirection.UP, "↑")
        val DownKey: KeyModel.CursorMove = kc(CursorDirection.DOWN, "↓")

        // State Change Button //
        val ShiftKey: KeyModel.StateChange = ks(State.SHIFT, "⇪") // u+21EA
        val CtrlKey: KeyModel.StateChange = ks(State.CTRL, "ctrl")
        val AltKey: KeyModel.StateChange = ks(State.ALT, "alt")
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