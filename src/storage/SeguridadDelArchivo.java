package storage;

import java.io.File;

public class SeguridadDelArchivo {

    public boolean isFileEncrypted(File file) {
        return file.getName().contains(".encrypted");
    }
}
