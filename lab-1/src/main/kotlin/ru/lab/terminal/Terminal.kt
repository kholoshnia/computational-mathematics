package ru.lab.terminal

import ru.lab.calculator.Result

/**
 * Terminal interface provides methods for reading SLAE
 * and writing result of the calculations.
 */
interface Terminal {
    /**
     * Reads SLAE from terminal or file.
     * @return read SLAE
     */
    fun readSlae(): List<List<Double>>

    /**
     * Writes result of the calculations.
     * @param result result of the calculations
     */
    fun writeResult(result: Result)
}