package Symbol.Line;

import Manager.SymbolManage;
import Symbol.GlobalConfig;
import Util.MathUtil;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class DoubleBrokenLine extends AbstractLine {

    private Circle circle;

    public DoubleBrokenLine(Pane drawPane){
        super();
        setDrawPane(drawPane);
        setLineType(LineType.DOUBLE_BROKEN_LINE);
        setStrokeWidth(2);
        setFill(null);
        for(int i = 0; i < getCircles().length; i++){
            getCircles()[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            getCircles()[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        }
        circle = new Circle(GlobalConfig.CIRCLE_RADIUS);
        circle.setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        setStartX((getDrawPane().getWidth() - getLineLength()) / 2);
        setStartY(getDrawPane().getHeight() / 2);
        setEndX((getDrawPane().getWidth() - getLineLength()) / 2);
        setEndY(getDrawPane().getHeight() / 2 + getLineLength());
        setMiddleX(getStartX() + getLineLength());
        setMiddleY((getStartY() + getEndY()) / 2);
        updateLine();
        drawOperationFrame();
        initEvent();
        initOperationFrameEvent();
        drawPane.getChildren().add(this);
    }

    @Override
    public void updateLine() {
        double x1 = getStartX();
        double y1 = getStartY();
        double height = GlobalConfig.ARROW_LENGTH * (Math.sqrt(3) / 2);

        double arc1 = MathUtil.getArcOfX(getStartX(),getStartY(),getMiddleX(),getStartY());
        double arc2 = MathUtil.getArcOfX(getEndX(),getEndY(),getMiddleX(),getEndY());
        double x2 = getStartX() + height * Math.cos(arc1);
        double y2 = getStartY() - height * Math.sin(arc1);

        double x3 = x2 - GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc1);
        double y3 = y2 - GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc1);

        double x4 = x2 + GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc1);
        double y4 = y2 + GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc1);

        double x5 = getMiddleX();
        double y5 = getStartY();

        double x6 = getMiddleX();
        double y6 = getEndY();

        double x7 = getEndX() + height * Math.cos(arc2) ;
        double y7 = getEndY() - height * Math.sin(arc2);

        double x8 =x7 - GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc2);
        double y8 =y7 - GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc2);

        double x9=getEndX();
        double y9=getEndY();

        double x10=x7 + GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc2);
        double y10=y7 + GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc2);

        getPoints().clear();
        getPoints().addAll(x1,y1,x3,y3,x4,y4,x1,y1,x3,y3,x2,y2,x5,y5,x6,y6,x7,y7,x8,y8,x9,y9,x10,y10,x7,y7);
    }

    @Override
    public void select(MouseEvent event) {
        if(event.isControlDown()){
            SymbolManage.getManage().getSelectedShape().addMore(this);
        }else{
            SymbolManage.getManage().getSelectedShape().add(this);
        }
    }

    @Override
    public void initOperationFrameEvent() {
        for(Circle circle:getCircles()){
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setCursor(Cursor.E_RESIZE);
                }
            });
        }
        circle.setCursor(Cursor.E_RESIZE);
        getCircles()[0].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getStartConnect().isBind()){
                    getStartConnect().unConnect();
                }
            }
        });
        getCircles()[1].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getEndConnect().isBind()){
                    getEndConnect().unConnect();
                }
            }
        });

        getCircles()[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setStartX(MouseInParent.getX());
                setStartY(MouseInParent.getY());
                setMiddleY((getStartY() + getEndY()) / 2);
                SymbolManage.getManage().detectLineEnter(getStartX(), getStartY());
            }
        });
        getCircles()[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setEndX(MouseInParent.getX());
                setEndY(MouseInParent.getY());
                setMiddleY((getStartY() + getEndY()) / 2);
                SymbolManage.getManage().detectLineEnter(getEndX(),getEndY());
            }
        });
        getCircles()[0].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(DoubleBrokenLine.this, getStartConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });

        getCircles()[1].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(DoubleBrokenLine.this, getEndConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setMiddleX(MouseInParent.getX());
                setMiddleY(MouseInParent.getY());
                setMiddleY((getStartY() + getEndY()) / 2);
            }
        });
    }

    @Override
    public void updateText() {
        getText().setX((getStartX() + getMiddleX()) / 2);
        getText().setY((getStartY() - 10));
    }

    @Override
    public void drawOperationFrame() {
        getCircles()[0].setCenterX(startX.get());
        getCircles()[0].setCenterY(startY.get());
        getCircles()[1].setCenterX(endX.get());
        getCircles()[1].setCenterY(endY.get());
        circle.setCenterX(getMiddleX());
        circle.setCenterY(getMiddleY());
    }
    @Override
    public void showOperationFrame() {
        for(Circle circle:getCircles()){
            getDrawPane().getChildren().add(circle);
        }
        getDrawPane().getChildren().add(circle);
    }

    @Override
    public void hideOperationFrame() {
        for(Circle circle:getCircles()){
            getDrawPane().getChildren().remove(circle);
        }
        getDrawPane().getChildren().remove(circle);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationCircle = false;
        for(Circle circle: getCircles()){
            Bounds toLocal = circle.getBoundsInLocal();
            Bounds toScene = circle.localToScene(toLocal);
            if(toScene.contains(x, y)){
                inOperationCircle = true;
                break;
            }
        }
        if(circle.localToScene(circle.getBoundsInLocal()).contains(x, y)){
            inOperationCircle = true;
        }
        if(!inOperationCircle){
            return getBoundsInScene().contains(x,y);
        }else{
            return false;
        }
    }
}
