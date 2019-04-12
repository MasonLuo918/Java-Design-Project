package Symbol.Line;

import Symbol.MShape;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public abstract class AbstractLine extends Polyline implements MShape {

    private Pane drawPane;

    private int lineType;

    private double initX;

    private double initY;

    private Point2D cursorPoint;

    private double initLineLenght = 100;


    SimpleDoubleProperty startX = new SimpleDoubleProperty();

    SimpleDoubleProperty startY = new SimpleDoubleProperty();

    SimpleDoubleProperty endX = new SimpleDoubleProperty();

    SimpleDoubleProperty endY = new SimpleDoubleProperty();

    SimpleDoubleProperty middleX = new SimpleDoubleProperty();

    SimpleDoubleProperty middleY = new SimpleDoubleProperty();


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

    public double getInitLineLenght() {
        return initLineLenght;
    }

    public void setInitLineLenght(double initLineLenght) {
        this.initLineLenght = initLineLenght;
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
}
