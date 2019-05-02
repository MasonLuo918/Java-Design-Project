package Symbol.Symbol;

import Manager.SymbolManage;
import Symbol.Frame.ConnectCircleFrame;
import Symbol.Frame.OperationFrame;
import Symbol.GlobalConfig;
import Symbol.MShape;
import Symbol.SymbolBeans.SymbolBean;
import Util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class AbstractSymbol extends Pane implements MShape {

    /*
     * 生成一个唯一的uuid
     */
    private String uuid;

    /*
     * 设置形状的类型
     */
    private int symbolType;

    /*
     * beans 对象，用来保存
     */
    private SymbolBean symbolBean = new SymbolBean();

    /*
     * 操作框
     */
    private OperationFrame operationFrame = new OperationFrame(this);

    /*
     * 连接框
     */
    private ConnectCircleFrame connectCircleFrame = new ConnectCircleFrame(this);

    /*
     * 设置Symbol
     */
    private Shape myShape;

    /*
     * 设置Layout到鼠标的距离
     */
    private double initX;
    private double initY;

    /*
     * text在一行的情况下的高
     */
    private double textHeight = 0;

    /*
     *  鼠标点下时，记录鼠标在Parent的坐标
     */
    private Point2D cursorPoint;

    private TextArea textArea = new TextArea();

    private Text text = new Text("");

    public AbstractSymbol() {
        init();
    }

    public void init() {
        setUuid(UUID.getUUID()); //设置一个唯一的uuid
//        System.out.println(getUuid());
        initSymbolType();
        setPrefSize(GlobalConfig.PANE_WIDTH, GlobalConfig.PANE_HEIGHT);
        initTextHeight();
        text.setFont(GlobalConfig.FONT);
        textArea.setFont(GlobalConfig.FONT);
        text.textProperty().bindBidirectional(textArea.textProperty());
        setTextListenerEvent();
        initMyShape();
        initEvent();
        initOperationFrameEvent();
        getChildren().addAll(myShape, text);
    }

    @Override
    public void showOperationFrame() {
        operationFrame.showOperationFrame();
    }

    @Override
    public void hideOperationFrame() {
        operationFrame.hideOperationFrame();
    }

    @Override
    public void select(MouseEvent event) {
        SymbolManage manage = SymbolManage.getManage();
        if (event.isControlDown()) {
            manage.getSelectedShape().addMore(this);
        } else {
            manage.getSelectedShape().add(this);
        }
    }

    @Override
    public void initEvent() {
        prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() > GlobalConfig.MAX_PANE_HEIGHT || newValue.doubleValue() < GlobalConfig.MIN_PANE_HEIGHT) {
                    setPrefHeight(oldValue.doubleValue());
                }
                drawOperationFrame();
                drawConnectCircle();
                updateText();
                updateConnectCircleCoorInParent();
            }
        });

        prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() > GlobalConfig.MAX_PANE_WIDTH || newValue.doubleValue() < GlobalConfig.MIN_PANE_WIDTH) {
                    setPrefWidth(oldValue.doubleValue());
                }
                drawOperationFrame();
                drawConnectCircle();
                updateText();
                updateConnectCircleCoorInParent();
            }
        });
        PositionListener positionListener = new PositionListener();
        translateXProperty().addListener(positionListener);
        translateYProperty().addListener(positionListener);
        layoutXProperty().addListener(positionListener);
        layoutYProperty().addListener(positionListener);
        rotateProperty().addListener(positionListener);
    }

    @Override
    public void initOperationFrameEvent() {
        operationFrame.initOperationFrameEvent();
    }


    public void initTextHeight() {
        Text text = new Text("Hello World");
        text.setFont(GlobalConfig.FONT);
        setTextHeight(text.getBoundsInLocal().getHeight());
    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationFrame = false;
        for (Circle circle : operationFrame.getCircles()) {
            Bounds circleBoundsInLocal = circle.getBoundsInLocal();
            Bounds circleBoundsInScene = circle.localToScene(circleBoundsInLocal);
            if (circleBoundsInScene.contains(x, y)) {
                inOperationFrame = true;
                break;
            }
        }
        Bounds bounds = getBoundsInScene();
        if (bounds.contains(x, y) && !inOperationFrame) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断线是否进入范围内
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isLineInner(double x, double y) {
        double minX = getLayoutX() + getTranslateX() - GlobalConfig.ENTER_WIDTH;
        double minY = getLayoutY() + getTranslateY() - GlobalConfig.ENTER_WIDTH;
        double maxX = getLayoutX() + getTranslateX() + getPrefWidth() + GlobalConfig.ENTER_WIDTH;
        double maxY = getLayoutY() + getTranslateY() + getPrefHeight() + GlobalConfig.ENTER_WIDTH;
        if (minX < x && minY < y && maxX > x && maxY > y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Point2D sceneToParent(double x, double y) {
        Point2D toLocal = sceneToLocal(x, y);
        Point2D toParent = localToParent(toLocal);
        return toParent;
    }

    @Override
    public Bounds getBoundsInScene() {
        Bounds localBounds = getBoundsInLocal();
        Bounds sceneBounds = localToScene(localBounds);
        return sceneBounds;
    }

    @Override
    public boolean isLine() {
        return false;
    }

    @Override
    public void drawOperationFrame() {
        operationFrame.drawOperationFrame();
    }

    /**
     * 初始化一个Shape，将Shape的宽高绑定该pane的宽高，使得pane的宽高变化时，内容能跟着变换
     * 不同的Shape绑定不同的宽高
     * 最后调用setMyShape置入pane
     */
    public abstract void initMyShape();

    public Shape getMyShape() {
        return myShape;
    }

    public void setMyShape(Shape myShape) {
        this.myShape = myShape;
    }

    @Override
    public double getInitX() {
        return initX;
    }

    @Override
    public double getInitY() {
        return initY;
    }

    @Override
    public void setInitX(double initX) {
        this.initX = initX;
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

    public void showConnectCircle() {
        connectCircleFrame.showConnectCircle();
    }

    public void hideConnectCircle() {
        connectCircleFrame.hideConnectCircle();
    }

    public void drawConnectCircle() {
        connectCircleFrame.drawConnectCircle();
    }

    @Override
    public void showTextArea() throws JsonProcessingException {
        textArea.setPrefHeight(getPrefHeight());
        textArea.setPrefWidth(getPrefWidth());
        textArea.setWrapText(true);
        if (!getChildren().contains(textArea)) {
            getChildren().addAll(textArea);
        }
    }

    @Override
    public void hideTextArea() {
        if (getChildren().contains(textArea)) {
            getChildren().remove(textArea);
        }
    }

    @Override
    public void setTextListenerEvent() {
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (text.getBoundsInLocal().getHeight() > getPrefHeight() - getTextHeight()) {
                    text.setText(oldValue);
                }
                updateText();
            }
        });
    }

    /**
     * 更新文本的位置
     */
    @Override
    public void updateText() {
        text.setWrappingWidth(getPrefWidth());
        text.setTextAlignment(TextAlignment.CENTER);
        Bounds bounds = text.getBoundsInParent();
        double y = getPrefHeight() - bounds.getHeight();
        text.setY(y / 2 + getTextHeight());
    }

    public void setUserBean(SymbolBean beans){
        setLayoutX(beans.getLayoutX());
        setLayoutY(beans.getLayoutY());
        setPrefHeight(beans.getHeight());
        setPrefWidth(beans.getWidth());
        setTranslateX(beans.getTranslateX());
        setTranslateY(beans.getTranslateY());
        setRotate(beans.getRotate());
        setUuid(beans.getUuid());
        getText().setText(beans.getText());
        setSymbolType(beans.getSymbolType());
    }
    public abstract void initSymbolType();

    public void updateConnectCircleCoorInParent() {
        connectCircleFrame.updateConnectCircleCoorInParent();
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public double getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(double textHeight) {
        this.textHeight = textHeight;
    }

    public OperationFrame getOperationFrame() {
        return operationFrame;
    }

    public void setOperationFrame(OperationFrame operationFrame) {
        this.operationFrame = operationFrame;
    }

    public ConnectCircleFrame getConnectCircleFrame() {
        return connectCircleFrame;
    }

    public void setConnectCircleFrame(ConnectCircleFrame connectCircleFrame) {
        this.connectCircleFrame = connectCircleFrame;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(int symbolType) {
        this.symbolType = symbolType;
    }

    public SymbolBean getSymbolBean() {
        updateBeans();
        return symbolBean;
    }

    public void setSymbolBean(SymbolBean symbolBean) {
        this.symbolBean = symbolBean;
    }

    @Override
    public String toJson() throws JsonProcessingException {
        updateBeans();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getSymbolBean());
        return json;
    }
    public void updateBeans(){
        symbolBean.setLayoutX(getLayoutX());
        symbolBean.setLayoutY(getLayoutY());
        symbolBean.setHeight(getPrefHeight());
        symbolBean.setWidth(getPrefWidth());
        symbolBean.setSymbolType(getSymbolType());
        symbolBean.setText(getText().getText());
        symbolBean.setTranslateX(getTranslateX());
        symbolBean.setTranslateY(getTranslateY());
        symbolBean.setUuid(getUuid());
        symbolBean.setRotate(getRotate());
    }
    class PositionListener implements ChangeListener {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            AbstractSymbol.this.drawConnectCircle();
            AbstractSymbol.this.updateConnectCircleCoorInParent();
        }
    }
}
