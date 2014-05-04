package bessernote.nodemaker.placement;

import bessernote.nodemaker.BaseGUI;
import bessernote.nodemaker.ShowOneGUI;
import bessernote.ui.BNumberField;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class PlacementGUI1D extends BaseGUI {
    
    boolean x;
    
    ComboBox combo;
    ShowOneGUI show1;
    BaseGUI manual;
    BaseGUI fill;
    BNumberField coord;
    BNumberField size;
    BNumberField pad;
    
    public PlacementGUI1D(Node top, double spacing, boolean x) {
        
        super(top, spacing, false);
        this.x = x;
                
        combo = new ComboBox();
        combo.getItems().addAll("Manual", "Fill");
        combo.setValue("Manual");
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                show1.showGUI(t1);
                _top.autosize();
            }
        });
        HBox h0 = new HBox();
        h0.getChildren().add(combo);
        h0.setAlignment(Pos.CENTER);
        
        manual = new BaseGUI(top, spacing, false);
        HBox h = new HBox();
        h.getChildren().add(new Text(x ? "X: " : "Y: "));
        coord = new BNumberField("0");
        h.getChildren().add(coord);
        manual.getChildren().add(h);
        HBox h2 = new HBox();
        h2.getChildren().add(new Text(x ? "W: " : "H: "));
        size = new BNumberField("100");
        h2.getChildren().add(size);
        manual.getChildren().add(h2);
        
        fill = new BaseGUI(top, spacing, false);
        HBox h3 = new HBox();
        h3.getChildren().add(new Text("Pad: "));
        pad = new BNumberField("0");
        h3.getChildren().add(pad);
        fill.getChildren().add(h3);

        show1 = new ShowOneGUI(top);
        show1.addGUI("Manual", manual);
        show1.addGUI("Fill", fill);
                
        getChildren().addAll(h0, show1);
    }
    
    @Override
    public void editNode(Node n) {
        try {
            Region r = (Region) n;
            Region parent = (Region) n.parentProperty().getValue();
            if (x) {
                if (combo.getValue().equals("Manual")) {
                    r.setLayoutX(coord.getNum());
                    r.setPrefWidth(size.getNum());
                } else {
                    double num = pad.getNum();
                    EasyBind.bind(r.layoutXProperty(), num);
                    r.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*num), 0));
                }
            } else {
                if (combo.getValue().equals("Manual")) {
                    r.setLayoutY(coord.getNum());
                    r.setPrefHeight(size.getNum());
                } else {
                    double num = pad.getNum();
                    EasyBind.bind(r.layoutYProperty(), num);
                    r.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*num), 0));
                }
            }      
        } catch (ClassCastException e) { 
            System.out.println("Node or it's parent isn't a Region. "
                    + "Only use PlacementGUIRegion in Region MetaGUIs.");
        }
    }
    
    @Override
    public void setPos(double x, double y) {
        if (this.x) {
            coord.setText(Double.toString(x));
        } else {
            coord.setText(Double.toString(y));
        }
//        combo.setValue("Manual");
//        show1.showGUI("Manual");
    }
    
    @Override
    public void setSize(double x, double y) {
        if (this.x) {
            size.setText(Double.toString(x));
        } else {
            size.setText(Double.toString(y));
        }
//        combo.setValue("Manual");
//        show1.showGUI("Manual");
    }
    
    public double getSize(){
        return size.getNum();
    }
    
    public double getCoord(){
        return coord.getNum();
    }
}
