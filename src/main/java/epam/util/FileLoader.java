package epam.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;



@Component
public class FileLoader {

    private final Log log = LogFactory.getLog(FileLoader.class);

    @Value("${storage.file.path}")
    private String storageFilePath;

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, List<Map<String, String>>> loadFile() {

        File file = new File(storageFilePath);
        if (!file.exists()) {
            log.error("Data file not found: " + storageFilePath);
            return Map.of();
        }

        try {
            log.info("Loading data from file: " + storageFilePath);
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Error reading data from file: " + storageFilePath, e);
            throw new RuntimeException("Failed to load data from file: " + storageFilePath, e);
        }

    }
}
