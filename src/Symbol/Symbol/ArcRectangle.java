package Symbol.Symbol;

import Symbol.GlobalConfig;

public class ArcRectangle extends AbstractSymbol {

    private double arc = 30;

    @Override
    public void initMyShape() {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.widthProperty().bind(prefWidthProperty());
        rectangle.heightProperty().bind(prefHeightProperty());
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        rectangle.setFill(GlobalConfig.SYMBOL_FILL_COLOR);
        rectangle.setStroke(GlobalConfig.SYMBOL_STROKE_COLOR);
        setMyShape(rectangle);
    }
}
