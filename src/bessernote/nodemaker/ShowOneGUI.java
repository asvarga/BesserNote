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

/**
 *
 * @author avarga
 */
public class ShowOneGUI extends StackPane {

    Map<String, Node> map;
    Node shown = null;

    public ShowOneGUI() {
        map = new HashMap<String, Node>();
    }

    public void addGUI(String str, Node n) {

        if (map.isEmpty() || (map.containsKey(str) && map.get(str).isVisible())) {
            n.setVisible(true);
            n.setManaged(true);
            shown = n;
        } else {
            n.setVisible(false);
            n.setManaged(false);
        }

        this.getChildren().remove(map.get(str));
        this.getChildren().add(n);
        map.put(str, n);

    }

    public void removeGUI(String str) {
        if (map.containsKey(str)) {
            this.getChildren().remove(map.get(str));
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
        } else {
            System.out.println("poop");
        }
    }
}
