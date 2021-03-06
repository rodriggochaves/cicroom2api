package com.cicroomapi.models

object Cypher {
  private val alphabet = ('A' to 'Z').toList ::: ('0' to '9').toList

  def encipher(key: String, text: String): String = {
    // Build the cipher alphabet by concatenating the key to
    // the alphabet and removing duplicate letters
    val cipherAlphabet = (key.toUpperCase.toList ::: alphabet).distinct

    println(alphabet)

    val cipherText = for (c <- text.toUpperCase) yield c match {
      case ' ' => ' '
      case _   => cipherAlphabet(alphabet.indexOf(c))
    }
    return cipherText.mkString
  }
}