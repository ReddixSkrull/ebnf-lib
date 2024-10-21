package de.doering.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private static final Logger LOGGER = LogManager.getLogger(FileReader.class);

    public  List<String> readFileFromResources(String fileName) {
        List<String> lines = new ArrayList<>();

        try (InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);  // Füge jede gelesene Zeile zur Liste hinzu
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }

        return lines;
    }
}
