/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUIRegion;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class CircleGUI extends BaseGUI{

        
    PlacementGUIRegion placement;
    ColorPicker cp;
    Paint paint = Color.WHITE;
    
    public CircleGUI(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- Circle GUI ---");

        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }
    
    
    @Override
    public Node getNode(BUndoManager undoManager) {
        Ellipse returnMe = new Ellipse();
        returnMe.setStroke(paint);
        returnMe.setStrokeWidth(5);
        //returnMe.setRadius(100, 100, 100);
        return returnMe;
    }
    
    @Override
    public void editNode(Node n) {
        Ellipse returnMe = (Ellipse) n;
        returnMe.setStyle("-fx-background-color: #00000000");
        returnMe.setFill(Color.TRANSPARENT);
        returnMe.setLayoutX(placement.getCoord().getX());
        returnMe.setLayoutY(placement.getCoord().getY());
        double xCenter =  placement.getSize().getX()/2.0;
        double yCenter = placement.getSize().getY()/2.0;  
        double radiusX = placement.getSize().getX()/2.0;
        double radiusY = placement.getSize().getY()/2.0;
        returnMe.setCenterX(xCenter);
        returnMe.setCenterY(yCenter);
        returnMe.setRadiusX(radiusX);
        returnMe.setRadiusY(radiusY);
        //System.out.println(n);
        //n.setStyle("-fx-background-color: #"+color);
//        Pane p = (Pane) n;
//        p.setMinSize(p.getPrefWidth(), p.getPrefHeight());
//        p.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //n.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2, cp.getValue().toString().length()-2));
    }
    
    @Override
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
    
    @Override
    public void setSize(double x, double y) {
        placement.setSize(x, y);
    }
    
    @Override
    public void setColor(String color){
        paint = Paint.valueOf(color);
    }
    
}
