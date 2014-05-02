/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;


import bessernote.canvases.DrawCanvas;
import bessernote.nodemaker.DrawingMenu;
import bessernote.nodemaker.NodeGUI;
import bessernote.nodemaker.dockingMenu;
import bessernote.nodemaker.placement.DraggingUtil;
import com.sun.javafx.runtime.VersionInfo;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import saving.Loader;
import saving.Saver;
import undo.AddChange;
import undo.BUndoManager;
import undo.DeleteChange;

/**
 *
 * @author avarga
 */
public class BesserNote extends Application {

    private Stage stage;
    private Scene scene;
    private BorderPane root;
    private MenuBar menuBar;
    private StackPane stackPane;
    private Pane sheet;
    private Pane above;
    
    private DashedBox dragBox;
    
    private Map<Node, DashedBox> selectBoxes;
    private Node superSelected;
    private int superIndex;
    //private DashedBox superBox;
    private List<Node> superClicked;
    
    private Map<Node, Double> dragOffsetX;
    private Map<Node, Double> dragOffsetY;
    private HashSet<Parent> toResize;
    private boolean dragging = false;
    private boolean resizing = false;
    
    private Popup popup;
    private NodeGUI nodeGUI;
    private Window primaryStage;
    private Desktop desktop = Desktop.getDesktop();
    private FileChooser fileChooser = new FileChooser();
    //private GraphicsContext gc;
    
    private dockingMenu dockingMenu;
    
    private double startOutlineX;
    private double startOutlineY;
    private Parent target;
    
    ///Drawing Canvases
    private DrawCanvas drawCanvas = new DrawCanvas(this, 2000, 2000);
    //private Canvas circleCanvas = new Canvas();
    private String currentMode;  
    
    private BUndoManager undoManager;
    
    //

    @Override
    public void start(final Stage stage) throws IOException {
        this.stage = stage;
        System.out.println("JavaFX Version: "+VersionInfo.getRuntimeVersion());// VersionInfo.getRuntimeVersion())‌​;

        root = new BorderPane();
        scene = new Scene(root, 640, 480, Color.BLACK);
        
        stackPane = new StackPane();
        root.setCenter(stackPane);

        sheet = new Pane();        
        sheet.setStyle("-fx-background-color: #000000");
        stackPane.getChildren().add(sheet);
        target = sheet;
        
        above = new Pane();
        above.setMouseTransparent(true);    // doesn't block clicks
        stackPane.getChildren().add(above);
                
        dragBox = new DashedBox(new String[]{"red", "yellow", "green"}, 10, 3);
        dragBox.setVisible(false);
        above.getChildren().add(dragBox);
        
        stackPane.getChildren().add(drawCanvas);
        drawCanvas.toBack();
        
        //// SELECTION ////
        
        selectBoxes = new HashMap<>();


        undoManager = new BUndoManager();

        
        addSheetListeners();

        //// NODE MAKER ////


        popup = new Popup();
        nodeGUI = new NodeGUI(5);
        popup.getContent().addAll(nodeGUI);
        popup.setAutoFix(false);
        popup.setHideOnEscape(true);
        
        dockingMenu = new dockingMenu(nodeGUI);
        root.setLeft(dockingMenu);
        
        popup.addEventFilter(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    if (e.getCode().equals(KeyCode.ENTER)) {
                        createNode();
                        dragBox.setVisible(false);
                    }
                };
            }
        );
        
        nodeGUI.createButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                createNode();
                dragBox.setVisible(false);
            }
        });
        
        nodeGUI.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                popup.hide();
                dragBox.setVisible(false);
            }
        });
        
        
        
        //// PAINTING MENU ////
        
        
        


        //// MENU BAR ////

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BSSR files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New...");
        menuItemNew.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(final ActionEvent e){
                Application app2 = new BesserNote();
                Stage anotherStage = new Stage();
                try {
                    app2.start(anotherStage);
                } catch (Exception ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        MenuItem menuItemOpen = new MenuItem("Open...");
        menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        try {
                            openFile(file);
                        } catch (IOException ex) {
                            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        });
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t){
                try {
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menuFile.getItems().addAll(
                menuItemNew,
                menuItemOpen,
                menuItemSave,
                new SeparatorMenuItem(),
                menuItemExit);

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        
        MenuItem menuItemUndo = new MenuItem("Undo");
        menuItemUndo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                undoManager.undo();
            }
        });
        menuEdit.getItems().addAll(menuItemUndo);
        
        MenuItem menuItemRedo = new MenuItem("Redo");
        menuItemRedo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                undoManager.redo();
            }
        });
        menuEdit.getItems().addAll(menuItemRedo);
        
        MenuItem menuItemAdd = new MenuItem("Add");
        menuItemAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                popup.show(stage);
            }
        });
        menuEdit.getItems().addAll(menuItemAdd);
        
        MenuItem menuItemDraw = new MenuItem("Draw");
        menuItemDraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t){
                Popup drawingMenu = new Popup();
                try {
                    drawingMenu.getContent().addAll(new DrawingMenu(BesserNote.this));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawingMenu.show(stage, scene.getX(), scene.getY());
            }
        });
        menuEdit.getItems().addAll(menuItemDraw);

        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.setTop(menuBar);

        ////  ////
        
        stage.setMaximized(true);
        
        stage.setTitle("BesserNote"); 
        stage.setScene(scene); 
        stage.show(); 
        //System.out.println(sheet.getWidth());
    }
    
