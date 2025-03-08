package epam.response_dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TrainingResponseDTOTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime trainingDate = LocalDateTime.of(2024, 3, 8, 10, 0);
        LocalDateTime duration = LocalDateTime.of(2024, 3, 8, 12, 0);

        TraineeResponseDTO trainee = TraineeResponseDTO.builder()
                .traineeDateOfBirth(LocalDateTime.of(1995, 5, 15, 0, 0))
                .address("123 Main St")
                .build();

        TrainerResponseDTO trainer = TrainerResponseDTO.builder()
                .trainerSpecialization("Cardio")
                .build();

        TrainingResponseDTO dto = TrainingResponseDTO.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName("Morning Cardio")
                .trainingDate(trainingDate)
                .trainingType("Cardio")
                .trainingDuration(duration)
                .build();

        assertEquals("Morning Cardio", dto.getTrainingName());
        assertEquals("Cardio", dto.getTrainingType());
        assertEquals(trainingDate, dto.getTrainingDate());
        assertEquals(duration, dto.getTrainingDuration());
        assertEquals(trainee, dto.getTrainee());
        assertEquals(trainer, dto.getTrainer());
    }

    @Test
    void testSetters() {
        TrainingResponseDTO dto = TrainingResponseDTO.builder().build();
        LocalDateTime trainingDate = LocalDateTime.of(2024, 3, 8, 14, 0);
        LocalDateTime duration = LocalDateTime.of(2024, 3, 8, 16, 0);

        dto.setTrainingName("Evening Yoga");
        dto.setTrainingType("Yoga");
        dto.setTrainingDate(trainingDate);
        dto.setTrainingDuration(duration);

        assertEquals("Evening Yoga", dto.getTrainingName());
        assertEquals("Yoga", dto.getTrainingType());
        assertEquals(trainingDate, dto.getTrainingDate());
        assertEquals(duration, dto.getTrainingDuration());
    }

    @Test
    void testToString() {
        LocalDateTime trainingDate = LocalDateTime.of(2024, 3, 8, 18, 0);
        LocalDateTime duration = LocalDateTime.of(2024, 3, 8, 19, 30);

        TrainingResponseDTO dto = TrainingResponseDTO.builder()
                .trainingName("Night Workout")
                .trainingType("Strength")
                .trainingDate(trainingDate)
                .trainingDuration(duration)
                .build();

        String expected = "TrainingResponseDTO(trainee=null, trainer=null, trainingName=Night Workout, trainingDate=" +
                trainingDate + ", trainingType=Strength, trainingDuration=" + duration + ")";

        assertEquals(expected, dto.toString());
    }
}
