/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class ShowOneGUI extends BaseGUI {

    StackPane stack;
    Map<String, BaseGUI> map;
    BaseGUI shown = null;

    public ShowOneGUI(Node parent) {
        super(parent);
        stack = new StackPane();
        getChildren().add(stack);
        map = new HashMap<String, BaseGUI>();
    }

    public void addGUI(String str, BaseGUI n) {

        if (map.isEmpty() || (map.containsKey(str) && map.get(str).isVisible())) {
            n.setVisible(true);
            n.setManaged(true);
            shown = n;
        } else {
            n.setVisible(false);
            n.setManaged(false);
        }

        stack.getChildren().remove(map.get(str));
        stack.getChildren().add(n);
        map.put(str, n);

    }

    public void removeGUI(String str) {
        if (map.containsKey(str)) {
            stack.getChildren().remove(map.get(str));
            map.remove(str);
        }
    }
    
    public void showGUI(String str) {
        if (map.containsKey(str)) {
            shown.setVisible(false);
            shown.setManaged(false);
            map.get(str).setVisible(true);
            map.get(str).setManaged(true);
            shown = map.get(str);
        }
    }

    @Override
    public Node getNode(BUndoManager undoManager) {
        return shown.getNode(undoManager);
    }
    
    @Override
    public void editNode(Node n) {
        shown.editNode(n);
    }
    
    @Override
    public void setPos(double x, double y) {
        for (Map.Entry<String, BaseGUI> mapEntry : map.entrySet()) {
            mapEntry.getValue().setPos(x, y);
        }
    }
    
    @Override
    public void setColor(String color){
        //System.out.println(color);
        //System.out.println(shown);
        shown.setColor(color);
    }
    
    @Override
    public void setSize(double x, double y) {
        for (Map.Entry<String, BaseGUI> mapEntry : map.entrySet()) {
            mapEntry.getValue().setSize(x, y);
        }
    }

}
