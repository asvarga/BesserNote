/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class DoodleSave implements Saveable {
    
    private List<PathElementSaveable> pathElements = new ArrayList<>();
    private String color;
    
    public DoodleSave(Path aPath){
        for(PathElement element: aPath.getElements()){
            if(element instanceof LineTo)
                pathElements.add(new lineToSave((LineTo) element));
            else if(element instanceof MoveTo)
                pathElements.add(new moveToSave((MoveTo)element));
        }
        color = aPath.getStroke().toString();
    }

    @Override
    public Node create(BUndoManager undoManager) {
        Path returnMe = new Path();
        returnMe.setStrokeWidth(7);
        returnMe.setStroke(Color.WHITE);
        for(PathElementSaveable element: pathElements){
            returnMe.getElements().add(element.create());
        }
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
