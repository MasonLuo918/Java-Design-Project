package Symbol.Line;

import Symbol.GlobalConfig;
import Symbol.MShape;
import Symbol.SymbolBeans.ConnectBean;
import Symbol.SymbolBeans.LineBean;
import Util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

public abstract class AbstractLine extends Polyline implements MShape {

    private Circle[] circles = new Circle[2];

    private Pane drawPane;

    private int lineType;

    private double initX;

    private double initY;

    private double lastStartX;

    private double lastStartY;

    private double lastEndX;

    private double lastEndY;

    private double lastMiddleX;

    private double lastMiddleY;

    private Point2D cursorPoint;

    private double lineLength = 100;

    private TextField textField = new TextField();

    private Text text = new Text();

    private Connect startConnect = new Connect(this);

    private Connect endConnect = new Connect(this);

    private String uuid;

    private LineBean lineBean = new LineBean();

    private ConnectBean startBean = new ConnectBean();

    private ConnectBean endBean = new ConnectBean();

    SimpleDoubleProperty startX = new SimpleDoubleProperty();

    SimpleDoubleProperty startY = new SimpleDoubleProperty();

    SimpleDoubleProperty endX = new SimpleDoubleProperty();

    SimpleDoubleProperty endY = new SimpleDoubleProperty();

    SimpleDoubleProperty middleX = new SimpleDoubleProperty();

    SimpleDoubleProperty middleY = new SimpleDoubleProperty();

