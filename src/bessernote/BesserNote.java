/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;

import bessernote.nodemaker.NodeGUI;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FillTransition; 
import javafx.animation.ParallelTransition; 
import javafx.animation.RotateTransition; 
import javafx.animation.ScaleTransition; 
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition; 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration; 


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
    private Window primaryStage;
    private Desktop desktop = Desktop.getDesktop();

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
        
        final FileChooser fileChooser = new FileChooser();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New...");
        menuItemNew.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(final ActionEvent e){
                Application app2 = new BesserNote();
                Stage anotherStage = new Stage();
                try {
                    app2.start(anotherStage);
                } catch (Exception ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        MenuItem menuItemOpen = new MenuItem("Open...");
        menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                }
        });
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t){
                
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
    
        private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                BesserNote.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
        }
        /*
        private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
>>>>>>> b86d65958f341bb6956d4fec61b8679998fb282a

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    
    
              public void handle(ActionEvent event) {
              FileChooser fileChooser = new FileChooser();
  
              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
              fileChooser.getExtensionFilters().add(extFilter);
              
              //Show save file dialog
              File file = fileChooser.showSaveDialog(primaryStage);
              
              if(file != null){
                  SaveFile(Santa_Claus_Is_Coming_To_Town, file);
              }
          }
      });
        */
      
    public static void main(String[] args) {
        launch(args);
    }
}
