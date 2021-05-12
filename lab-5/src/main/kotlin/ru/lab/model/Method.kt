package ru.lab.model

enum class Method(val method: String) {
    LAGRANGE("Lagrange"),
    NEWTON("Newton");

    companion object {
        fun find(method: String) = values().find { it.method == method }
    }
}