    public AbstractLine(){
        setUuid(UUID.getUUID());
        startConnect.setStartPoint(true);
        endConnect.setStartPoint(false);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
            circles[i].setCursor(Cursor.E_RESIZE);
        }
        startConnect.setLineXProperty(startX);
        startConnect.setLineYProperty(startY);
        endConnect.setLineXProperty(endX);
        endConnect.setLineYProperty(endY);
        textField.setPrefWidth(50);
        textField.layoutXProperty().bind(startX);
        textField.layoutYProperty().bind(startY);
        textField.setPrefColumnCount(2);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 2){
                    String value = newValue.substring(0,2);
                   textField.setText(value);
                }
            }
        });
    }

    public void initEvent() {
        startX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
                updateText();
            }
        });
        startY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                updateText();
                drawOperationFrame();
            }
        });
        endX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
                updateText();
            }
        });
        endY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
                updateText();
            }
        });

        middleX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
                updateText();
            }
        });
        middleY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
                updateText();
            }
        });


        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStroke(Color.RED);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStroke(Color.BLACK);
            }
        });

    }
    @Override
    public void showOperationFrame() {
        for(Circle circle:circles){
            getDrawPane().getChildren().add(circle);
        }
    }

    @Override
    public void hideOperationFrame() {
        for(Circle circle:circles){
            getDrawPane().getChildren().remove(circle);
        }
    }

    @Override
    public void drawOperationFrame() {
        circles[0].setCenterX(startX.get());
        circles[0].setCenterY(startY.get());
        circles[1].setCenterX(endX.get());
        circles[1].setCenterY(endY.get());
    }

    public void setUserBean(LineBean bean){
        setLineType(bean.getLineType());
        getText().setText(bean.getText());
        setUuid(bean.getUuid());
        setStartX(bean.getStartX());
        setStartY(bean.getStartY());
        setMiddleY(bean.getMiddleY());
        setMiddleX(bean.getMiddleX());
        setEndX(bean.getEndX());
        setEndY(bean.getEndY());
        if(!drawPane.getChildren().contains(text)){
            drawPane.getChildren().add(text);
        }
    }

    public Pane getDrawPane() {
        return drawPane;
    }

    public void setDrawPane(Pane drawPane) {
        this.drawPane = drawPane;
    }


    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }


    @Override
    public boolean isLine() {
        return true;
    }

    public double getLineLength() {
        return lineLength;
    }

    public void setLineLength(double lineLength) {
        this.lineLength = lineLength;
    }

    public abstract void updateLine();

    @Override
    public double getInitX() {
        return initX;
    }

    @Override
    public void setInitX(double initX) {
        this.initX = initX;
    }

    @Override
    public double getInitY() {
        return initY;
    }

    @Override
    public void setInitY(double initY) {
        this.initY = initY;
    }

    @Override
    public Point2D getCursorPoint() {
        return cursorPoint;
    }

    @Override
    public void setCursorPoint(Point2D cursorPoint) {
        this.cursorPoint = cursorPoint;
    }

    @Override
    public Bounds getBoundsInScene() {
        return localToScene(getBoundsInLocal());
    }

    @Override
    public Point2D sceneToParent(double x, double y) {
        Point2D toLocal = sceneToLocal(x,y);
        return toLocal;
    }

    @Override
    public void showTextArea() {
        if(!drawPane.getChildren().contains(text)){
            drawPane.getChildren().add(text);
        }
        textField.setText(text.getText());
        drawPane.getChildren().add(textField);
    }

    @Override
    public void hideTextArea() {
        text.setText(textField.getText());
        drawPane.getChildren().remove(textField);
    }

    @Override
    public void setTextListenerEvent() {

    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationCircle = false;
        for(Circle circle: circles){
            Bounds toLocal = circle.getBoundsInLocal();
            Bounds toScene = circle.localToScene(toLocal);
            if(toScene.contains(x, y)){
                inOperationCircle = true;
                break;
            }
        }
        if(!inOperationCircle){
            return getBoundsInScene().contains(x,y);
        }else{
            return false;
        }
    }

    public double getStartX() {
        return startX.get();
    }

    public SimpleDoubleProperty startXProperty() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX.set(startX);
    }

    public double getStartY() {
        return startY.get();
    }

    public SimpleDoubleProperty startYProperty() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY.set(startY);
    }

    public double getEndX() {
        return endX.get();
    }

    public SimpleDoubleProperty endXProperty() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX.set(endX);
    }

    public double getEndY() {
        return endY.get();
    }

    public SimpleDoubleProperty endYProperty() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY.set(endY);
    }

    public double getMiddleX() {
        return middleX.get();
    }

    public SimpleDoubleProperty middleXProperty() {
        return middleX;
    }

    public void setMiddleX(double middleX) {
        this.middleX.set(middleX);
    }

    public double getMiddleY() {
        return middleY.get();
    }

    public SimpleDoubleProperty middleYProperty() {
        return middleY;
    }

    public void setMiddleY(double middleY) {
        this.middleY.set(middleY);
    }

    public double getLastStartX() {
        return lastStartX;
    }

    public void setLastStartX(double lastStartX) {
        this.lastStartX = lastStartX;
    }

    public double getLastStartY() {
        return lastStartY;
    }

    public void setLastStartY(double lastStartY) {
        this.lastStartY = lastStartY;
    }

    public double getLastEndX() {
        return lastEndX;
    }

    public void setLastEndX(double lastEndX) {
        this.lastEndX = lastEndX;
    }

    public double getLastEndY() {
        return lastEndY;
    }

    public void setLastEndY(double lastEndY) {
        this.lastEndY = lastEndY;
    }

    public Connect getStartConnect() {
        return startConnect;
    }

    public void setStartConnect(Connect startConnect) {
        this.startConnect = startConnect;
    }

    public Connect getEndConnect() {
        return endConnect;
    }

    public void setEndConnect(Connect endConnect) {
        this.endConnect = endConnect;
    }

    public Circle[] getCircles() {
        return circles;
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LineBean getLineBean() {
        updateBean();
        return lineBean;
    }

    public void setLineBean(LineBean lineBean) {
        updateBean();
        this.lineBean = lineBean;
    }

    public void updateBean(){
        lineBean.setStartX(getStartX());
        lineBean.setStartY(getStartY());
        lineBean.setMiddleX(getMiddleX());
        lineBean.setMiddleY(getMiddleY());
        lineBean.setEndX(getEndX());
        lineBean.setEndY(getEndY());
        lineBean.setLineType(getLineType());
        lineBean.setText(getText().getText());
        lineBean.setUuid(getUuid());
    }

    @Override
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getLineBean());
        return json;
    }

    public void updateConnectBean(){
        startBean.setBind(startConnect.isBind());
        startBean.setCircleIndex(startConnect.getCircleIndex());
        startBean.setLineUUID(startConnect.getLine().getUuid());
        startBean.setLineX(startConnect.getLineX());
        startBean.setLineY(startConnect.getLineY());
        startBean.setSymbolUUID(startConnect.getSymbol().getUuid());
        startBean.setStartPoint(startConnect.isStartPoint());


        endBean.setBind(endConnect.isBind());
        endBean.setCircleIndex(endConnect.getCircleIndex());
        endBean.setLineUUID(endConnect.getLine().getUuid());
        endBean.setLineX(endConnect.getLineX());
        endBean.setLineY(endConnect.getLineY());
        endBean.setSymbolUUID(endConnect.getSymbol().getUuid());
        endBean.setStartPoint(endConnect.isStartPoint());
    }


    public void setConnectUserBean(ConnectBean bean){
        if(bean.isStartPoint()){
            startConnect.setLineXProperty(startX);
            startConnect.setLineYProperty(startY);
            startConnect.setCircleIndex(bean.getCircleIndex());
        }
    }
    public ConnectBean getStartBean() {
        updateConnectBean();
        return startBean;
    }

    public void setStartBean(ConnectBean startBean) {
        this.startBean = startBean;
    }

    public ConnectBean getEndBean() {
        updateConnectBean();
        return endBean;
    }

    public void setEndBean(ConnectBean endBean) {
        this.endBean = endBean;
    }

    public double getLastMiddleX() {
        return lastMiddleX;
    }

    public void setLastMiddleX(double lastMiddleX) {
        this.lastMiddleX = lastMiddleX;
    }

    public double getLastMiddleY() {
        return lastMiddleY;
    }

    public void setLastMiddleY(double lastMiddleY) {
        this.lastMiddleY = lastMiddleY;
    }
}
