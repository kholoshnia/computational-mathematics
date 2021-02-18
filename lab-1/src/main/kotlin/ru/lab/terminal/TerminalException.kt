package ru.lab.terminal

class TerminalException(message: String) : Exception(message) {
    constructor(e: Exception) : this(e.localizedMessage)
}