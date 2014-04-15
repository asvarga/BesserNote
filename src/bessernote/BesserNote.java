/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;

import bessernote.nodemaker.NodeGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author avarga
 */
public class BesserNote extends Application {

    private Scene scene;
    private BorderPane root;
    private MenuBar menuBar;
    private Pane sheet;
    
    private Popup popup;
    private NodeGUI nodeGUI;

    @Override
    public void start(final Stage stage) {

        root = new BorderPane();
        scene = new Scene(root, 640, 480, Color.BLACK);

        sheet = new Pane();
        root.setCenter(sheet);
        sheet.setStyle("-fx-background-color: black;");

        //// NODE MAKER ////

        popup = new Popup();
        nodeGUI = new NodeGUI(5);
        popup.getContent().addAll(nodeGUI);
        popup.setAutoFix(false);
        popup.setHideOnEscape(true);
        
        nodeGUI.createButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Node newNode = nodeGUI.getNode();
                if (newNode != null) {
                    sheet.getChildren().add(newNode);
                }
                popup.hide();
            }
        });
        
        //// RIGHT CLICK ////
        
        sheet.addEventFilter(MouseEvent.MOUSE_CLICKED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        nodeGUI.setPos(e.getX(), e.getY());
                        popup.show(stage);
                    }
                };
            }
        );

        //// MENU BAR ////

        menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New...");
        MenuItem menuItemOpen = new MenuItem("Open...");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menuFile.getItems().addAll(
                menuItemNew,
                menuItemOpen,
                new SeparatorMenuItem(),
                menuItemExit);

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        MenuItem menuItemAdd = new MenuItem("Add");
        menuItemAdd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                popup.show(stage);
            }
        });
        menuEdit.getItems().addAll(menuItemAdd);

        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.setTop(menuBar);

        ////  ////

        stage.setTitle("BesserNote");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
