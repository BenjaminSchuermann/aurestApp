package aurestApp.controller;

import aurestApp.Model;
import aurestApp.tools.eigeneklassen.Email;
import aurestApp.tools.eigeneklassen.MessageConsumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class eMailProgress implements Initializable {
    @FXML
    public Button closer;
    @FXML
    public ProgressBar progess;
    @FXML
    public TextArea ta_Statusbereich;
    @FXML
    public VBox vbox;
    @FXML
    public Label lbl_bearbeite;
    private File selectedDirectory;
    private Model m;
    private File[] files;
    private int dateianzahl = 0;
    private ListView listView;

    public eMailProgress(File selectedDirectory, ListView listView, Model m) {
        this.selectedDirectory = selectedDirectory;
        this.listView = listView;
        this.m = m;
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closer.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Email email = new Email(m);

        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

        files = selectedDirectory.listFiles();

        if (files != null) {
            //Anzahl der Dateien ermitteln
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".msg")) {
                    dateianzahl++;
                }
            }

            Task<Object> worker = new Task<Object>() {
                @Override
                protected Object call() throws Exception {

                    if (files != null) {
                        //Doppelte Anzahl festlegen damit die Progressbar richtig läuft (+1 für den Status)
                        final int numMessages = dateianzahl * 2 + 1;
                        //Den Consumer starten
                        Platform.runLater(() -> new MessageConsumer(messageQueue, ta_Statusbereich, numMessages).start());

                        int i = 1;
                        messageQueue.put("eMails werden mit einem neutralen Namen unbennant ... bitte warten...");
                        //Dateien erstmal alle neutral umbennen
                        for (File file : files) {
                            if (file.isFile() && file.getName().endsWith(".msg")) {
                                updateProgress(i, numMessages);
                                boolean out = file.renameTo(new File(file.getParent() + "//" + RandomStringUtils.randomAlphanumeric(10) + i + ".msg"));
                                i++;
                            }
                        }
                        messageQueue.put("eMails werden verarbeitet ...");
                        //dann neu auflisten und nach eMail Betreff umbennen
                        files = selectedDirectory.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                if (file.isFile() && file.getName().endsWith(".msg")) {
                                    messageQueue.put("eMail Nr. " + Integer.toString(i - dateianzahl) + " in Bearbeitung");
                                    //System.out.println(i.toString() + ": " + file.getName());
                                    email.renameMail(file);
                                    updateProgress(i, numMessages);
                                    i++;
                                }
                            }
                        }
                        updateProgress(numMessages, numMessages);
                    }
                    messageQueue.put(email.getStatus());
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();

                    Notifications.create().darkStyle()
                            .title("eMail umbennen")
                            .text("eMails wurden umbennant")
                            .showInformation();

                }
            };

            worker.setOnSucceeded(event -> {
                closer.setDisable(false);
                //Wenn eMails vorhanden sind, dann erzeuge den Inhalt für die ListView
                files = selectedDirectory.listFiles();
                int idn = 0;
                for (File file : files) {
                    if (file.getName().endsWith(".msg")) {
                        idn++;
                    }
                }
                File[] nureMails = new File[idn];
                int inm = 0;
                for (File file : files) {
                    if (file.getName().endsWith(".msg"))
                        nureMails[inm++] = file;
                }

                String[] dateinamen = new String[idn];
                for (int i = 0; i < nureMails.length; i++) {
                    dateinamen[i] = nureMails[i].getName();
                }

                listView.setItems(FXCollections.observableArrayList(dateinamen));
                listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            });

            lbl_bearbeite.setText("bearbeite " + Integer.toString(dateianzahl) + " eMails");

            progess.prefWidthProperty().bind(vbox.widthProperty().subtract(30.0));  //  -20 is for

            progess.progressProperty().bind(worker.progressProperty());
            //lbl_aktuell.textProperty().bind(progess.progressProperty().multiply(10000.0).asString() );

            worker.messageProperty().addListener((observable, oldValue, newValue) -> {
                ta_Statusbereich.appendText("\n" + newValue);
            });

            new Thread(worker).start();
        }
    }
}
