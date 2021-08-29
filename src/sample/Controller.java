package sample;

import Model.Language;
import Model.Translator;
import Model.translated.data.Root;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> drpFromCountry, drpToCountry;

    @FXML
    TextField inpFrom, inpTo;

    @FXML
    ImageView btnSwap;

    @FXML
    Label btnCopyFromText, btnCopyToText;

    Translator translator;
    List<Language> languages;
    String transaledText;

    int holdFromIndex;
    int holdToIndex;
    String holdFromText;
    String holdToText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translator = new Translator();
        transaledText = "";
        setupData();
    }

    private void setupData() {
        drpFromCountry.setDisable(true);
        drpToCountry.setDisable(true);
        inpFrom.setDisable(true);
        btnSwap.setDisable(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        languages = translator.getAllLang();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (languages != null) {
                                    for (Language lang : languages) {
                                        drpFromCountry.getItems().add(lang.getLanguage());
                                        drpToCountry.getItems().add(lang.getLanguage());
                                    }
                                    drpFromCountry.setDisable(false);
                                    drpToCountry.setDisable(false);
                                    inpFrom.setDisable(false);
                                    btnSwap.setDisable(false);
                                }
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }


    public void translate() {
        drpFromCountry.setDisable(true);
        drpToCountry.setDisable(true);
        inpFrom.setDisable(true);
        btnSwap.setDisable(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Root root = translator.translate(drpFromCountry.getSelectionModel().getSelectedItem(),
                                drpToCountry.getSelectionModel().getSelectedItem(), inpFrom.getText());
                        transaledText = root.data.translations.get(0).translatedText;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                inpTo.setText(transaledText);
                                drpFromCountry.setDisable(false);
                                drpToCountry.setDisable(false);
                                inpFrom.setDisable(false);
                                btnSwap.setDisable(false);
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    public void swap() {
        int index1 = drpFromCountry.getSelectionModel().getSelectedIndex();
        int index2 = drpToCountry.getSelectionModel().getSelectedIndex();
        System.out.println("From Index->" + index1 + "_____ To Index->" + index2);
        drpFromCountry.getSelectionModel().select(index2);
        drpToCountry.getSelectionModel().select(index1);
        inpFrom.setText(inpTo.getText());
        inpTo.setText("");
        translate();
    }

    public void copyText(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(btnCopyFromText)) {
            StringSelection stringSelection = new StringSelection(inpFrom.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else if (mouseEvent.getSource().equals(btnCopyToText)) {
            StringSelection stringSelection = new StringSelection(inpTo.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
}
