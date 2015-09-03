package fabrice.app.files;

import java.io.File;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public interface FilePredicat {
    boolean apply (File file);
}
