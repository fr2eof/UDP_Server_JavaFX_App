package com.practice.GUI.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AbstractController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger("AbstractController");
    private Stage stage;
    protected final HashMap<String, Locale> langMap = new HashMap<>();
    @FXML
    protected ChoiceBox<String> langChoiceBox;
    protected Locale locale = Locale.US;
    protected ResourceBundle errors;
    protected ResourceBundle messages;

    public AbstractController() {
        initializeLangMap();
    }

    @Override
    public void initialize(URL loc, ResourceBundle resourceBundle) {
        this.errors = ResourceBundle.getBundle("errors", locale);
        this.messages = ResourceBundle.getBundle("messages", locale);
    }

    protected void initializeLangMap() {
        langMap.put("English", new Locale("en", "US"));
        langMap.put("Русский", new Locale("ru", "RU"));
        langMap.put("Беларуская", new Locale("be", "BY"));
        langMap.put("Dansk", new Locale("da", "DK"));
        langMap.put("Español", new Locale("es", "ES"));
    }

    protected void switchLocale(@NotNull Locale newLocale) {
        locale = newLocale;
        messages = ResourceBundle.getBundle("messages", locale);
        errors = ResourceBundle.getBundle("errors", locale);
        updateUI();
    }


    protected void updateUI() {

    }

    protected String getLangByLocale(Locale locale) {
        return langMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(locale))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("English");
    }

    protected void setLocaleAndResources(Locale locale, ResourceBundle messages, ResourceBundle errors) {
        this.locale = locale;
        this.messages = messages;
        this.errors = errors;
        updateUI();
    }

    protected void newStageOrganization(@NotNull FXMLLoader loader) throws IOException {
        loader.setResources(ResourceBundle.getBundle("messages", locale));
        Parent root = loader.load();

        AbstractController controller = loader.getController();
        controller.setLocaleAndResources(locale, messages, errors);

        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.setTitle("My Application Title");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
    }
}
