package bessernote.nodemaker.placement;

import bessernote.nodemaker.BaseGUI;
import bessernote.nodemaker.ShowOneGUI;
import bessernote.ui.NumberField;
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
    NumberField coord;
    NumberField pad;
    
    public PlacementGUI1D(double spacing, boolean x) {
        
        super(spacing, false);
        
        this.x = x;
                
        combo = new ComboBox();
        combo.getItems().addAll("Manual", "Fill");
        combo.setValue("Manual");
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                show1.showGUI(t1);
                autosize();
            }
        });
        HBox h0 = new HBox();
        h0.getChildren().add(new Text(x ? "X: " : "Y: "));
        h0.getChildren().add(combo);
        h0.setAlignment(Pos.CENTER);
        
        manual = new BaseGUI(spacing, false);
        HBox h = new HBox();
        h.getChildren().add(new Text("Loc: "));
        coord = new NumberField("0");
        h.getChildren().add(coord);
        manual.getChildren().add(h);
        
        fill = new BaseGUI(spacing, false);
        HBox h2 = new HBox();
        h2.getChildren().add(new Text("Pad: "));
        pad = new NumberField("0");
        h2.getChildren().add(pad);
        fill.getChildren().add(h2);

        show1 = new ShowOneGUI();
        show1.addGUI("Manual", manual);
        show1.addGUI("Fill", fill);
                
        getChildren().addAll(h0, show1);
    }
    
    public void editNode(Node n) {
        try {
            Region r = (Region) n;
            if (x) {
                if (combo.getValue().equals("Manual")) {
                    r.setLayoutX(coord.getNum());
                } else {
                    double num = pad.getNum();
                    double tb = 0;
                    if (r.getPadding() != null) {
                        tb = r.getPadding().getTop();
                    }
                    r.setPadding(new Insets(tb, num, tb, num));
                    r.prefWidthProperty().bind(((Region)r.getParent()).widthProperty());
                }
            } else {
                if (combo.getValue().equals("Manual")) {
                    r.setLayoutY(coord.getNum());
                } else {
                    double num = pad.getNum();
                    double lr = 0;
                    if (r.getPadding() != null) {
                        lr = r.getPadding().getLeft();
                    }
                    r.setPadding(new Insets(num, lr, num, lr));
                    r.prefHeightProperty().bind(((Region)r.getParent()).heightProperty());
                }
            }      
        } catch (ClassCastException e) { 
            System.out.println("Node or it's parent isn't a Region. "
                    + "Only use RegionPlacementGUI in Region MetaGUIs.");
        }
    }
    
    public void setPos(double x, double y) {
        if (this.x) {
            coord.setText(Double.toString(x));
        } else {
            coord.setText(Double.toString(y));
        }
        combo.setValue("Manual");
        show1.showGUI("Manual");
    }
    
}
