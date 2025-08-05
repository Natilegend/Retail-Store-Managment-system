package util;

import Exception.RetailInventoryException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FileUtil {
    // Define the path to the log file
    private static final Path LOG_FILE_PATH = Paths.get("logs", "activity.txt");

    public static void log(String message) throws RetailInventoryException {
        try {
            // Get the parent directory of the log file
            Path logDir = LOG_FILE_PATH.getParent();

            // Create the 'logs' directory if it does not exist
            if (logDir != null && !Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            // Write the log message, appending to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH.toFile(), true))) {
                writer.write(LocalDateTime.now() + ": " + message);
                writer.newLine();
            }
        } catch (IOException e) {
            // Throw a custom exception if any file operation fails
            throw new RetailInventoryException("Error writing to log file: " + e.getMessage());
        }
    }
}