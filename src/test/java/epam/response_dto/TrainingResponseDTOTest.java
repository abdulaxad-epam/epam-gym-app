package epam.response_dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainingResponseDTOTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime trainingDate = LocalDateTime.of(2024, 3, 8, 10, 0);

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
                .trainingDuration(2)
                .build();

        assertEquals("Morning Cardio", dto.getTrainingName());
        assertEquals("Cardio", dto.getTrainingType());
        assertEquals(trainingDate, dto.getTrainingDate());
        assertEquals(2, dto.getTrainingDuration());
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
        dto.setTrainingDuration(3);

        assertEquals("Evening Yoga", dto.getTrainingName());
        assertEquals("Yoga", dto.getTrainingType());
        assertEquals(trainingDate, dto.getTrainingDate());
        assertEquals(3, dto.getTrainingDuration());
    }

    @Test
    void testToString() {
        LocalDateTime trainingDate = LocalDateTime.of(2024, 3, 8, 18, 0);

        TrainingResponseDTO dto = TrainingResponseDTO.builder()
                .trainingName("Night Workout")
                .trainingType("Strength")
                .trainingDate(trainingDate)
                .trainingDuration(1)
                .build();

        String expected = "TrainingResponseDTO(trainee=null, trainer=null, trainingName=Night Workout, trainingDate=" +
                trainingDate + ", trainingType=Strength, trainingDuration=" + 1 + ")";

        assertEquals(expected, dto.toString());
    }
}
