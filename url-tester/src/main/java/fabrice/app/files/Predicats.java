package fabrice.app.files;

import java.io.File;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class Predicats {
    public static FilePredicat hasExtension(final String extension) {
        return new FilePredicat() {
            @Override
            public boolean apply(File file) {
                return file.getName().endsWith(extension);
            }
        };
    }

    public static FilePredicat isFile() {
        return new FilePredicat() {
            @Override
            public boolean apply(File file) {
                return file.isFile();
            }
        };

    }
}
