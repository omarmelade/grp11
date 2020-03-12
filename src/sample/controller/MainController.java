package sample.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            initDrawer();
    }

    private void initDrawer(){
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("../view/toolbarController.fxml"));
            drawer.setSidePane(toolbar);
            drawer.close();
        }catch (IOException ignored){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ignored);
        }

        HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
                task.setRate(task.getRate() * -1 );
                task.play();
                if(drawer.isOpened()){
                    drawer.close();
                }
                else
                {
                    drawer.open();
                }
            });
        }
    }
