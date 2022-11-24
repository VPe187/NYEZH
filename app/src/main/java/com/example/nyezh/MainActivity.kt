package com.example.nyezh

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editTextPassword : EditText = findViewById(R.id.editTextPassword)
        var buttonGenerate : Button = findViewById(R.id.buttonGenerate)

        var switchLetters : Switch = findViewById(R.id.switchLetters)
        var switchUppercase : Switch = findViewById(R.id.switchUppercase)
        var switchNumbers : Switch = findViewById(R.id.switchLetters)
        var switchSpecials : Switch = findViewById(R.id.switchLetters)

        editTextPassword.isEnabled = false;

        buttonGenerate.setOnClickListener {
            val password = Password()
            val generatedPassword = password.generate(20, isLetters = true, isUppercase = true, isNumbers = true, isSpecial = true)
            editTextPassword.setText(generatedPassword)
            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("password", generatedPassword)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext, "Jelszó vágólapra másolva!", Toast.LENGTH_SHORT).show()

        }
    }
}