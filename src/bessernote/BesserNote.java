/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;

import javafx.animation.FillTransition; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Rectangle; 
import javafx.stage.Stage; 
import javafx.animation.Timeline; 
import javafx.animation.ParallelTransition; 
import javafx.animation.RotateTransition; 
import javafx.animation.ScaleTransition; 
import javafx.animation.TranslateTransition; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
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
    private NodeMaker nodeMaker;

    @Override 
    public void start(final Stage stage) { 
        
        root = new BorderPane(); 
        scene = new Scene(root, 640, 480, Color.BLACK); 
        
        sheet = new Pane();
        root.setCenter(sheet);
        sheet.setStyle("-fx-background-color: black;");
        
        //// NODE MAKER ////
        
        popup = new Popup();
        nodeMaker = new NodeMaker();
        popup.getContent().addAll(nodeMaker);
        popup.setAutoFix(false);
        popup.setHideOnEscape(true);
        popup.show(stage);
        
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
                popup.setX(stage.xProperty().doubleValue());
                popup.setY(stage.yProperty().doubleValue()+
                        stage.getHeight()-nodeMaker.getHeight());
                popup.show(stage);
            }
        });
        menuEdit.getItems().addAll(menuItemAdd);
        
        // --- Menu View
        Menu menuView = new Menu("View");
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.setTop(menuBar);
        
        //// MAGIC SQUARE ////
        
        Rectangle r = new Rectangle(100, 100, 250, 250); 
        r.setFill(Color.BLUE); 
        sheet.getChildren().add(r); 
 
        TranslateTransition translate = 
        new TranslateTransition(Duration.millis(750)); 
        translate.setToX(390); 
        translate.setToY(200); 
 
        FillTransition fill = new FillTransition(Duration.millis(750)); 
        fill.setToValue(Color.RED); 
 
        RotateTransition rotate = new RotateTransition(Duration.millis(750)); 
        rotate.setToAngle(360); 
 
        ScaleTransition scale = new ScaleTransition(Duration.millis(750)); 
        scale.setToX(0.1); 
        scale.setToY(0.1); 
 
        ParallelTransition transition = new ParallelTransition(r, 
        translate, fill, rotate, scale); 
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true); 
        transition.play();
        
        ////  ////
 
        stage.setTitle("BesserNote"); 
        stage.setScene(scene); 
        stage.show(); 
    } 

    public static void main(String[] args) {
        launch(args);
    }
}
