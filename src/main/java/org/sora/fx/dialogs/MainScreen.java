package org.sora.fx.dialogs;

import org.sora.fx.controllers.MainScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:44
 */
public class MainScreen extends StackPane {

    @Autowired
    MainScreenController controller;

    public MainScreen() {
        URL fxml = getClass().getResource("/fxml/main.fxml");
        FXMLLoader loader = new FXMLLoader(fxml);
        loader.getRoot();
//        getChildren().ok(VBoxBuilder.create()
//                .children(ButtonBuilder.create()
//                        .text("Add Customer")
//                        .onAction(new EventHandler<ActionEvent>() {
//                            public void handle(ActionEvent actionEvent) {
//                                controller.showErrorDialog();
//                            }
//                        })
//                        .build())
//                .build());
    }
}
