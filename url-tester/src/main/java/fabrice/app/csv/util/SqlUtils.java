package fabrice.app.csv.util;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class SqlUtils {

    public static String toSqlName(String str) {
        return stripAccents(str.trim().toLowerCase().replace("-", "_").replace(".", "").replace(" (", "_").replace(") ", "_").replace("(", "").replace(")", "").replace(" ", "_"));
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static String toValue(String val) {
        String v = val.trim();
        if (StringUtils.isNumeric(v)) {
            return v;
        }
        if (StringUtils.isEmpty(v)) {
            return "NULL";
        }
        if ("*".equals(v)) {
            return "NULL";
        }
        System.out.println(String.format("Invalid value : '%s'. Returning null", v));
        return "NULL";
    }
}
