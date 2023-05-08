package io.harness.plugins.harness_bva.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File createFileRecursively(File createFile) throws IOException {
        if(createFile.exists()){
            return createFile;
        }
        File parentDir = createFile.getParentFile();
        if(! parentDir.exists()){
            if(!parentDir.mkdirs()){
                throw new IOException("Could not create parentDir " + parentDir.toString());

            }
        }
        if(!createFile.createNewFile()){
            throw new IOException("Could not create file " + createFile.toString());
        }
        return createFile;
    }

    public static File createDirectoryRecursively(final String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        return FileUtils.createDirectoryRecursively(directory);
    }

    public static File createDirectoryRecursively(File createDir) throws IOException {
        if(createDir.exists()) {
            return createDir;
        }
        if(!createDir.mkdirs()){
            throw new IOException("Could not create dir " + createDir.toString());
        }
        return createDir;
    }
}
