/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.ui.BFlashCard;
import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BWrapPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author ddliu
 */
public class dockingMenu extends VBox{
    private final Node _top = this;
    private final double spacing = 0.5;
    private ToggleGroup group = new ToggleGroup();
    private Color c;
    //Buttons
    private final ColorPicker cp = new ColorPicker();
    private final ToggleButton Pane = new ToggleButton();
    private final ToggleButton WrapPane = new ToggleButton();
    private final ToggleButton TextArea = new ToggleButton();
    private final ToggleButton ScrollPane = new ToggleButton();
    private final ToggleButton TabPane = new ToggleButton();
    private final ToggleButton FlashCard = new ToggleButton();
    
    //None of these actually get displayed. Purely for the purposes of making nodes.
    private PaneGUI makePane = new PaneGUI(_top, spacing); 
    private ScrollPaneGUI makeScrollPane = new ScrollPaneGUI(_top, spacing); 
    private WrapPaneGUI makeWrapPane = new WrapPaneGUI(_top, spacing); 
    private TextAreaGUI makeTextArea = new TextAreaGUI(_top, spacing); 
    private TabPaneGUI makeTabPane = new TabPaneGUI(_top, spacing); 
    private FlashCardGUI makeFlashCard = new FlashCardGUI(_top, spacing); 
    
    public NodeGUI nodeGUI;
    
    public dockingMenu(NodeGUI nodeGUI) throws FileNotFoundException{
        
        this.nodeGUI = nodeGUI;
        
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
        
        //// Add buttons ////
        
        //Pane
        ImageView paneImage = new ImageView(new Image(new FileInputStream("images/Pane.png")));
        Pane.setGraphic(paneImage);
        Pane.setToggleGroup(group);
        //WrapPane
        ImageView wrapImage = new ImageView(new Image(new FileInputStream("images/WrapPane.png")));
        WrapPane.setGraphic(wrapImage);
        WrapPane.setToggleGroup(group);
        //TextArea
        ImageView textImage = new ImageView(new Image(new FileInputStream("images/Text.png")));
        TextArea.setGraphic(textImage);
        TextArea.setToggleGroup(group);
        //ScrollPane
        ImageView scrollImage = new ImageView(new Image(new FileInputStream("images/ScrollPane.png")));
        ScrollPane.setGraphic(scrollImage);
        ScrollPane.setToggleGroup(group);
        //TabPane
        ImageView tabImage = new ImageView(new Image(new FileInputStream("images/TabPane.png")));
        TabPane.setGraphic(tabImage);
        TabPane.setToggleGroup(group);
        //TextArea
        ImageView flashImage = new ImageView(new Image(new FileInputStream("images/FlashCard.png")));
        FlashCard.setGraphic(flashImage);
        FlashCard.setToggleGroup(group);   
        
        //// Color Picker ////
        
        //Initialize color to white
        nodeGUI.setColor(cp.getValue().toString().substring(2, cp.getValue().toString().length() -2 ));
        cp.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                c = cp.getValue();
                nodeGUI.setColor(c.toString().substring(2, c.toString().length() -2 ));
            }
        });
        
        //Add Buttons
        this.getChildren().addAll(Pane, WrapPane, TextArea, ScrollPane, TabPane, FlashCard, cp);
        
        
        //Listens for clicks and toggles modes
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    toggleNodeGUIMode();
                    nodeGUI.setColor(c.toString().substring(2, c.toString().length() -2 ));
                 }
            });
    }
    
    public void toggleNodeGUIMode(){
        if(Pane.isSelected()){
            nodeGUI.setValue("Pane");
        }
        else if(WrapPane.isSelected()){
            nodeGUI.setValue("WrapPane");
        }
        else if(TextArea.isSelected()){
            nodeGUI.setValue("TextArea");
        }
        else if(ScrollPane.isSelected()){
            nodeGUI.setValue("ScrollPane");
        }
        else if(TabPane.isSelected()){
            nodeGUI.setValue("TabPane");
        }
        else if(FlashCard.isSelected()){
            nodeGUI.setValue("FlashCard");
        }
        //No mode is selected.
    }
}
