package com.kakuwawawa.customime

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.getValue
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
    private var keyboardState: State by mutableStateOf(State.DEFAULT)

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

    override fun onCreateInputView(): View {
        val view = ComposeKeyboardView(this)
        view.onKeyEvent = ::KeyboardClickEvent
        view.keyboardState = { keyboardState }

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
    fun KeyboardClickEvent(keyModel: KeyModel){
        when(keyModel){
            is KeyModel.InputValue -> {
                OnInputValue(keyModel.value)
            }
            is KeyModel.CursorMove -> {
                OnCursorMove(keyModel.cursorDirection)
            }
            is KeyModel.StateChange -> {
                OnStateChange(keyModel.state)
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
                ic.setSelection(extracted.selectionStart - 1, extracted.selectionEnd - 1)
            }
            CursorDirection.RIGHT -> {
                ic.setSelection(extracted.selectionStart + 1, extracted.selectionEnd + 1)
            }
            CursorDirection.UP -> {
                /* 未実装(思ったより難しい)
                var offset = 0
                do{
                    offset++
                    val char = ic.getTextBeforeCursor(offset, 0)
                }while(char.isNullOrEmpty() || char == "\n")
                ic.setSelection(offset, offset) */
            }
            CursorDirection.DOWN -> {
                // 同上
            }
        }
    }
    fun OnStateChange(state: State){
        this.keyboardState = state
    }
}