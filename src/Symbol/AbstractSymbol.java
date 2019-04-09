package Symbol;

import Manager.SymbolManage;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import jdk.nashorn.internal.objects.Global;

import java.awt.*;

public abstract class AbstractSymbol extends Pane implements MShape{


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
        }
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
        getChildren().addAll(myShape);
    }

    @Override
    public void showOperationFrame() {
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
        /*
         *  设置点击选中事件
         *  (之后取消，改为整个画布)
         */
//        myShape.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                select(event);
//            }
//        });

    }

    @Override
    public void initOperationFrameEvent() {

    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        Bounds bounds = getBoundsInScene();
        if(bounds.contains(x,y)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Point2D sceneToParent(double x,double y){
        Point2D toLocal = sceneToLocal(x, y);
        Point2D toParent = localToParent(x,y);
        return toParent;
    }


    @Override
    public Bounds getBoundsInScene() {
        Bounds localBounds = getBoundsInLocal();
        Bounds sceneBounds = localToScene(localBounds);
        return sceneBounds;
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
}
