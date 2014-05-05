/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.BesserNote;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author ddliu
 */
public class DockingMenu extends VBox{
    private final Node _top = this;
    private final double spacing = 0.5;
    private ToggleGroup group = new ToggleGroup();
    private Color c;
    //Reference to BesserNote
    private final BesserNote besser;
    //Buttons
    private final ColorPicker cp = new ColorPicker();
    private final ToggleButton Draw = new ToggleButton();
    private final ToggleButton Pane = new ToggleButton();
    private final ToggleButton WrapPane = new ToggleButton();
    private final ToggleButton TextArea = new ToggleButton();
    private final ToggleButton ScrollPane = new ToggleButton();
    private final ToggleButton TabPane = new ToggleButton();
    private final ToggleButton FlashCard = new ToggleButton();
    private final ToggleButton ImageButton = new ToggleButton();
    private final ToggleButton DeckButton = new ToggleButton();
    //private final RadioButton radioButton = new RadioButton();

    
    public NodeGUI nodeGUI;
    
    public DockingMenu(NodeGUI nodeGUI, BesserNote besser) throws FileNotFoundException{
        this.getStylesheets().add(getClass().getResource("toggle-button.css").toExternalForm());
        this.nodeGUI = nodeGUI;
        this.besser = besser;
        
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
        
        //// Add buttons ////
        
        //Draw
        ImageView drawImage = new ImageView(new Image(new FileInputStream("images/Draw.png")));
        Draw.setGraphic(drawImage);
        Draw.setToggleGroup(group);
            //Add Listener
            Draw.focusedProperty().addListener(new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                    {
                        if (newPropertyValue)
                        {
                            setDrawMode();
                        }
                        else
                        {
                            setDrawOff();
                        }
                    }
                });
        
       //Escape unselects everything.
        this.addEventFilter(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        group.selectToggle(null);
                        setDrawOff();
                        nodeGUI.setValue("null");
                    }
                }});
                             
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
        //Image
        ImageView imageImage = new ImageView(new Image(new FileInputStream("images/Image.png")));
        ImageButton.setGraphic(imageImage);
        ImageButton.setToggleGroup(group);
        //Image
        ImageView deckImage = new ImageView(new Image(new FileInputStream("images/Deck.png")));
        DeckButton.setGraphic(deckImage);
        DeckButton.setToggleGroup(group);
        
        //Add radio button
        //radioButton.setToggleGroup(group);
        
        //// Color Picker ////
        
        //Initialize color to white
        c = cp.getValue();
        nodeGUI.setColor(cp.getValue().toString().substring(2, cp.getValue().toString().length() -2 ));
        cp.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                c = cp.getValue();
                besser.strokeColor(c);
                nodeGUI.setColor(c.toString().substring(2, c.toString().length() -2 ));
            }
        });
        
        //Add Buttons
        this.getChildren().addAll(Pane, WrapPane, Draw, TextArea, ScrollPane, TabPane, FlashCard, ImageButton, DeckButton, cp);
        
        //Listens for clicks and toggles modes
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    toggleNodeGUIMode();
                    nodeGUI.setColor(c.toString().substring(2, c.toString().length() -2 ));
                 }
            });
        
        
        this.setPaneMode();
    }
    
    public void toggleNodeGUIMode(){
        if(Draw.isSelected()){
            besser.drawOn();
            Draw.setSelected(true);
        }
        if(Pane.isSelected()){
            nodeGUI.setValue("Pane");
            Pane.setSelected(true);
        }
        else if(WrapPane.isSelected()){
            nodeGUI.setValue("WrapPane");
            WrapPane.setSelected(true);
        }
        else if(TextArea.isSelected()){
            nodeGUI.setValue("TextArea");
            TextArea.setSelected(true);
        }
        else if(ScrollPane.isSelected()){
            nodeGUI.setValue("ScrollPane");
            ScrollPane.setSelected(true);
        }
        else if(TabPane.isSelected()){
            nodeGUI.setValue("TabPane");
            TabPane.setSelected(true);
        }
        else if(FlashCard.isSelected()){
            nodeGUI.setValue("FlashCard");
            FlashCard.setSelected(true);
        }
        else if(ImageButton.isSelected()){
            nodeGUI.setValue("Image");
            ImageButton.setSelected(true);
        }
        else if(DeckButton.isSelected()){
            nodeGUI.setValue("Deck");
            DeckButton.setSelected(true);
        }
        //No mode is selected.
    }
    
    public void setDrawMode(){
        Draw.setSelected(true);
        group.selectToggle(Draw);
        besser.drawOn();
        Draw.requestFocus();
        Draw.fire();
    }
    
    public void setDrawOff(){
        Draw.setSelected(false);
        besser.drawOff();
    }
    
    public void setPaneMode(){
        Pane.setSelected(true);
        group.selectToggle(Pane);
        nodeGUI.setValue("Pane");
        Pane.requestFocus();
        Pane.fire();
    }
    
    public void setWrapPaneMode(){
        WrapPane.setSelected(true);
        group.selectToggle(WrapPane);
        nodeGUI.setValue("WrapPane");
        WrapPane.requestFocus();
        WrapPane.fire();
    }
    
    public void setTextAreaMode(){
        TextArea.setSelected(true);
        group.selectToggle(TextArea);
        nodeGUI.setValue("TextArea");
        TextArea.requestFocus();
        TextArea.fire();        
    }
    
    public void setScrollPaneMode(){
        ScrollPane.setSelected(true);
        group.selectToggle(ScrollPane);
        nodeGUI.setValue("ScrollPane");
        ScrollPane.requestFocus();
        ScrollPane.fire();
    }
    
    public void setTabPaneMode(){
        TabPane.setSelected(true);
        group.selectToggle(TabPane);
        nodeGUI.setValue("TabPane");
        TabPane.requestFocus();
        TabPane.fire();
    }
    
    public void setFlashCardMode(){
        FlashCard.setSelected(true);
        group.selectToggle(FlashCard);
        nodeGUI.setValue("FlashCard");
        FlashCard.requestFocus();
        FlashCard.fire();
    }
    
    public void setImageMode(){
        ImageButton.setSelected(true);
        group.selectToggle(ImageButton);
        nodeGUI.setValue("Image");
        ImageButton.requestFocus();
        ImageButton.fire();
    }
    
    public void setDeckMode(){
        DeckButton.setSelected(true);
        group.selectToggle(DeckButton);
        nodeGUI.setValue("Deck");
        DeckButton.requestFocus();
        DeckButton.fire();
    }
    
    public void unselectAll(){
        group.selectToggle(null);
        setDrawOff();
        nodeGUI.setValue("null");
    }
    
}
