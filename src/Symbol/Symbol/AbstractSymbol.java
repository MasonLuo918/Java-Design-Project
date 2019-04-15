package Symbol.Symbol;

import Manager.SymbolManage;
import Symbol.GlobalConfig;
import Symbol.MShape;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import MathUtil.MathUtil;

import java.awt.*;


public abstract class AbstractSymbol extends Pane implements MShape {


    /*
     * circles[0]为旋转该图形的circle,其他依次为左上角顺时针数过去
     */
    private Circle[] circles = new Circle[9];

    /*
     * 当连接线接近的时候,就会显示这几个circle，用以连接
     */
    private Circle[] connectCircle = new Circle[4];

    /*
     * lines[0]为连接旋转圆的线,其他依次为上右下左四条操作线
     */
    private Line[] lines = new Line[5];


    /*
     * 设置Symbol
     */
    private Shape myShape;

    /**
     * 设置Layout到鼠标的距离
     */
    private double initX;

    private double initY;

    private Point2D cursorPoint;

    public AbstractSymbol(){
        init();
    }


    public void init(){
        setPrefSize(GlobalConfig.PANE_WIDTH,GlobalConfig.PANE_HEGHT);
        /*
         * 给操作框对象分配内存空间
         */
        for(int i = 0; i < circles.length; i++){
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
            circles[i].setCursor(Cursor.E_RESIZE);
        }
        //更改旋转圆手势
        circles[0].setCursor(Cursor.HAND);
        for(int i = 0; i < connectCircle.length; i++){
            connectCircle[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            connectCircle[i].setFill(GlobalConfig.CONNECT_CIRCLE_COLOR);
        }
        for(int i = 0; i < lines.length; i++){
            lines[i] = new Line();
            lines[i].setStroke(GlobalConfig.OPERATION_FRAME_LINE_COLOR);
            lines[i].getStrokeDashArray().add(3.0);
        }
        initMyShape();
        initEvent();
        initOperationFrameEvent();
        getChildren().addAll(myShape);
    }



    @Override
    public void showOperationFrame() {
        hideOperationFrame();
        drawOperationFrame();
        for(Circle circle:circles){
            getChildren().add(circle);
        }

        for(Line line:lines){
            getChildren().add(line);
        }
    }

    @Override
    public void hideOperationFrame() {
        for(Circle circle:circles){
            getChildren().remove(circle);
        }

        for(Line line:lines){
            getChildren().remove(line);
        }
    }

    @Override
    public void drawOperationFrame() {
        circles[0].setCenterX(getPrefWidth() / 2);
        circles[0].setCenterY(-GlobalConfig.OFFSET);

        circles[1].setCenterX(0);
        circles[1].setCenterY(0);

        circles[2].setCenterX(getPrefWidth() / 2);
        circles[2].setCenterY(0);

        circles[3].setCenterX(getPrefWidth());
        circles[3].setCenterY(0);

        circles[4].setCenterX(getPrefWidth());
        circles[4].setCenterY(getPrefHeight() / 2);

        circles[5].setCenterX(getPrefWidth());
        circles[5].setCenterY(getPrefHeight());

        circles[6].setCenterX(getPrefWidth() / 2);
        circles[6].setCenterY(getPrefHeight());

        circles[7].setCenterX(0);
        circles[7].setCenterY(getPrefHeight());

        circles[8].setCenterX(0);
        circles[8].setCenterY(getPrefHeight() / 2);

        lines[0].setStartX(getPrefWidth() / 2);
        lines[0].setStartY(GlobalConfig.CIRCLE_RADIUS - GlobalConfig.OFFSET);
        lines[0].setEndX(getPrefWidth() / 2);
        lines[0].setEndY(-GlobalConfig.CIRCLE_RADIUS);

        lines[1].setStartX(GlobalConfig.CIRCLE_RADIUS);
        lines[1].setStartY(0);
        lines[1].setEndX(getPrefWidth() - GlobalConfig.CIRCLE_RADIUS);
        lines[1].setEndY(0);

        lines[2].setStartX(getPrefWidth());
        lines[2].setStartY(0);
        lines[2].setEndX(getPrefWidth());
        lines[2].setEndY(getPrefHeight() - GlobalConfig.CIRCLE_RADIUS);

        lines[3].setStartX(getPrefWidth() - GlobalConfig.CIRCLE_RADIUS);
        lines[3].setStartY(getPrefHeight());
        lines[3].setEndX(GlobalConfig.CIRCLE_RADIUS);
        lines[3].setEndY(getPrefHeight());

        lines[4].setStartX(0);
        lines[4].setStartY(getPrefHeight() - GlobalConfig.CIRCLE_RADIUS);
        lines[4].setEndX(0);
        lines[4].setEndY(0);
    }

    @Override
    public void select(MouseEvent event) {
        SymbolManage manage = SymbolManage.getManage();
        if(event.isControlDown()){
            manage.addMore(this);
        }else{
            manage.add(this);
        }
    }

    @Override
    public void initEvent() {
        prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.doubleValue() > GlobalConfig.MAX_PANE_HEIGHT || newValue.doubleValue() < GlobalConfig.MIN_PANE_HEIGHT){
                    setPrefHeight(oldValue.doubleValue());
                }
                drawOperationFrame();
                drawConnectCircle();
            }
        });

        prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.doubleValue() > GlobalConfig.MAX_PANE_WIDTH || newValue.doubleValue() < GlobalConfig.MIN_PANE_WIDTH){
                    setPrefWidth(oldValue.doubleValue());
                }
                drawOperationFrame();
                drawConnectCircle();
            }
        });
    }

    @Override
    public void initOperationFrameEvent() {

        for(Circle circle:circles){

            /**
             * 当监测到按下操作圆的时候，将OperationDrag设置为true，以便禁止拖拽
             */
            circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    SymbolManage symbolManage = SymbolManage.getManage();
                    symbolManage.setOpertionDrag(true);
                    setInitX(getTranslateX());
                    setInitY(getTranslateY());
                    setCursorPoint(sceneToParent(event.getSceneX(), event.getSceneY()));
                }
            });

            circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    SymbolManage symbolManage = SymbolManage.getManage();
                    symbolManage.setOpertionDrag(false);
                }
            });
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(Color.RED);
                }
            });
            circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
                }
            });
        }
        circles[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D eventCoorRelativeToParent = sceneToParent(event.getSceneX(), event.getSceneY());
                double shapeCenterX = getPrefWidth() / 2 + getLayoutX() + getTranslateX();
                double shapeCenterY = getPrefHeight() / 2 + getLayoutY() + getTranslateY();
                System.out.println(getLayoutX() + " " + getLayoutY());
                double ax = 0;
                double ay = shapeCenterY - getLayoutY();
                double bx = eventCoorRelativeToParent.getX() - shapeCenterX;
                double by = shapeCenterY - eventCoorRelativeToParent.getY();
                double a = shapeCenterY - getLayoutY();
                double b = MathUtil.distance(shapeCenterX, shapeCenterY, eventCoorRelativeToParent.getX(),
                        eventCoorRelativeToParent.getY());
                double arc = Math.acos((ax * bx + ay * by) / (a * b));
                double angle = MathUtil.arcChangeAngle(arc);
                if (bx < 0) {
                    angle *= -1;
                }
                setRotate(angle);

            }
        });

        circles[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                double dragX = mousePointInParent.getX() - cursorPoint.getX();
                double dragY = mousePointInParent.getY() - cursorPoint.getY();
                double newPositionX = (initX + dragX) ;
                double newPositionY = (initY + dragY);
                double addWidth = (getTranslateX() - newPositionX);
                double addHeight = (getTranslateY() - newPositionY);
                setPrefWidth(getPrefWidth() + addWidth);
                setPrefHeight(getPrefHeight() + addHeight);
                setTranslateX(newPositionX);
                setTranslateY(newPositionY);
            }
        });

    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationFrame = false;
        for(Circle circle:circles){
            Bounds circleBoundsInLocal = circle.getBoundsInLocal();
            Bounds circleBoundsInScene = circle.localToScene(circleBoundsInLocal);
            if(circleBoundsInScene.contains(x, y)){
                inOperationFrame = true;
                break;
            }
        }
        Bounds bounds = getBoundsInScene();
        if(bounds.contains(x,y) && !inOperationFrame){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断线是否进入范围内
     * @param x
     * @param y
     * @return
     */
    public boolean isLineInner(double x, double y) {
        double minX= getLayoutX() + getTranslateX() - GlobalConfig.ENTER_WIDTH;
        double minY = getLayoutY() + getTranslateY() - GlobalConfig.ENTER_WIDTH;
        double maxX = getLayoutX() + getTranslateX() + getPrefWidth() + GlobalConfig.ENTER_WIDTH;
        double maxY = getLayoutY() + getTranslateY()  + getPrefHeight() + GlobalConfig.ENTER_WIDTH;
        if(minX < x && minY < y && maxX > x && maxY > y){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public Point2D sceneToParent(double x,double y){
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
    public boolean isLine(){
        return false;
    }

    /**
     *  初始化一个Shape，将Shape的宽高绑定该pane的宽高，使得pane的宽高变化时，内容能跟着变换
     *  不同的Shape绑定不同的宽高
     *  最后调用setMyShape置入pane
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
        return  initX;
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

    public void showConnectCircle(){
        hideConnectCircle();
        drawConnectCircle();
        for(Circle circle:connectCircle){
            getChildren().add(circle);
        }
    }

    public void hideConnectCircle(){
        for(Circle circle:connectCircle){
            getChildren().remove(circle);
        }
    }

    public void drawConnectCircle(){

        connectCircle[0].setCenterX(getPrefWidth() / 2);
        connectCircle[0].setCenterY(0);

        connectCircle[1].setCenterX(getPrefWidth());
        connectCircle[1].setCenterY(getPrefHeight() / 2);

        connectCircle[2].setCenterX(getPrefWidth() / 2);
        connectCircle[2].setCenterY(getPrefHeight());

        connectCircle[3].setCenterX(0);
        connectCircle[3].setCenterY(getPrefHeight() / 2);

    }
}
