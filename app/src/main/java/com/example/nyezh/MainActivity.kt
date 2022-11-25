package com.example.nyezh

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isLetters  = false
    private var isUppercase = false
    private var isNumbers  = false
    private var isSpecials = false
    private var passwordLength = 10
    private var maxPoint = 100

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var buttonGenerate : Button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchLetters : Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchUppercase : Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchNumbers : Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchSpecials : Switch
    private lateinit var textViewPasswordLength : TextView
    private lateinit var editTextPassword : EditText
    private lateinit var seekBar : SeekBar
    private lateinit var progressBar : ProgressBar
    private lateinit var menu : Menu

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val splashIntent = Intent(this@MainActivity, Splash::class.java)
        startActivity(splashIntent)

        this.editTextPassword = this.findViewById(R.id.editTextPassword)
        this.buttonGenerate = this.findViewById(R.id.buttonGenerate)
        this.textViewPasswordLength = this.findViewById(R.id.textViewPasswordLength)
        this.switchLetters = this.findViewById(R.id.switchLetters)
        this.switchUppercase = this.findViewById(R.id.switchUppercase)
        this.switchNumbers = this.findViewById(R.id.switchNumbers)
        this.switchSpecials = this.findViewById(R.id.switchSpecials)
        this.seekBar = this.findViewById(R.id.seekBarPasswordLength)
        this.progressBar = this.findViewById(R.id.progressBarPoints)
        this.seekBar.progress = passwordLength
        this.progressBar.max = maxPoint
        this.progressBar.min = 1
        this.progressBar.progress = 1
        this.editTextPassword.isEnabled = false
        this.buttonGenerate.isEnabled = false
        this.textViewPasswordLength.text = passwordLength.toString()
        var isTouched = false

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
                textViewPasswordLength.text = i.toString()
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
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }

    private fun setLengthFalse() {
        menu.findItem(R.id.length_6).isChecked = false
        menu.findItem(R.id.length_8).isChecked = false
        menu.findItem(R.id.length_10).isChecked = false
        menu.findItem(R.id.length_14).isChecked = false
        menu.findItem(R.id.length_18).isChecked = false
        menu.findItem(R.id.length_20).isChecked = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        this.menu = menu
        this.menu.findItem(R.id.length_10).isChecked = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.menu.findItem(R.id.character_letters).isChecked = isLetters
        this.menu.findItem(R.id.character_uppercase).isChecked = isUppercase
        this.menu.findItem(R.id.character_numbers).isChecked = isNumbers
        this.menu.findItem(R.id.character_specials).isChecked = isSpecials
        return when (item.itemId) {

            R.id.character_letters -> {
                item.isChecked = ! item.isChecked
                this.switchLetters.isChecked = item.isChecked
                this.isLetters = item.isChecked
                this.buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                true
            }

            R.id.character_uppercase -> {
                item.isChecked = ! item.isChecked
                this.switchUppercase.isChecked = item.isChecked
                this.isUppercase = item.isChecked
                this.buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                true
            }

            R.id.character_numbers -> {
                item.isChecked = ! item.isChecked
                this.switchNumbers.isChecked = item.isChecked
                this.isNumbers = item.isChecked
                this.buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                true
            }

            R.id.character_specials -> {
                item.isChecked = ! item.isChecked
                this.switchSpecials.isChecked = item.isChecked
                this.isSpecials = item.isChecked
                this.buttonGenerate.isEnabled = isLetters || isUppercase || isNumbers || isSpecials
                true
            }

            R.id.length_6 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 6
                this.textViewPasswordLength.text = "6"
                true
            }

            R.id.length_8 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 8
                this.textViewPasswordLength.text = "8"
                true
            }

            R.id.length_10 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 10
                this.textViewPasswordLength.text = "10"
                true
            }

            R.id.length_14 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 14
                this.textViewPasswordLength.text = "14"
                true
            }

            R.id.length_18 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 18
                this.textViewPasswordLength.text = "18"
                true
            }

            R.id.length_20 -> {
                this.setLengthFalse()
                item.isChecked = !item.isChecked
                this.seekBar.progress = 20
                this.textViewPasswordLength.text = "20"
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}