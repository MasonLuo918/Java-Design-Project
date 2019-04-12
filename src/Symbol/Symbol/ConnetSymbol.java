package Symbol.Symbol;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnetSymbol extends AbstractSymbol{

    @Override
    public void initMyShape() {
        setPrefHeight(50);
        setPrefWidth(50);
        Circle circle = new Circle(25);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.centerXProperty().bind(prefWidthProperty().divide(2));
        circle.centerYProperty().bind(prefHeightProperty().divide(2));
        setMyShape(circle);
    }


    /**
     *  取消操作框，固定大小
     */
    @Override
    public void showOperationFrame(){

    }

    @Override
    public void hideOperationFrame(){

    }
}
