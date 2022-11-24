package com.example.nyezh

import java.security.SecureRandom

class Password {
    private val lettersPoints : Float = 2F
    private val upperPoints : Float = 2F
    private val numberPoints : Float = 1F
    private val specialPoints : Float = 5F
    private val maxLength : Float = 20F
    private val maxPoints : Float = lettersPoints + upperPoints + numberPoints + specialPoints
    private val letters : String = "abcdefghijklmnopqrstuvwxyz"
    private val uppercaseLetters : String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val numbers : String = "1234567890"
    //private val special : String = "[]ß#&@Ł#$=%()<>-!?."
    private val special : String = "ß#&@Ł#$=%<>!?."

    fun generate(length : Int, isLetters : Boolean, isUppercase : Boolean, isNumbers : Boolean, isSpecial : Boolean) : String {
        var result = ""
        var i = 0
        if(isLetters){ result += this.letters }
        if(isUppercase){ result += this.uppercaseLetters }
        if(isNumbers){ result += this.numbers }
        if(isSpecial){ result += this.special }
        val rnd = SecureRandom.getInstance("SHA1PRNG")
        val sb = StringBuilder(length)
        while (i < length) {
            val randomInt : Int = rnd.nextInt(result.length)
            sb.append(result[randomInt])
            i++
        }
        return sb.toString()
    }

    fun getStrength(password : String) : Float {
        var factor = 0F
        val length = password.length
        if( password.matches( Regex(".*["+this.letters+"].*") ) ) {
            factor += lettersPoints
        }
        if( password.matches( Regex(".*["+this.uppercaseLetters+"].*") ) ){
            factor += upperPoints
        }
        if( password.matches( Regex(".*["+this.numbers+"].*") ) ){
            factor += numberPoints
        }
        if( password.matches( Regex(".*["+this.special+"].*") ) ){
            factor += specialPoints
        }
        return ((factor*length)/(this.maxPoints*this.maxLength))*100
    }

}