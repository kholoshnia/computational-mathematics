package ru.lab.terminal.file

import java.io.File

/**
 * File manager class provides methods for
 * working with files.
 */
class FileManager {
    private val supportedFormats: List<String> = listOf("txt", "md", "csv")

    /**
     * Opens file and checks for errors.
     * @param path file path
     * @return file
     */
    fun openFile(path: String): File {
        val file = File(path)

        if (!file.exists()) throw FileManagerException("$path does not exist")
        if (!file.isFile) throw FileManagerException("$path is not a file")
        if (!supportedFormats.contains(file.extension)) throw FileManagerException("${file.extension} is not supported")
        if (!file.canRead()) throw FileManagerException("Cannot read from file $path (check permissions)")
        if (!file.canWrite()) throw FileManagerException("Cannot write to file $path (check permissions)")

        return file
    }
}