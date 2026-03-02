package com.kakuwawawa.customime

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
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
    private var keyboardState: State by mutableStateOf(State.DEFAULT)
    private var keyboardPage: Int by mutableIntStateOf(0)

    override fun onCreateInputView(): View {
        val view = ComposeKeyboardView(this)
        view.onKeyEvent = ::KeyboardClickEvent
        view.keyboardState = { this.keyboardState }
        view.keyboardPage = { keyboardPage }

        window?.window?.decorView?.let { decor ->
            decor.setViewTreeLifecycleOwner(this)
            decor.setViewTreeViewModelStoreOwner(this)
            decor.setViewTreeSavedStateRegistryOwner(this)
        }

        return view
    }

    // ----------------------
    // KeyboardMethod
    // ----------------------
    fun KeyboardClickEvent(keyModel: KeyModel, state: State){
        when(keyModel){
            is KeyModel.InputValue -> {
                if(state == State.SHIFT) OnInputValue(keyModel.shiftValue)
                else OnInputValue(keyModel.value)            }
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
                }catch(ex: Exception){
                    ic.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_DPAD_LEFT))
                }
            }
            CursorDirection.RIGHT -> {
                try{
                    ic.setSelection(extracted.selectionStart + 1, extracted.selectionEnd + 1)
                }catch (ex: Exception){
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
        this.keyboardState = state
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
        keyboardPage = page
    }
}