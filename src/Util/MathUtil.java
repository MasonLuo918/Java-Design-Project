package Util;

import Symbol.GlobalConfig;
import javafx.geometry.Point2D;

public class MathUtil {


    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 两个点之间的距离
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * @param arc
     * @return 弧度转化为角度的值
     */
    public static double arcChangeAngle(double arc) {
        return 180 / Math.PI * arc;
    }

    public static boolean isEqual(double x, double y) {
        if (x == y) {
            return true;
        } else {
            return false;
        }
    }

    public static double angleChangeArc(double angle) {
        return angle / 180 * Math.PI;
    }

//    public static double getAngleOfX(double x1, double y1, double x2, double y2){
//        return 0;
//    }

    public static double getArcOfY(double x1, double y1, double x2, double y2) {
        double length = distance(x2, y2, x1, y1);
        double result = Math.asin((x2 - x1) / length);
        return result;
    }

    public static double getArcOfX(double x1, double y1, double x2, double y2) {
        double result = Math.atan((y1 - y2) / (x2 - x1));
        if (x2 < x1) {
            result += Math.PI;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(arcChangeAngle(getArcOfX(2, 2, 1, 1)));
    }

    /**
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return 箭头的坐标
     */
    public static Point2D[] getArrowPoint(double startX, double startY, double endX, double endY) {
        Point2D[] points = new Point2D[3];
        double arc = getArcOfY(startX, startY, endX, endY);
        System.out.println("arc = " + arc);
        double x, y;
        double height = GlobalConfig.ARROW_LENGTH * (Math.sqrt(3) / 2);
        x = GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc);
        y = GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc);
        points[0] = new Point2D(endX - height * Math.sin(arc) + x, endY - height * Math.cos(x) - y);
        points[1] = new Point2D(endX - height * Math.sin(arc) - x, endY - height * Math.cos(x) + y);
        points[2] = new Point2D(endX, endY);
        return points;
    }

    public static Point2D[] getArrowPoint(int arrowType, double x, double y) {
        Point2D[] points = new Point2D[3];
        double height = GlobalConfig.ARROW_LENGTH * (Math.sqrt(3) / 2);
        double length = GlobalConfig.ARROW_LENGTH;
        double x1, x2, x3, y1, y2, y3;
        switch (arrowType) {
            case GlobalConfig.ARROW_LEFT:
                x1 = x + height;
                y1 = y - length / 2;
                x2 = x + height;
                y2 = y + length / 2;
                x3 = x + height;
                y3 = y;
                break;
            case GlobalConfig.ARROW_BOTTOM:
                x1 = x + length / 2;
                y1 = y - height;
                x2 = x - length / 2;
                y2 = y - height;
                x3 = x;
                y3 = y - height;
                break;
            case GlobalConfig.ARROW_RIGHT:
                x1 = x - height;
                y1 = y - length / 2;
                x2 = x - height;
                y2 = y + length / 2;
                x3 = x - height;
                y3 = y;
                break;
            case GlobalConfig.ARROW_TOP:
                x1 = x - length / 2;
                y1 = y + height;
                x2 = x + length / 2;
                y2 = y + height;
                x3 = x;
                y3 = y + height;
                break;
            default:
                x1 = x2 = x3 = y1 = y2 = y3 = 0;
                break;
        }
        points[0] = new Point2D(x1, y1);
        points[1] = new Point2D(x2, y2);
        points[2] = new Point2D(x3, y3);
        return points;
    }
}
