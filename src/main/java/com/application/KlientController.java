package com.application;

import com.library.KlientDAO;
import com.library.Klient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;

public class KlientController {
    @FXML
    TableView<Klient> table;

    @FXML
    TableColumn<Klient, Integer> idColumn;
    @FXML
    TableColumn<Klient, String> imieColumn;
    @FXML
    TableColumn<Klient, String> nazwiskoColumn;
    @FXML
    TableColumn<Klient, String> peselColumn;


    @FXML
    TextField imieTextField;
    @FXML
    TextField nazwiskoTextField;
    @FXML
    TextField peselTextField;

    private ObservableList<Klient> klienci;


    public void initialize() {
        try {
            klienci = FXCollections.observableArrayList(KlientDAO.getInstance().all());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        table.setItems(klienci);

        imieColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nazwiskoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        peselColumn.setCellFactory(TextFieldTableCell.forTableColumn());


    }

    public void nazwiskoEdit(TableColumn.CellEditEvent<Klient, String> editEvent) {
        Klient k = editEvent.getTableView().getItems().get(editEvent.getTablePosition().getRow());
        k.setNazwisko(editEvent.getNewValue());
        try {
            k.save();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while trying to save changes\n\n" + e.getMessage());
        }
    }

    public void imieEdit(TableColumn.CellEditEvent<Klient, String> editEvent) {
        Klient k = editEvent.getTableView().getItems().get(editEvent.getTablePosition().getRow());
        k.setImie(editEvent.getNewValue());
        try {
            k.save();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while trying to save changes\n\n" + e.getMessage());
        }
    }

    public void peselEdit(TableColumn.CellEditEvent<Klient, String> editEvent) {
        Klient k = editEvent.getTableView().getItems().get(editEvent.getTablePosition().getRow());
        k.setPesel(editEvent.getNewValue());
        try {
            k.save();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while trying to save changes\n\n" + e.getMessage());
        }
    }

    public void addKlient() {
        try {
            Klient k = Klient.create(imieTextField.getText(), nazwiskoTextField.getText(), peselTextField.getText());
            klienci.add(k);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while trying to add klient\n\n" + e.getMessage());

            return;
        }

        imieTextField.setText("");
        nazwiskoTextField.setText("");
        peselTextField.setText("");
    }

    public void deleteKlient() {
        Klient k = table.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Na pewno chcesz usunac klienta?",
                ButtonType.YES, ButtonType.CANCEL
        );
        confirmation.showAndWait();
        if (confirmation.getResult() != ButtonType.YES) {
            return;
        }
        this.klienci.remove(k);
        try {
            Klient.delete(k);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(
                    Alert.AlertType.ERROR,
                    "Error occurred when tried to delete klient\n\n" + e.getMessage()
            ).show();
        }
    }

}
