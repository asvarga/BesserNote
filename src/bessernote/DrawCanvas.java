/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package bessernote;

import bessernote.BesserNote;
import bessernote.DashedBox;
import java.util.ArrayList;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
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
    private Path doodle = new Path();
    private MoveTo move;
        private GraphicsContext gc = this.getGraphicsContext2D();
    private EventHandler<MouseEvent> clicked;
    private EventHandler<MouseEvent> dragged;
    private EventHandler<MouseEvent> released;
    private Color c;
    
    public DrawCanvas(final BesserNote besser, double width, double height){
        super(width, height);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        this.besser = besser;
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(7);
        setVisible(false);
        
        DrawCanvas this2 = this;
        this.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> ov, Parent t, Parent t1) {
                if (this2.getParent() != null) {
                    Pane p = (Pane) this2.getParent();
                    this2.widthProperty().bind(p.widthProperty());
                    this2.heightProperty().bind(p.heightProperty());
                }
            }
        });
        
        clicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                       if (e.getButton() == MouseButton.SECONDARY) {
                        besser.startOutlineX = e.getX();
                        besser.startOutlineY = e.getY();
                        
                        boolean clickedSelected = false;
                        
                        if (e.isShortcutDown()) {
                            for (Map.Entry<Node, DashedBox> entry : besser.selectBoxes.entrySet()) {
                                Node n = entry.getKey();
                                Point2D local = besser.sheetToLocal(n, e.getX(), e.getY());
                                if (n instanceof Region &&
                                        local.getX() >= 0 && 
                                        local.getY() >= 0 &&
                                        local.getX() <= n.getBoundsInLocal().getWidth() &&
                                        local.getY() <= n.getBoundsInLocal().getHeight()) {
                                    clickedSelected = true;
                                    besser.target = (Parent) n;
                                    break;
                                }
                            }
                        }
                        
                        if (clickedSelected) {
                            besser.cancelSuperClick();
                            besser.unselectAll();
                            besser.superClicked = new ArrayList<>();
                            besser.superClicked.add(besser.target);
                            besser.flipSelection(0);
                        } else {
                            besser.cancelSuperClick();
                            besser.unselectAll();
                            besser.superClicked = besser.superClick(e.getX(), e.getY());
                            if (besser.superClicked.size() > 0 && besser.superClicked.get(0) instanceof Pane) {
                                besser.flipSelection(0);
                                besser.target = (Parent) besser.superClicked.get(0);
                            } else {
                                besser.unselectAll();
                                besser.target = besser.sheet;
                            }
                        }
                                              
                    
                    doodle = new Path();
                    doodle.setLayoutX(e.getX());
                    doodle.setLayoutY(e.getY());                    
                    move = new MoveTo(e.getX(), e.getY());
                    gc.beginPath();
                    gc.lineTo(e.getX(), e.getY());
                    doodle.getElements().add(move);
                    gc.stroke();
                    }
                }
                };
        dragged = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.SECONDARY){
                gc.lineTo(e.getX(), e.getY());
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
                doodle.getElements().add(new LineTo(e.getX(), e.getY()));
                }
            }
            };
        released = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.SECONDARY){
                gc.stroke();
                gc.closePath();

//                System.out.println(doodle.getLayoutX() + " " + doodle.getLayoutY());
                besser.addDoodle(doodle);
//                System.out.println(doodle);
                gc.clearRect(0, 0, 2000, 2000);
                }
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
        this.c = c;
        gc.setStroke(this.c);
        doodle.setStroke(this.c);
    }
    
    public void changeBrushWidth(){
        //TODO: Do something
    }
    
}