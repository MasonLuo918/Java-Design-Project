package MathUtil;

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
}
