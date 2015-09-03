package fabrice.app.files;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Fabrice Hong
 * @date 16.04.15
 */
public class Directory {

    protected final Logger log;

    private String fileCollectionRoot;
    private Collection<File> pathCollection;
    private List<FilePredicat> predicats;

    public Directory(String fileCollectionRoot) {
        this.fileCollectionRoot = fileCollectionRoot;
        this.predicats = new ArrayList<FilePredicat>();
        this.log = LoggerFactory.getLogger(Directory.class);
    }

    public Directory withPredicat(FilePredicat filePredicat) {
        this.predicats.add(filePredicat);
        return this;
    }

    public Collection<File> getPathCollection() throws IOException {
        if (this.pathCollection == null) {
            this.pathCollection = getDescendantDirectories(getFileCollectionRoot());
        }
        return this.pathCollection;
    }

    private String getFileCollectionRoot() {
        return this.fileCollectionRoot;
    }


    private List<File> getDescendantDirectories(String path) throws IOException {

        List<File> collectedFiles = new ArrayList<File>();
        String primaryType;
        File xmlFile;

        File directory = new File(path);
        if(!directory.isDirectory()){
            log.error(String.format("The path %s is not a directory", path));
            return collectedFiles;
        }
        //collectedFiles.add(directory);

        File[] childs = directory.listFiles();
        if(childs != null) {
            for (File entry : childs) {
                if (applies(entry)) {
                    collectedFiles.add(entry);
                }
                if (entry.isDirectory()) {
                    collectedFiles.addAll(getDescendantDirectories(entry.getPath()));
                }
            }
        }
        return collectedFiles;
    }

    private boolean applies(File entry) {
        for (FilePredicat predicat : predicats) {
            if (!predicat.apply(entry)) {
                return false;
            }
        }
        return true;
    }

    public static String relativize(String root, String absolutePath){
        String path = FilenameUtils.normalize(absolutePath);
        String normalizedRoot = FilenameUtils.normalize(root);
        if(absolutePath.startsWith(normalizedRoot)){
            path = absolutePath.substring(normalizedRoot.length()+1);
        }
        return path;
    }

    private static String getParent(String path){
        return path.substring(0, path.lastIndexOf("/"));
    }
}
