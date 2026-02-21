package com.kakuwawawa.customime

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ライフサイクルの基底処理
        super.onCreate(savedInstanceState)
        // 画面描画
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                Column {
                    ActivityScreen()
                    Spacer(modifier = Modifier.weight(1f)
                    )
                }
            }
            isSystemInDarkTheme()
        }
    }
}

@Composable
fun ActivityScreen(){
    Column(Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
        // 親contextを取得
        val ctx = LocalContext.current
        Text(text = "Custom Keyboard")
        val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try Here")) }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            ctx.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }) {
            Text(text = "Enable IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = text, onValueChange = setValue, modifier = Modifier.fillMaxWidth())
    }
}