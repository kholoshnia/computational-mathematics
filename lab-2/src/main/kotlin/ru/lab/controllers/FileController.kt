package ru.lab.controllers

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javafx.scene.control.Alert
import javafx.stage.FileChooser
import ru.lab.model.Results
import ru.lab.model.Settings
import tornadofx.Controller
import tornadofx.alert

class FileController : Controller() {
    private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    private val fileChooser = FileChooser()

    fun importSettings(): Settings? {
        val file = fileChooser.showOpenDialog(primaryStage)
            ?: return null

        return try {
            mapper.readValue(file)
        } catch (e: JacksonException) {
            alert(Alert.AlertType.WARNING, "Import error", e.message)
            null
        }
    }

    fun exportResults(results: Results) {
        val file = fileChooser.showSaveDialog(primaryStage)

        try {
            mapper.writeValue(file, results)
        } catch (e: JacksonException) {
            alert(Alert.AlertType.WARNING, "Export error", e.message)
        }
    }
}