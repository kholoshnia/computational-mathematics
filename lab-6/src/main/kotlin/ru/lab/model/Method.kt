package ru.lab.model

enum class Method(val method: String) {
    EULER("Euler"),
    ADAMS("Adams");

    companion object {
        fun find(method: String) = values().find { it.method == method }
    }
}