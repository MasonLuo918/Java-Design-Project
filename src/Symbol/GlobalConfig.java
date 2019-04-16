package Symbol;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public interface GlobalConfig {
    public static final double CIRCLE_RADIUS = 4;

    public static final Color OPERATION_FRAME_CIRCLE_COLOR = Color.rgb(0, 255, 255);

    public static final Color OPERATION_FRAME_LINE_COLOR = Color.rgb(0, 255, 255);

    public static final Color CONNECT_CIRCLE_COLOR = Color.rgb(193,205,205);

    public static final Color SYMBOL_FILL_COLOR = Color.WHITE;

    public static final Color SYMBOL_STROKE_COLOR = Color.BLACK;

    public static final Font FONT = Font.font(12);

    public static final double LINE_LENGTH = 100;

    public static final double PANE_WIDTH = 150;

    public static final double PANE_HEIGHT = 100;

    public static final double MIN_PANE_WIDTH = 100;

    public static final double MIN_PANE_HEIGHT = 75;

    public static final double MAX_PANE_WIDTH = 400;

    public static final double MAX_PANE_HEIGHT = 200;

    public static final double ENTER_WIDTH = 50; // 当线进入边界50左右就显示连接圆

    public static final double ARROW_LENGTH = 10;

    /**
     * 平行四边形缩紧距离
     */
    public static final double IOFRAME_OFFSET = 30;
    /*
     *  连接操作线和旋转圆的线的长度
     */
    public static final double OFFSET = 50;
}
