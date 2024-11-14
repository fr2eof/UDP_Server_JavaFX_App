package com.practice.GUI.controller;


import com.practice.backend.BackendClientApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends AbstractController implements Initializable {

    @FXML
    private TableView<?> tableView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button addButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button drawButton;

    @FXML
    private TextArea serverMessages;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xCoordField=null;

    @FXML
    private TextField yCoordField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField passportIdField;

    @FXML
    private TextField eyeColorField;

    @FXML
    private TextField hairColorField;

    @FXML
    private TextField xLocField;

    @FXML
    private TextField yLocField;

    @FXML
    private TextField nameLocField;

    @FXML
    private TextField commandInputField;

    @FXML
    private Label userNameLabel;

    public MainWindowController() {
        super();
    }

    @Override
    protected void updateUI() {
        if (serverMessages != null) {
            serverMessages.setText(messages.getString("server_messages"));
        }
        if (commandInputField != null) {
            commandInputField.setPromptText(messages.getString("command_input_field"));
        }
        if (deleteButton != null) {
            deleteButton.setText(messages.getString("delete_button"));
        }
        if (updateButton != null) {
            updateButton.setText(messages.getString("update_button"));
        }
        if (addButton != null) {
            addButton.setText(messages.getString("add_button"));
        }
        if (submitButton != null) {
            submitButton.setText(messages.getString("submit_button"));
        }
        if (drawButton != null) {
            drawButton.setText(messages.getString("draw_button"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(null);
        super.initialize(url, resourceBundle);
    }

    @FXML
    private void handleDeleteButton() {
        try {
            long id = Long.parseLong(idField.getText());
            if ((boolean) BackendClientApp.go(new Object[]{"remove_by_id", id})[0]) {
                serverMessages.appendText("\nRemoval by ID is successful");
            }else{
                serverMessages.appendText("\nRemoval by ID failed");
            }

        }catch (NumberFormatException e ) {//todo доделать
            }
    }

    @FXML
    private void handleUpdateButton() {
        try{
        int id = Integer.parseInt(idField.getText());
        Object name = nameField.getText();
        float xCoordFieldText = Float.parseFloat(xCoordField.getText());
        int yCoordFieldText = Integer.parseInt(yCoordField.getText());
        int salaryFieldText = Integer.parseInt(salaryField.getText());
        Object positionFieldText = positionField.getText().toUpperCase();
        Object statusFieldText = statusField.getText().toUpperCase();
        Object passportIdFieldText = passportIdField.getText();
        Object eyeColorFieldText = eyeColorField.getText().toUpperCase();
        Object hairColorFieldText = hairColorField.getText().toUpperCase();
        long xLocFieldText = Long.parseLong(xLocField.getText());
        long yLocFieldText = Long.parseLong(yLocField.getText());
        Object nameLocFieldText = nameLocField.getText();
        if ((boolean) BackendClientApp.go(new Object[]{"update", id, name , xCoordFieldText,
                yCoordFieldText, salaryFieldText,positionFieldText,statusFieldText, passportIdFieldText,
                eyeColorFieldText, hairColorFieldText, xLocFieldText, yLocFieldText ,nameLocFieldText})[0]) {
            serverMessages.appendText("\nUpdating is successful");
        }else{
            serverMessages.appendText("\nUpdating failed");
        }

    }catch (NumberFormatException e ) {//todo доделать
    }
    }

    @FXML
    private void handleAddButton() {
        try {
            Object name = nameField.getText();
            Object xCoordFieldText = Float.parseFloat(xCoordField.getText());
            Object yCoordFieldText = Integer.parseInt(yCoordField.getText());
            Object salaryFieldText = Integer.parseInt(salaryField.getText());
            Object positionFieldText = positionField.getText().toUpperCase();
            Object statusFieldText = statusField.getText().toUpperCase();
            Object passportIdFieldText = passportIdField.getText();
            Object eyeColorFieldText = eyeColorField.getText().toUpperCase();
            Object hairColorFieldText = hairColorField.getText().toUpperCase();
            Object xLocFieldText = Long.parseLong(xLocField.getText());
            Object yLocFieldText = Long.parseLong(yLocField.getText());
            Object nameLocFieldText = nameLocField.getText();
            if ((boolean) BackendClientApp.go(new Object[]{"add", name , xCoordFieldText,
                    yCoordFieldText, salaryFieldText,positionFieldText,statusFieldText, passportIdFieldText,
                    eyeColorFieldText, hairColorFieldText, xLocFieldText, yLocFieldText ,nameLocFieldText})[0]) {
                serverMessages.appendText("\nAdding is successful");
            }else{
                serverMessages.appendText("\nAdding failed");
            }

        }catch (NumberFormatException e ) {//todo доделать
        }
    }

    @FXML
    private void handleSubmitButton() {
        // Логика для отправки данных
        serverMessages.appendText("Submit button clicked\n");
    }

    @FXML
    private void handleDrawButton() {
        // Логика для отрисовки
        serverMessages.appendText("Draw button clicked\n");
    }
}
