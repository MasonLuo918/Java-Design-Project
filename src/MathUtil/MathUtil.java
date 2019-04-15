package MathUtil;

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

    public static boolean isEqual(double x, double y){
        if(x == y){
            return true;
        }else{
            return false;
        }
    }

    public static double angleChangeArc(double angle){
        return angle / 180 * Math.PI;
    }

//    public static double getAngleOfX(double x1, double y1, double x2, double y2){
//        return 0;
//    }

    public static double getArcOfY(double x1, double y1, double x2, double y2){
       double length = distance(x2, y2, x1, y1);
       double result = Math.asin((x2 - x1) / length);
       return result;
    }

    public static double getArcOfX(double x1, double y1, double x2, double y2){
        double result = Math.atan((y1 - y2) / (x2 - x1));
        if(x2 < x1){
            result += Math.PI;
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(arcChangeAngle(getArcOfX(2,2,1,1)));
    }

    /**
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return 箭头的坐标
     */
    public static Point2D[] getArrowPoint(double startX, double startY, double endX, double endY){
        Point2D[] points = new Point2D[3];
        double arc = getArcOfY(startX, startY, endX, endY);
        System.out.println("arc = " + arc);
        double x, y;
        double height = GlobalConfig.ARROW_LENGTH * ( Math.sqrt(3) / 2);
        x = GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc);
        y = GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc);
        points[0] = new Point2D(endX - height * Math.sin(arc) + x, endY - height * Math.cos(x) - y );
        points[1] = new Point2D(endX - height * Math.sin(arc) - x, endY - height * Math.cos(x) + y);
        points[2] = new Point2D(endX, endY);
        return points;
    }
}
