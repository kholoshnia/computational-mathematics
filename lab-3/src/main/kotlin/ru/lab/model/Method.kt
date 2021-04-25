package ru.lab.model

enum class Method(val method: String) {
    RECTANGLE("Rectangle"),
    TRAPEZOIDS("Trapezoids"),
    SIMPSONS("Simpson's");

    companion object {
        fun find(method: String) = values().find { it.method == method }
    }
}