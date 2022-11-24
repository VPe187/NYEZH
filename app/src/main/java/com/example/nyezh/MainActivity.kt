package com.example.nyezh

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var isLetters  = false
    private var isUppercase = false
    private var isNumbers  = false
    private var isSpecials = false
    private var passwordLength = 10
    private var maxPoint = 100

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextPassword : EditText = this.findViewById(R.id.editTextPassword)
        val buttonGenerate : Button = this.findViewById(R.id.buttonGenerate)
        val textViewPasswordLength : TextView = this.findViewById(R.id.textViewPasswordLength)
        val switchLetters : Switch = this.findViewById(R.id.switchLetters)
        val switchUppercase : Switch = this.findViewById(R.id.switchUppercase)
        val switchNumbers : Switch = this.findViewById(R.id.switchNumbers)
        val switchSpecials : Switch = this.findViewById(R.id.switchSpecials)
        val seekBar : SeekBar = this.findViewById(R.id.seekBarPasswordLength)
        val progressBar : ProgressBar = this.findViewById(R.id.progressBarPoints)
        seekBar.progress = passwordLength
        var isTouched = false
        progressBar.max = maxPoint
        progressBar.min = 1
        progressBar.progress = 1
        editTextPassword.isEnabled = false
        buttonGenerate.isEnabled = false
        textViewPasswordLength.text = passwordLength.toString()
        switchLetters.run {
            setOnTouchListener(object : OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    isTouched = true
                    return false
                }
            })
            setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isTouched) {
                    isTouched = false
                    isLetters = isChecked
                    buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                }
            })
        }
        switchUppercase.run {
            setOnTouchListener(object : OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    isTouched = true
                    return false
                }
            })
            setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isTouched) {
                    isTouched = false
                    isUppercase = isChecked
                    buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                }
            })
        }
        switchNumbers.run {
            setOnTouchListener(object : OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    isTouched = true
                    return false
                }
            })
            setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isTouched) {
                    isTouched = false
                    isNumbers = isChecked
                    buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                }
            })
        }
        switchSpecials.run {
            setOnTouchListener(object : OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    isTouched = true
                    return false
                }
            })
            setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isTouched) {
                    isTouched = false
                    isSpecials = isChecked
                    buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                }
            })
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                passwordLength = i
                textViewPasswordLength.text = i.toString();
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        buttonGenerate.setOnClickListener {
            val password = Password()
            val generatedPassword = password.generate(passwordLength, isLetters, isUppercase, isNumbers, isSpecials)
            val passwordPoint = password.getStrength(generatedPassword).toInt()
            editTextPassword.setText(generatedPassword)
            val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("password", generatedPassword)
            clipboard.setPrimaryClip(clip)
            progressBar.progress = passwordPoint
            Toast.makeText(applicationContext, "Jelszó vágólapra másolva!", Toast.LENGTH_SHORT).show()
        }
    }
}