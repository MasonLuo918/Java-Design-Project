package Util;

public class UUID {
    public static String getUUID(){
        java.util.UUID uuid = java.util.UUID.randomUUID();
        String string = uuid.toString();
        String uuidStr=string.replace("-", "");
        return uuidStr;
    }
}
