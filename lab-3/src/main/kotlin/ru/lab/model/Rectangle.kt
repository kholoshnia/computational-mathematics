package ru.lab.model

enum class Rectangle(val method: String) {
    LEFT("Left"),
    RIGHT("Right"),
    MIDDLE("Middle");

    companion object {
        fun find(method: String) = values().find { it.method == method }
    }
}