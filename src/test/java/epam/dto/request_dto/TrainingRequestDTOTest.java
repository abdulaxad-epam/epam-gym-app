package epam.dto.request_dto;

import epam.training.dto.TrainingRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 10, 14, 30);

        TrainingRequestDTO dto = TrainingRequestDTO.builder()
                .trainerUsername("trainer123")
                .traineeUsername("trainee456")
                .trainingName("Strength Training")
                .trainingDate(date)
                .trainingType("Weightlifting")
                .trainingDuration(60)
                .build();

        assertEquals("trainer123", dto.getTrainerUsername());
        assertEquals("trainee456", dto.getTraineeUsername());
        assertEquals("Strength Training", dto.getTrainingName());
        assertEquals(date, dto.getTrainingDate());
        assertEquals("Weightlifting", dto.getTrainingType());
        assertEquals(60, dto.getTrainingDuration());
    }

    @Test
    void testSetters() {
        TrainingRequestDTO dto = TrainingRequestDTO.builder().build();
        LocalDateTime date = LocalDateTime.of(2024, 5, 15, 10, 0);

        dto.setTrainerUsername("coachMike");
        dto.setTraineeUsername("johnDoe");
        dto.setTrainingName("Cardio Blast");
        dto.setTrainingDate(date);
        dto.setTrainingType("Running");
        dto.setTrainingDuration(45);

        assertEquals("coachMike", dto.getTrainerUsername());
        assertEquals("johnDoe", dto.getTraineeUsername());
        assertEquals("Cardio Blast", dto.getTrainingName());
        assertEquals(date, dto.getTrainingDate());
        assertEquals("Running", dto.getTrainingType());
        assertEquals(45, dto.getTrainingDuration());
    }

    @Test
    void testToString() {
        LocalDateTime date = LocalDateTime.of(2024, 6, 20, 16, 15);

        TrainingRequestDTO dto = TrainingRequestDTO.builder()
                .trainerUsername("fitTrainer")
                .traineeUsername("fitTrainee")
                .trainingName("Yoga Session")
                .trainingDate(date)
                .trainingType("Yoga")
                .trainingDuration(90)
                .build();

        String expected = "TrainingRequestDTO(trainerUsername=fitTrainer, traineeUsername=fitTrainee, trainingName=Yoga Session, trainingDate=" + date + ", trainingType=Yoga, trainingDuration=90)";
        assertEquals(expected, dto.toString());
    }
}
