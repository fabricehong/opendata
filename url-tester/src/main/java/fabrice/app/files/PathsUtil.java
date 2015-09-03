package fabrice.app.files;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 17.06.15
 */
public class PathsUtil {
    public static String join(String path1, String path2) {
        // remove trailing slash
        String firstPath = path1.charAt(path1.length()-1)=='/' ? path1.substring(0, path1.length()-1):path1;

        if(path2.charAt(0)=='/') {
            return firstPath+path2;
        }
        return firstPath+"/"+path2;
    }
}
