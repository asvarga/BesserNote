/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.canvases;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import bessernote.DashedBox;
import java.util.ArrayList;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

/**
 * DrawCanvas is the preview for a line that is drawn on the canvas by a user. 
 * Once the user releases his mouse (if escape is not pressed), then the line is rendered on the component with focus in Bessernote.
 * @author ddliu
 */
public class DrawCanvas extends Canvas{
    
    //The start x and y where the user clicked.
    private double x;
    private double y;
    private Path doodle;
    private GraphicsContext gc = this.getGraphicsContext2D();
    
    public DrawCanvas(double width, double height){
        super(width, height);
        this.setStyle("-fx-background-color: black;");
        setup();
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(7);
        setVisible(false);
    }
    
    
    public final void setup(){
          this.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Mouse clicked.");
                    gc.beginPath(); 
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                    }
                });
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e){
                gc.lineTo(e.getX(), e.getY());
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
            }
            });
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                System.out.println("Mouse released");
                gc.fill();
                gc.stroke();
                gc.closePath();

            }
        });
    }
    
    public void changeColor(Color c){
        System.out.println(c);
        gc.setStroke(c);
    }
    
    public void changeBrushWidth(){
        //TODO: Do something
    }
    
}
