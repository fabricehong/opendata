package fabrice.app.csv.util;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvUtil {

    public static int colNameToindex(String name) {
        int result = 0;
        int factor = 1;
        char[] chars = name.toUpperCase().toCharArray();
        for (int i = chars.length-1; i>=0; i--) {
            result+=charToUnit(chars[i])*factor;
            factor*=26;
        }
        return result;
    }

    private static int charToUnit(Character c) {
        return c-64;
    }
}
