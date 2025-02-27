package epam.config;

import epam.dao.TraineeDAO;
import epam.dao.TrainerDAO;
import epam.dao.TrainingDAO;
import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import epam.enums.TrainingType;
import epam.util.FileLoader;
import epam.util.parser.TraineeParser;
import epam.util.parser.TrainerParser;
import epam.util.parser.TrainingParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadConfigurationTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private FileLoader fileLoader;

    @Mock
    private TraineeParser traineeParser;

    @Mock
    private TrainerParser trainerParser;

    @Mock
    private TrainingParser trainingParser;

    private LoadConfiguration loadConfiguration;

    private Trainee trainee;
    private Trainee trainee2;
    private Trainer trainer;
    private Training training;

    @BeforeEach
    void setUp() {
        loadConfiguration = new LoadConfiguration(trainingDAO, trainerDAO, traineeDAO, fileLoader, traineeParser, trainerParser, trainingParser);

        trainee = Trainee.builder()
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .isActive(true)
                .dateOfBirth("1995-06-15")
                .address("123 Main St, Springfield")
                .build();

        trainee2 = Trainee.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .dateOfBirth("1998-09-20")
                .address("456 Elm St, Metropolis")
                .build();

        training = Training.builder()
                .trainingId(UUID.fromString("770e8403-e29b-41d4-a716-446655440003"))
                .traineeId(UUID.randomUUID().toString())
                .trainingName("Some training")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("7 years")
                .build();

        trainer = Trainer.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .specialization("Specialization")
                .build();
    }

    @Test
    void testLoadDataFromFile_WithValidData() {
        // Mocking
        Map<String, List<Map<String, String>>> mockData = new HashMap<>();

        List<Map<String, String>> traineesData = List.of(
                Map.of("firstname", "John", "lastname", "Doe"),
                Map.of("firstname", "Alice", "lastname", "Smith")
        );

        List<Map<String, String>> trainersData = List.of(
                Map.of("firstname", "Mike", "lastname", "Brown")
        );

        List<Map<String, String>> trainingsData = List.of(
                Map.of("title", "Java Training")
        );

        mockData.put("trainee", traineesData);
        mockData.put("trainer", trainersData);
        mockData.put("trainings", trainingsData);

        when(fileLoader.loadFile()).thenReturn(mockData);

        // Mocking parser
        List<Trainee> trainees = List.of(trainee, trainee2);
        List<Trainer> trainers = List.of(trainer);
        List<Training> trainings = List.of(training);

        when(traineeParser.parse(traineesData)).thenReturn(trainees);
        when(trainerParser.parse(trainersData)).thenReturn(trainers);
        when(trainingParser.parse(trainingsData)).thenReturn(trainings);

        loadConfiguration.loadDataFromFile();

        // Verify
        verify(traineeDAO, times(1)).insert(trainee.getUserId(), trainee);
        verify(trainerDAO, times(1)).insert(trainer.getUserId(), trainer);
        verify(trainingDAO, times(1)).insert(training.getTrainingId(), training);
    }

    @Test
    void testLoadDataFromFile_WithNoData() {
        when(fileLoader.loadFile()).thenReturn(Collections.emptyMap());

        loadConfiguration.loadDataFromFile();

        verifyNoInteractions(traineeDAO, trainerDAO, trainingDAO);
    }

    @Test
    void testLoadDataFromFile_WithEmptyLists() {
        Map<String, List<Map<String, String>>> mockData = new HashMap<>();
        mockData.put("trainee", Collections.emptyList());
        mockData.put("trainer", Collections.emptyList());
        mockData.put("trainings", Collections.emptyList());

        when(fileLoader.loadFile()).thenReturn(mockData);

        loadConfiguration.loadDataFromFile();

        verifyNoInteractions(traineeDAO, trainerDAO, trainingDAO);
    }
}
