/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.BesserNote;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author ddliu
 * The drawing menu which has options for lines, circles, and (later) rectangles.
 * It also has a color picker. 
 * And it switches on graphics mode.
 */
public class DrawingMenu extends VBox{
    
    public BesserNote besser;
    private ToggleGroup group = new ToggleGroup();
    private Color c;
    private final ColorPicker cp = new ColorPicker();
    private final ToggleButton cursor = new ToggleButton();
    private final ToggleButton line = new ToggleButton();
    private final ToggleButton circle = new ToggleButton();
    private Object currentMode;
    
    public DrawingMenu(final BesserNote besser) throws FileNotFoundException {
        super();
        setAlignment(Pos.BOTTOM_LEFT);
        
        this.besser = besser;
        
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
        
        //// Add buttons ////
        
        //Cursor
        ImageView cursorImage = new ImageView(new Image(new FileInputStream("images/cursor.jpg")));
        cursor.setGraphic(cursorImage);
        cursor.setToggleGroup(group);
        //Drawing
        ImageView lineImage = new ImageView(new Image(new FileInputStream("images/draw.jpg")));
        line.setGraphic(lineImage);
        line.setToggleGroup(group);
        //Circle
        ImageView circleImage = new ImageView(new Image(new FileInputStream("images/circle.jpg")));
        circle.setGraphic(circleImage);
        circle.setToggleGroup(group);
        
        //// Color Picker ////
        cp.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                c = cp.getValue();
                besser.strokeColor(c);
            }
        });
        
        //Adds buttons to the VBox 
        this.getChildren().addAll(cursor, line, circle, cp);

        //Listens for clicks
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    if(line.isSelected()){
                        besser.circleOff();
                        besser.drawOn();
                    }
                    else if(circle.isSelected()){
                        besser.drawOff();
                        besser.circleOn();
                    }
                    else if(cursor.isSelected()){
                        besser.drawOff();
                        besser.circleOff();
                    }
                 }
            });

        //Listens for exit
        
        this.addEventFilter(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE){
                        besser.circleOff();
                        besser.drawOff();
                    }
                }
            }
        );
        
        
    }

    public Node getNode() {
        return null;
    }

    public void editNode(Node n) {
        
    }
    
    public void setPos(double x, double y) {
        
    }
    
    public void setSize(double x, double y) {
        
    }
}
    
