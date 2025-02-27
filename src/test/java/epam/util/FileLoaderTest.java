package epam.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class FileLoaderTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FileLoader fileLoader;


    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadFileSuccess() throws IOException {
        // Create the file in the temp directory
        File tempFile = tempDir.resolve("preparedData.json").toFile();

        // Ensure directories exist
        tempFile.getParentFile().mkdirs();

        // Write test data
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("""
            {
                "trainee": [
                    {"userId": "123e4567-e89b-12d3-a456-426614174000", "firstname": "John", "lastname": "Doe"}
                ],
                "trainer": [
                    {"userId": "456e4567-e89b-12d3-a456-426614174000", "firstname": "Jane", "lastname": "Smith"}
                ]
            }
            """);
        }

        // Set correct file path in FileLoader
        ReflectionTestUtils.setField(fileLoader, "storageFilePath", tempFile.getAbsolutePath());

        // Use real ObjectMapper
        ObjectMapper realMapper = new ObjectMapper();
        ReflectionTestUtils.setField(fileLoader, "objectMapper", realMapper);

        // Load file and assert results
        Map<String, List<Map<String, String>>> result = fileLoader.loadFile();
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.get("trainee").size(), "Should contain one trainee entry");
        assertEquals(1, result.get("trainer").size(), "Should contain one trainer entry");
        assertEquals("John", result.get("trainee").get(0).get("firstname"), "Trainee name should be John");
        assertEquals("Jane", result.get("trainer").get(0).get("firstname"), "Trainer name should be Jane");
    }


    @Test
    void testLoadFileNotFound() {
        ReflectionTestUtils.setField(fileLoader, "storageFilePath", "preparedData.json");

        Map<String, List<Map<String, String>>> result = fileLoader.loadFile();

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when file is missing");
    }


    @Test
    void testLoadFileIOException() throws IOException {
        ReflectionTestUtils.setField(fileLoader, "storageFilePath", "src/main/resources/preparedData.json");

        when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
                .thenThrow(new IOException("Mocked IO Exception"));

        Exception exception = assertThrows(RuntimeException.class, () -> fileLoader.loadFile());
        assertTrue(exception.getMessage().contains("Failed to load data from file"), "Exception should indicate failure");
    }
}
