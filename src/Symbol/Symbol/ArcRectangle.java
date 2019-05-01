package Symbol.Symbol;

import Symbol.GlobalConfig;

public class ArcRectangle extends AbstractSymbol {

    private final double arc = 50;

    @Override
    public void initMyShape() {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.widthProperty().bind(prefWidthProperty());
        rectangle.heightProperty().bind(prefHeightProperty());
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);
        rectangle.setFill(GlobalConfig.SYMBOL_FILL_COLOR);
        rectangle.setStroke(GlobalConfig.SYMBOL_STROKE_COLOR);
        setMyShape(rectangle);
    }

    @Override
    public void initSymbolType(){
        setSymbolType(SymbolType.ARC_RECTANGLE);
    }
}
