package ru.lab.model

enum class Method(val method: String) {
    HALF_DIVISION("Half division"),
    NEWTON("Newton"),
    SIMPLE_ITERATIONS("Simple iterations");

    companion object {
        fun find(method: String) = values().find { it.method == method }
    }
}