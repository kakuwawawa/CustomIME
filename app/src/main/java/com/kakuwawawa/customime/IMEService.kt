package com.kakuwawawa.customime

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class IMEService : InputMethodService(),
    LifecycleOwner,
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    // ----------------------
    // Lifecycle
    // ----------------------

    private val lifecycleRegistry = LifecycleRegistry(this)
    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    // ----------------------
    // ViewModel
    // ----------------------

    private val viewModelStoreInternal = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = viewModelStoreInternal

    // ----------------------
    // SavedState
    // ----------------------

    private val savedStateRegistryController =
        SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    // ----------------------
    // Lifecycle mapping
    // ----------------------

    override fun onCreate() {
        super.onCreate()

        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onBindInput() {
        super.onBindInput()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onUnbindInput() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onUnbindInput()
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewModelStoreInternal.clear()
        super.onDestroy()
    }

    // ----------------------
    // Compose View
    // ----------------------
    lateinit var composeView: ComposeKeyboardView

    override fun onCreateInputView(): View {
        val view = ComposeKeyboardView(this)
        view.onKeyEvent = ::KeyboardClickEvent

        window?.window?.decorView?.let { decor ->
            decor.setViewTreeLifecycleOwner(this)
            decor.setViewTreeViewModelStoreOwner(this)
            decor.setViewTreeSavedStateRegistryOwner(this)
        }
        composeView = view

        return view
    }

    // ----------------------
    // KeyboardMethod
    // ----------------------
    fun KeyboardClickEvent(keyModel: KeyModel, state: State){
        when(keyModel){
            is KeyModel.InputValue -> {
                when(state){
                    State.SHIFT -> OnInputValue(keyModel.shiftValue)
                    State.DEFAULT -> OnInputValue(keyModel.value)
                    State.ALT -> {
                        OnInputValue(keyModel.value)
                    }
                    State.CTRL -> {
                        OnInputValue(keyModel.value)
                    }
                    else -> { }
                }
            }
            is KeyModel.CursorMove -> {
                OnCursorMove(keyModel.cursorDirection)
            }
            is KeyModel.StateChange -> {
                if(state == keyModel.state) OnStateChange(State.DEFAULT)
                else OnStateChange(keyModel.state)
            }
            is KeyModel.DeleteStr -> {
                OnDelete(keyModel.delete)
            }
            is KeyModel.Enter -> {
                OnEnter()
            }
            is KeyModel.PageChange -> {
                OnPageChange(keyModel.page)
            }
            is KeyModel.CodeInput -> {
                when(keyModel.metaEventKind){
                    MetaEventKind.Inheart -> {
                        when(state){
                            State.DEFAULT -> OnCodeInput(keyModel.code)
                            State.SHIFT -> OnMetaCodeInput(keyModel.code, KeyEvent.META_SHIFT_ON)
                            State.CTRL -> OnMetaCodeInput(keyModel.code, KeyEvent.META_CTRL_ON)
                            State.ALT -> OnMetaCodeInput(keyModel.code, KeyEvent.META_ALT_ON)
                            else -> { }
                        }
                    }
                    MetaEventKind.Default -> {
                        OnCodeInput(keyModel.code)
                    }
                    is MetaEventKind.State -> {
                        OnMetaCodeInput(keyModel.code, keyModel.metaEventKind.metaState)
                    }
                }
            }
            else -> { }
        }
    }
    fun OnInputValue(value: String){
        currentInputConnection?.commitText(value, value.length)
    }
    fun OnCursorMove(cursorDirection: CursorDirection){
        val ic = currentInputConnection ?: return
        val extracted = ic.getExtractedText(ExtractedTextRequest(),0)
        when(cursorDirection){
            CursorDirection.LEFT -> {
                try {
                    ic.setSelection(extracted.selectionStart - 1, extracted.selectionEnd - 1)
                }catch(_: Exception){
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT))
                }
            }
            CursorDirection.RIGHT -> {
                try{
                    ic.setSelection(extracted.selectionStart + 1, extracted.selectionEnd + 1)
                }catch (_: Exception){
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT))
                }
            }
            CursorDirection.UP -> {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP))
            }
            CursorDirection.DOWN -> {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN))
            }
        }
    }
    fun OnStateChange(state: State){
        composeView.changeKeyboardState(state)
    }
    fun OnDelete(delete: Delete){
        val ic = currentInputConnection ?: return
        // 選択範囲があるなら、まずそれを削除（置換）
        val selected = ic.getSelectedText(0)
        if (!selected.isNullOrEmpty()) {
            ic.commitText("", 1)
            return
        }
        // 通常削除
        ic.deleteSurroundingText(delete.before, delete.after)
    }
    fun OnEnter(){
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
    }
    fun OnPageChange(page: Int){
        composeView.changeKeyboardPage(page)
    }
    fun OnCodeInput(code: Int){
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, code))
    }
    fun OnMetaCodeInput(code: Int, metaState: Int){
        val ic = currentInputConnection ?: return
        ic.sendKeyEvent(
            KeyEvent(0,0,KeyEvent.ACTION_DOWN,code,0,metaState)
        )
        ic.sendKeyEvent(
            KeyEvent(0,0,KeyEvent.ACTION_UP,code,0,metaState)
        )
    }
}