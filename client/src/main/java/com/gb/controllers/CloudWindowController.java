package com.gb.controllers;

import com.gb.classes.MyDir.CloudCatalog;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.*;
import com.gb.classes.Command;
import com.gb.net.NettyNet;
import com.gb.views.*;
import com.gb.views.ico.icoCatalog.Large;
import com.gb.views.ico.icoCatalog.TileElement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class CloudWindowController extends WindowTilePane implements Initializable {
    public TextField loginField;
    public TextField passField;
    public Label logout;
    public Pane animatedProgress;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private static NettyNet net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(this);
        interactiveWindow.getStylesheets().add("com/gb/style.css");
        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();
        net = new NettyNet(this::readCommand, animatedProgress);
    }

    public void sendMessages(Command command) {
        net.sendMessages(command);
    }

    private void readCommand(Command command) {

        log.debug("Received: {}", command);
        if (command != null) {
            String com = command.getName();
            switch (com) {
                case "UpdateCatalog" -> System.out.println("Update catalog");
                case "Test" -> System.out.println("Test");
                case "myDirectory" -> updateViewNew((MyDirectory) command);
                case "cloudCatalog" -> updateCatalog((CloudCatalog) command);
                case "newFile" -> createNewFile((NewFile) command);
                case "message" -> serverMessage((MyMessage) command);
                case "userDisconnect" -> windowLogin();
            }
        }

/*        Platform.runLater(() -> {
            statuses.getItems().add(message);
        });*/


        /*
        Platform.runLater(() -> {
            listView.getItems().add(message);
        });
        */
    }

    public void createNewFile(NewFile newFile) {
        try {
            Path createFile = Paths.get(newFile.getFile().getPath());
            Files.write(createFile, newFile.getDataByte(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateList(ActionEvent actionEvent) {
        UpdateCatalog uc = new UpdateCatalog();
        sendMessages(uc);
    }

    public void AddDirectory(ActionEvent actionEvent) {

        TileElement newElement = new Large(new ImageView(ico.getIco("cat")), new File(currentDir + "\\" + readTemporaryName()), controller::sendMessages);
        workingWindow.getChildren().add(newElement);
        newElement.editing();

    }

    public void rename(ActionEvent actionEvent) {

        List<TileElement> list = getSelected();
        TileElement renElement = list.get(0);

        if(renElement != null){
            renElement.rename();
        }

    }

    public void DeleteButton(ActionEvent actionEvent) {
        StringBuilder delName = new StringBuilder();
        List<TileElement> list = getSelected();
        if (list.size() == 0){
            return;
        }else if (list.size() < 4){
            for (TileElement element : list) {
                Large l = (Large) element;
                if (delName.length() == 0) {
                    delName.append(l.getFile().getName());
                } else {
                    delName.append(", ").append(l.getFile().getName());
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Large l = (Large) list.get(i);
                if (i == 0) {
                    delName.append(l.getFile().getName());
                } else {
                    delName.append(", ").append(l.getFile().getName());
                }
            }
            delName.append(" и др.");
        }
        Alert alert = new Alert(Alert.AlertType.WARNING, "Удалить " + delName.toString() + "?", ButtonType.CANCEL, ButtonType.YES);
//            Alert alert = new Alert(Alert.AlertType.WARNING, new String(("Удалить " + delName + "?").getBytes(StandardCharsets.UTF_8)), ButtonType.CANCEL, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            for (TileElement t : list) {
                Large l = (Large) t;
                DeleteFile del = new DeleteFile(l.getFile());
                sendMessages(del);
            }
        }
    }

    public void serverMessage(MyMessage command) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, command.getText(), ButtonType.OK);
//            Alert alert = new Alert(Alert.AlertType.WARNING, answer, ButtonType.OK);
//            Alert alert = new Alert(Alert.AlertType.WARNING, new String(command.getText().getBytes(StandardCharsets.UTF_8)), ButtonType.OK);
            alert.showAndWait();
        });
    }

    public void AddFile(ActionEvent actionEvent) throws IOException {
/*
        Вот эта тема загружает Эксплорер в указанной папке

        String onlyPath = "D:\\";
        String completeCmd = "explorer.exe /select," + onlyPath;
        new ProcessBuilder(("explorer.exe " + completeCmd).split(" ")).start();
        */
//        new ProcessBuilder("explorer.exe").start(); // а вот конкретно так стартует библиотека пользователя


        List<File> files = null;
        files = fileChooser.showOpenMultipleDialog(HomeWindow.getScene().getWindow());
        if (files != null) {
            for (File file : files) {
                try {
                    byte[] dataByte = Files.readAllBytes(file.toPath());
                    String newFileName = currentDir.getPath() + "\\" + file.getName();
                    NewFile newFie = new NewFile(new File(newFileName), dataByte);
                    sendMessages(newFie);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void DownloadButton(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(HomeWindow.getScene().getWindow());
//        ObservableList<TreeItem<UserItem>> list = treeView.getSelectionModel().getSelectedItems();
        List<TileElement> list = getSelected();

//        for (TreeItem<UserItem> userItemTreeItem : list) {
        for (TileElement element : list) {
            File file = ((Large) element).getFile();
//            File cfile = userItemTreeItem.getValue().getFile();
            sendMessages(new GetFile(file, dir.getPath()));
        }
    }

    public void RenameButton(ActionEvent actionEvent) {
        TreeItem<UserItem> newItem = treeView.getSelectionModel().getSelectedItem();
        newItem.getValue().renameStarted();
        setEditing(newItem);
    }

    public void Login(ActionEvent actionEvent) {

        String login = loginField.getText();
        String password = passField.getText();
        if (login.isBlank() || password.isBlank()) {
            return;
        }
        UserConnect userConnect = new UserConnect(login, password);
        sendMessages(userConnect);

    }

    public void Registration(ActionEvent actionEvent) {

        String login = loginField.getText();
        String password = passField.getText();

        if (login.isBlank() || password.isBlank()) {
            return;
        }

        UserCreate userCreate = new UserCreate(login, password);
        sendMessages(userCreate);

    }


    public void Logout(MouseEvent mouseEvent) {
        windowLogin();
        sendMessages(new UserDisconnect());
    }
}