//    public void begin(final Stage stage) {
//        
//    }
    
    public void addSheetListeners(){
        
                
        scene.addEventFilter(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.TAB) {
                        if (superClicked.size() > 0) {
                            if (event.isShiftDown()) {
                                selectPrev();
                            } else {
                                selectNext();
                            }
                        }
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        cancelSuperClick();
                        unselectAll();
                        dragBox.setVisible(false);
                    }
                    else if (event.getCode() == KeyCode.BACK_SPACE){
                        for(Map.Entry<Node, DashedBox> entry: selectBoxes.entrySet()){
                            Node deleteMe = entry.getKey();
                            if (deleteMe != sheet) {
                                Pane parent = (Pane) deleteMe.getParent();
                                parent.getChildren().remove(deleteMe);
                                undoManager.addChange(new DeleteChange(deleteMe, parent));
                            }
                        }
                        
                        unselectAll();
                    }
                }
            }
        );
        
         sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (!e.isAltDown()) {
                            if (!e.isShiftDown()) {
                                unselectAll();
                            }
                            cancelSuperClick();
                            superClicked = superClick(e.getX(), e.getY());
                            if (superClicked.size() > 0) {
                                flipSelection(0);
                            }
                        }
                    }
                    
                };
            }
        );
                
        //// DRAG ////
        
        sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY && e.isAltDown()) {
                        if (superSelected != null) {
                            dragOffsetX = new HashMap<>();
                            dragOffsetY = new HashMap<>();
                           toResize = new HashSet<Parent>();
                            for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                                Node n = entry.getKey();
//                                checks if the bottom right corner was picked and adds toResize if selected
                               if( DraggingUtil.toScaleArea(n, e.getX(), e.getY()) && n instanceof Parent){
                                toResize.add((Parent)n);
                               }
                                
                                
                                Point2D local = sheetToLocal(
                                    n.getParent(), e.getX(), e.getY());
                                dragOffsetX.put(n, local.getX()-n.getLayoutX());
                                dragOffsetY.put(n, local.getY()-n.getLayoutY());
                            }
                            dragging = true;
                            if(!toResize.isEmpty()){
                                //out("RESIZING BITCHES");
                                resizing = true;
                            }
                            else{
                                //out("NOT RESIZING");
                            }
                        }
                    }
                };
            }
        );
        
        sheet.addEventFilter(MouseEvent.MOUSE_DRAGGED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY && dragging) {
                        if (superSelected != null) {
                            if(!resizing){
                                
                            
                            for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                                Node n = entry.getKey();
                                Point2D local = sheetToLocal(
                                    n.getParent(), e.getX(), e.getY());
                                try {   // can't set if bound
                                    double offset = dragOffsetX.get(n);
                                    n.setLayoutX(local.getX()-offset);
                                } catch (Exception ex) {}
                                try {   // can't set if bound
                                    double offset = dragOffsetY.get(n);
                                    n.setLayoutY(local.getY()-offset);
                                } catch (Exception ex) {}
                                showSelection(n);
                            }
                            }
                            else{//resizing
                                for(Parent n: toResize){
//                                    double offsetX = dragOffsetX.get(n);
//                                    double offsetY = dragOffsetY.get(n);
                                    double offsetX = e.getX() - n.localToScene(Point2D.ZERO).getX();
                                    double offsetY = e.getY() - n.localToScene(Point2D.ZERO).getY();
                                    
//                                    double currWidth = n.getLayoutBounds().getWidth();
//                                    double currHeight = n.getLayoutBounds().getHeight();
//                                    double currWidth = n.getBoundsInParent().getWidth();
//                                    double currHeight = n.getBoundsInParent().getHeight();
////                                    double currWidth = n.getBoundsInLocal().getWidth();
//                                    double currHeight = n.getBoundsInLocal().getHeight();
//                                    double offsetToCurrRatioX = offsetX / currWidth;
//                                    double offsetToCurrRatioY = offsetY / currHeight;
//                                    out("offsetX: %f",offsetX);
//                                    out("offsetY: %f",offsetY);
//                                    out("currWidth: %f",currWidth);
//                                    out("currHeight: %f",currHeight);
//                                    out("ratioX: %f",offsetToCurrRatioX);
//                                    out("ratioY: %f\n\n",offsetToCurrRatioY);
////                                    out("is resizable %b",n.isResizable());
//                                    KEEP TRACK OF THE ORIGINAL WIDTH NITWIT
//                                    n.setScaleX(n.getScaleX() * offsetToCurrRatioX);
//                                    n.setScaleY(n.getScaleY() * offsetToCurrRatioY);
//                                    DoubleProperty width = new SimpleDoubleProperty(0);
//                                    width.setValue(offsetX);
//                                    width.getValue();
//                                    DoubleProperty height = new SimpleDoubleProperty(offsetY);
//                                    height.setValue(offsetY);
//                                    height.getValue();
                                 if(n instanceof Region){
                                     Region r = (Region) n;
                                     r.setPrefHeight(offsetY);
                                     r.setPrefWidth(offsetX);
                                     r.autosize();
                                 }
                                 else if(n instanceof Group){
                                     Group g = (Group) n;
                                     g.prefWidth(offsetX);
                                     g.prefHeight(offsetY);
                                     g.autoSizeChildrenProperty();
                                     g.autosize();
                                     
                                 }
//                                 else if(n instanceof Control){
//                                     Control c = (Control) c;
//                                     
//                                 }
                                    
//                                    n.resize(offsetX, offsetY);
                                }
                            }
                        }
                    }
                };
            }
        );
        
        sheet.addEventFilter(MouseEvent.MOUSE_RELEASED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        dragging = false;
                        resizing = false;
                    }
                };
            }
        );

        //// RIGHT CLICK ////
                
        sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        startOutlineX = e.getX();
                        startOutlineY = e.getY();
                        
                        boolean clickedSelected = false;
                        for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                            Node n = entry.getKey();
                            Point2D local = sheetToLocal(n, e.getX(), e.getY());
                            if (n instanceof Region &&
                                    local.getX() >= 0 && 
                                    local.getY() >= 0 &&
                                    local.getX() <= n.getBoundsInLocal().getWidth() &&
                                    local.getY() <= n.getBoundsInLocal().getHeight()) {
                                clickedSelected = true;
                                target = (Parent) n;
                                break;
                            }
                        }
                        
                        if (clickedSelected) {
                            cancelSuperClick();
                            unselectAll();
                            superClicked = new ArrayList<>();
                            superClicked.add(target);
                            flipSelection(0);
                        } else {
                            cancelSuperClick();
                            unselectAll();
                            superClicked = superClick(e.getX(), e.getY());
                            if (superClicked.size() > 0 && superClicked.get(0) instanceof Pane) {
                                flipSelection(0);
                                target = (Parent) superClicked.get(0);
                            } else {
                                unselectAll();
                                target = sheet;
                            }
                        }
                    }
                };
            }
        );
        sheet.addEventFilter(MouseEvent.MOUSE_DRAGGED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        double dx = startOutlineX-e.getX();
                        double dy = startOutlineY-e.getY();
                        if (dx*dx+dy*dy >= 25) {
                            dragBox.setLayoutX(Math.min(startOutlineX, e.getX()));
                            dragBox.setLayoutY(Math.min(startOutlineY, e.getY()));
                            dragBox.setPrefSize(Math.abs(dx), Math.abs(dy));
                            dragBox.setVisible(true);
                        } else {
                            dragBox.setVisible(false);
                        }
                    }
                };
            }
        );
        sheet.addEventFilter(MouseEvent.MOUSE_RELEASED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        double dx = startOutlineX-e.getX();
                        double dy = startOutlineY-e.getY();
                        if (dx*dx+dy*dy >= 25) {
                            Point2D local = sheetToLocal(target,
                                    Math.min(startOutlineX, e.getX()), 
                                    Math.min(startOutlineY, e.getY()));
                            Point2D local2 = sheetToLocal(target,
                                    Math.max(startOutlineX, e.getX()), 
                                    Math.max(startOutlineY, e.getY()));
                            nodeGUI.setPos(local.getX(), local.getY());
                            nodeGUI.setSize(local2.getX()-local.getX(), 
                                    local2.getY()-local.getY());
                        } else {
                            Point2D local = sheetToLocal(target,startOutlineX, startOutlineY);
                            nodeGUI.setPos(local.getX(), local.getY());
                        }
                        //createNode();
                        //TODO: MAKE THE POPUP NOT SHOW AND JUST INSERT MANUALLY
                        createNode();
                        //popup.show(stage);
                    }
                };
            }
        );     
    }

        
