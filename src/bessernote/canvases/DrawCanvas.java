/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.canvases;

import bessernote.BesserNote;
import bessernote.DashedBox;
import java.util.ArrayList;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * DrawCanvas is the preview for a line that is drawn on the canvas by a user. 
 * Once the user releases his mouse (if escape is not pressed), then the line is rendered on the component with focus in Bessernote.
 * @author ddliu
 */
public class DrawCanvas extends Canvas{
    
    //The start x and y where the user clicked.
    public BesserNote besser;
    private double x;
    private double y;
    private Path doodle;
    private MoveTo move;
    private GraphicsContext gc = this.getGraphicsContext2D();
    private EventHandler<MouseEvent> clicked;
    private EventHandler<MouseEvent> dragged;
    private EventHandler<MouseEvent> released;
    
    public DrawCanvas(final BesserNote besser, double width, double height){
        super(width, height);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        this.besser = besser;
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(7);
        setVisible(false);
        
        clicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Mouse clicked.");
                    doodle = new Path();
                    move = new MoveTo(e.getX(), e.getY());
                    gc.beginPath(); 
                    gc.lineTo(e.getX(), e.getY());
                    doodle.getElements().add(move);
                    gc.stroke();
                    }
                };
        dragged = new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e){
                gc.lineTo(e.getX(), e.getY());
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
                doodle.getElements().add(new MoveTo(e.getX(), e.getY()));
            }
            };
        released = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                System.out.println("Mouse released");
                gc.fill();
                gc.stroke();
                gc.closePath();
                System.out.println(doodle);
                besser.addDoodle(doodle);
                gc.clearRect(0, 0, 2000, 2000);
            }
        };

        setup();
    }
    
    
    
    
    public final void setup(){
        addListeners();
    }
    
    public void addListeners(){
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, clicked);
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragged);
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, released);
    }
    
    public void removeListeners(){
        this.removeEventFilter(MouseEvent.MOUSE_PRESSED, clicked);
        this.removeEventFilter(MouseEvent.MOUSE_DRAGGED, dragged);
        this.removeEventFilter(MouseEvent.MOUSE_RELEASED, released);
    }
    
    public void changeColor(Color c){
        gc.setStroke(c);
    }
    
    public void changeBrushWidth(){
        //TODO: Do something
    }
    
    /*
    Turns out gc.restore() only restores soft state. Poo-poo.
    */
    public void undoDrawing(){
        System.out.println("FHIODUFHKDFH");
        gc.restore();
    }
    
}

