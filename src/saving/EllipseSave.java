/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class EllipseSave implements Saveable{
    
    private double xPos, yPos;
    private double xCent, yCent;
    private double xRad, yRad;
    private String color;
    
    
    public EllipseSave(Ellipse ellipse){
        xPos = ellipse.getLayoutX();
        yPos = ellipse.getLayoutY();
        xCent = ellipse.getCenterX();
        yCent = ellipse.getCenterY();
        xRad = ellipse.getRadiusX();
        yRad = ellipse.getRadiusY();   
        color = ellipse.getStroke().toString();
    }
    
    
    
    @Override
    public Node create(BUndoManager undoManager) {
        Ellipse returnMe = new Ellipse();
        returnMe.setCenterX(xCent);
        returnMe.setCenterY(yCent);
        returnMe.setRadiusX(xRad);
        returnMe.setRadiusY(yRad);
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.setStyle("-fx-background-color: #00000000");
        returnMe.setFill(Color.TRANSPARENT);
        returnMe.setStroke(Paint.valueOf(color));
        returnMe.setStrokeWidth(5);
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return new ArrayList<>();
    }
    
    
    
    
}