//    publienode void changeRoot(Pane newRoot){
//        stackPane.getChildren().remove(sheet);
//        sheet = newRoot;
//        stackPane.getChildren().add(sheet);
//        target = sheet;
//        addSheetListeners();
//        sheet.toFront();
//
//        //superClicked.clear();
//    }
    
    public Point2D sheetToLocal(Node n, double sheetX, double sheetY) {
        Point2D pointInScene = sheet.localToScene(sheetX, sheetY);
        return n.sceneToLocal(pointInScene);
    }
    
    public void flipSelection(int i) {
//        System.out.println(i);
        superIndex = i;
        superSelected = superClicked.get(superIndex);
        
        if (selectBoxes.containsKey(superSelected)) {
            DashedBox dashed = selectBoxes.get(superSelected);
            above.getChildren().remove(dashed);
            selectBoxes.remove(superSelected);
        } else {
            DashedBox dashed = new DashedBox(new String[]{"black", "gray", "white"}, 10, 3);
            above.getChildren().add(dashed);
            selectBoxes.put(superSelected, dashed);
            showSelection(superSelected);
        }
    }
    
    public void showSelection(Node n) {
        DashedBox dashed = selectBoxes.get(n);
        Bounds bounds = n.localToScene(n.getBoundsInLocal());
        bounds = sheet.sceneToLocal(bounds);
        dashed.setPrefSize(bounds.getWidth(), bounds.getHeight());
        dashed.setLayoutX(bounds.getMinX());
        dashed.setLayoutY(bounds.getMinY());
    }
    
    public void selectPrev() {
        flipSelection(superIndex);
        flipSelection((superClicked.size()+superIndex-1) % superClicked.size());
    }
    
    public void selectNext() {
        flipSelection(superIndex);
        flipSelection((superIndex+1) % superClicked.size());
    }
    
    public void cancelSuperClick() {
        superClicked = null;
        superIndex = -1;
        superSelected = null;
    }
    
    public void unselectAll() {
        for (Map.Entry mapEntry : selectBoxes.entrySet()) {
            above.getChildren().remove(mapEntry.getValue());
        }
        selectBoxes = new HashMap<>();
    }
    
    public List<Node> superClick(double x, double y) {
        List<Node> ret = new ArrayList<Node>();
        superClickHelper(x, y, sheet, ret);
        return ret;
    }
    
    private void superClickHelper(double x, double y, Node n, List<Node> list) {
        // check if inside  
        if (x >= 0 &&
                y >= 0 &&
                x <= n.getBoundsInLocal().getWidth() &&
                y <= n.getBoundsInLocal().getHeight()) {
            list.add(0, n);
            if (n instanceof Parent) {
                Parent p = (Parent) n;
                List<Node> children;
                if (p instanceof ChildSpecifier) {
                    children = ((ChildSpecifier) p).specifyChildren();
                } else {
                    children = p.getChildrenUnmodifiable();
                }
                for (Node child : children) {
                    Point2D local = child.parentToLocal(x, y);  
                    superClickHelper(local.getX(), local.getY(), child, list);
                }
            }
        }
    }
    
    /*
    public void replaceSheet(Pane newSheet) {
        sheet = newSheet;
        stackPane.getChildren().clear();
        stackPane.getChildren().add(sheet);
        sheet.toFront();
    }
    */
    
    public void replaceSheet(Pane newRoot){
        stackPane.getChildren().remove(sheet);
        selectBoxes.remove(sheet);
        superSelected = null;
        sheet = newRoot;
        addSheetListeners();
        stackPane.getChildren().add(sheet);
        above.toFront();
        target = sheet;
        //superClicked.clear();
    }
    
    private void openFile(File file) throws IOException {
        Loader load = new Loader(file);
        //changeRoot(load.getSheet());

        load.loadNew(undoManager);
        System.out.println(load.getSheet(undoManager).getChildren());

//        load.loadNew();
//        for (Node child: load.getSheet().getChildren()){
//            if(child instanceof Pane){
//                Pane printMe = (Pane) child;
//                //System.out.print(printMe.getPrefWidth() + " " + printMe.getPrefHeight() + " " + child.getLayoutX() + " " + child.getLayoutY());
//            }
//            //System.out.println(" " + child);
//        }
        //System.out.println(load.getSheet().getChildren());
        
        Application app2 = new BesserNote();
        Stage anotherStage = new Stage();
        try {
            app2.start(anotherStage);
            ((BesserNote) app2).replaceSheet(load.getSheet(undoManager));
        } catch (Exception ex) {
            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //changeRoot(load.getSheet());
    }
    
    private void saveFile() throws IOException{
              //Show save file dialog
              File file = fileChooser.showSaveDialog(primaryStage);
              if(file != null){
                  Saver save = new Saver(file);
                  save.save(sheet);
              }
          }
      
        
    public void createNode() {
        /*
        Node myNode = null; //dockingMenu.createNode();
        System.out.println(myNode);
        if (myNode != null){
            nodeGUI.editNode(myNode);
            sheet.getChildren().add(myNode);
        }
        */

        for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
            Node n = entry.getKey();
            if (n instanceof ChildSpecifier) {
                n = ((ChildSpecifier) n).specifySelf();
            }
            if (n instanceof Pane) {
                Node newNode = nodeGUI.getNode(undoManager);

        //        DraggingUtil.enableResizeDrag(newNode);
                if (newNode != null) {
                    ((Pane) n).getChildren().add(newNode);
                    nodeGUI.editNode(newNode);
                    undoManager.trackMyPlacementChanges((Region) newNode);
                    undoManager.addChange(new AddChange(newNode, (Pane) n));
                }
                        
            }
        }
        popup.hide();
                      
    }
      
    public static void main(String[] args) {
        launch(args);
    }
    
    public Node getCurrentFocus(){
        return this.scene.getFocusOwner();
    }
    
    
    public void drawOn(){  
        currentMode = "draw";
        drawCanvas.setVisible(true);
        drawCanvas.addListeners();
        drawCanvas.toFront();
    }
    
    public void drawOff(){
       drawCanvas.removeListeners();
    }
    
    public void circleOn(){
        //Bring to front
        //circleCanvas.setVisible(true);
    }
    
    public void circleOff(){
        //circleCanvas.setVisible(false);
    }
    
    public void setupDrawListeners(){
        
    }
    
    public void setupCircleListeners(){
        
    }
    
    public void undoDrawing(){
        drawCanvas.undoDrawing();
    }
    
    public void addDoodle(Path path){
        path.setFill(Color.WHITE);
        path.setStroke(Color.WHITE);
        path.setStrokeWidth(5);
        sheet.getChildren().add(path);
        System.out.println(sheet.getChildren());
        System.out.println(path.getElements());
    }
    
    public void strokeColor(Color c){
        switch(currentMode){
            case "draw":
                drawCanvas.changeColor(c);
            break;
        }
    }
    
    public static void out(Object o){
        System.out.println(o);
    }
    
    public static void out(String s, Object... o){
        System.out.println(String.format(s, o));
    }

    
}
