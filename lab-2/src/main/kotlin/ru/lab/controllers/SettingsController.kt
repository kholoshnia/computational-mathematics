package ru.lab.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javafx.stage.FileChooser
import ru.lab.model.Results
import ru.lab.model.Settings
import tornadofx.Controller

class SettingsController : Controller() {
    private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    private val fileChooser = FileChooser()

    init {
        fileChooser.extensionFilters.add(
            FileChooser.ExtensionFilter("YAML settings (*.yml, *.yaml)", "*.yml", "*.yaml")
        )
    }

    fun importSettings(): Settings? {
        val file = fileChooser.showOpenDialog(primaryStage) ?: return null
        return mapper.readValue(file)
    }

    fun exportSettings(results: Results) {
        val file = fileChooser.showSaveDialog(primaryStage)
        mapper.writeValue(file, results)
    }
}