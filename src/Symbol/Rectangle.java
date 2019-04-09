package Symbol;

public class Rectangle extends AbstractSymbol {

    @Override
    public void initMyShape() {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.widthProperty().bind(prefWidthProperty());
        rectangle.heightProperty().bind(prefHeightProperty());
        rectangle.setFill(GlobalConfig.SYMBOL_FILL_COLOR);
        rectangle.setStroke(GlobalConfig.SYMBOL_STROKE_COLOR);
        setMyShape(rectangle);
    }
}
