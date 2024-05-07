package storage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EscanerDeArchivos {

    private static final List<String> sensitiveExtensions = List.of("docx", "xlsx", "pdf");

    public List<File> scanForSensitiveFiles(String directoryPath) {
        List<File> sensitiveFiles = new ArrayList<>();
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        File file = path.toFile();
                        if (isSensitiveFile(file)) {
                            sensitiveFiles.add(file);
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error scanning directory: " + e.getMessage());
        }
        return sensitiveFiles;
    }

    private boolean isSensitiveFile(File file) {
        String fileName = file.getName();
        return sensitiveExtensions.stream().anyMatch(fileName::endsWith);
    }

}
