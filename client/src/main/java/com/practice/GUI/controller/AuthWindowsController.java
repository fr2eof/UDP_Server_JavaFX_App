package com.practice.GUI.controller;

import com.practice.backend.BackendClientApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

@Getter
@Setter
public class AuthWindowsController extends AbstractController implements Initializable {
    @FXML
    private Label reg_alternative_label;
    @FXML
    private Label log_alternative_label;
    @FXML
    private VBox vBox;
    @FXML
    private Button regButton;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private Label mainLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Label resulLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<String> langChoiceBox;
    private Stage stage;


    private static final Logger log = LoggerFactory.getLogger("AuthWindowsController");

    public AuthWindowsController() {
        super();
        this.langChoiceBox = super.langChoiceBox;
    }

    @Override
    protected void updateUI() {
        if (mainLabel != null) {
            mainLabel.setText(messages.getString("auth_main_label"));
        }
        if (usernameField != null) {
            usernameField.setPromptText(messages.getString("username"));
        }
        if (passwordField1 != null) {
            passwordField1.setPromptText(messages.getString("password"));
        }
        if (passwordField2 != null) {
            passwordField2.setPromptText(messages.getString("repeat_password"));
        }
        if (loginButton != null) {
            loginButton.setText(messages.getString("login_button"));
        }
        if (regButton != null) {
            regButton.setText(messages.getString("reg_button"));
        }
        if (log_alternative_label != null) {
            log_alternative_label.setText(messages.getString("log_alternative_label"));
        }
        if (reg_alternative_label != null) {
            reg_alternative_label.setText(messages.getString("reg_alternative_label"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        langChoiceBox.getItems().addAll(langMap.keySet());
        langChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Locale selectedLocale = langMap.get(newValue);
            if (selectedLocale != null) {
                switchLocale(selectedLocale);
            }
        });
        if (langChoiceBox != null) {
            Optional.ofNullable(getLangByLocale(locale)).ifPresent(langChoiceBox::setValue);
        }
    }

    @FXML
    private void underlineRegisterLabel() {
        if (log_alternative_label != null) {
            log_alternative_label.setStyle("-fx-underline: true");
        }
        if (reg_alternative_label != null) {
            reg_alternative_label.setStyle("-fx-underline: true");
        }
    }

    @FXML
    private void undoUnderlineRegisterLabel() {
        if (log_alternative_label != null) {
            log_alternative_label.setStyle("-fx-underline: false");
        }
        if (reg_alternative_label != null) {
            reg_alternative_label.setStyle("-fx-underline: false");
        }
    }

    @FXML
    private void loginMethod() {
        String username = usernameField.getText();
        String password = passwordField1.getText();
        try {
            if (!Objects.equals(username, "") && !Objects.equals(password, "")) {
                if ((boolean) BackendClientApp.go(new Object[]{"login", username, password})[0]) {
                    errorLabel.setText("");
                    errorLabel.setVisible(false);
                    auth_passed();
                } else {
                    errorLabel.setText(errors.getString("loginError"));
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText(errors.getString("emptyField"));
                errorLabel.setVisible(true);
            }
        } catch (IOException ex) {
            log.error("Main Window opening failed " + ex.getMessage());
            errorLabel.setText(errors.getString("systemError"));
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void regMethod() {
        String username = usernameField.getText();
        String password = passwordField1.getText();
        String repeatedPassword = passwordField2.getText();
        try {
            if (!Objects.equals(username, "") && !Objects.equals(password, "") && !Objects.equals(repeatedPassword, "")) {
                if (Objects.equals(password, repeatedPassword)) {
                    if ((boolean) BackendClientApp.go(new Object[]{"registration", username, password})[0]) {
                        errorLabel.setText("");
                        errorLabel.setVisible(false);
                        auth_passed();
                    } else {
                        errorLabel.setText(errors.getString("RegisterError"));
                        errorLabel.setVisible(true);
                    }
                } else {
                    errorLabel.setText(errors.getString("mismatchPassword"));
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText(errors.getString("emptyField"));
                errorLabel.setVisible(true);
            }
        } catch (IOException e) {
            log.error("Main Window opening failed " + e.getMessage());
            errorLabel.setText(errors.getString("systemError"));
            errorLabel.setVisible(true);
        }
    }

    private void auth_passed() throws IOException {
        Stage currentStage = (Stage) vBox.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        newStageOrganization(loader);
    }

    @FXML
    private void showRegisterWindow() throws IOException {
        Stage currentStage = (Stage) vBox.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/regWindow.fxml"));
        newStageOrganization(loader);
    }

    @FXML
    private void showLoginWindow() throws IOException {
        Stage currentStage = (Stage) vBox.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginWindow.fxml"));
        newStageOrganization(loader);
    }

    @FXML
    private void langChoice() {
        switchLocale(langMap.get(langChoiceBox.getValue()));
    }


}