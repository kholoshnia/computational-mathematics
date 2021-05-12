package ru.lab.model

enum class Variant(val variant: String) {
    TABLE("Table"),
    FUNCTION("Function");

    companion object {
        fun find(variant: String) = values().find { it.variant == variant }
    }
}