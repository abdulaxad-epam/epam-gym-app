package epam.mapper;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.request_dto.UserRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.UserResponseDTO;
import epam.entity.Trainer;
import epam.entity.TrainingType;
import epam.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerMapperTest {

    @Mock
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);

    private User user;
    private TrainingType trainingType;
    private Trainer trainer;
    private TrainerRequestDTO trainerRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        trainingType = new TrainingType();
        trainingType.setDescription("Fitness");

        userResponseDTO = UserResponseDTO.builder().build();
        userRequestDTO = UserRequestDTO.builder().build();

        trainer = Trainer.builder()
                .user(user)
                .specialization(trainingType)
                .build();

        trainerRequestDTO = TrainerRequestDTO.builder().build();
        trainerRequestDTO.setUser(userRequestDTO);
    }

    @Test
    void testToTrainerResponseDTO() {
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);

        TrainerResponseDTO result = trainerMapper.toTrainerResponseDTO(trainer);

        assertNotNull(result);
        assertEquals("Fitness", result.getTrainerSpecialization());
        assertEquals(userResponseDTO, result.getUser());

        verify(userMapper).toUserResponseDTO(user);
    }

    @Test
    void testToTrainer() {
        when(userMapper.toUser(userRequestDTO)).thenReturn(user);

        Trainer result = trainerMapper.toTrainer(trainerRequestDTO, trainingType);

        assertNotNull(result);
        assertEquals("Fitness", result.getSpecialization().getDescription());
        assertEquals(user, result.getUser());

        verify(userMapper).toUser(userRequestDTO);
    }

    @Test
    void testToTrainerResponseDTO_NullTrainer() {

        TrainerResponseDTO result = trainerMapper.toTrainerResponseDTO(null);
        assertNull(result);
        assertNull(trainerMapper.toTrainer(null, trainingType).getTrainerId());
    }

    @Test
    void testToTrainerResponseDTO_NullSpecialization() {
        trainer.setSpecialization(null);
        TrainerResponseDTO result = trainerMapper.toTrainerResponseDTO(trainer);

        assertNotNull(result);
        assertNull(result.getTrainerSpecialization());
    }

    @Test
    void testToTrainer_NullRequestDTO() {
        Trainer result = trainerMapper.toTrainer(null, trainingType);
        assertNull(result.getTrainerId());
    }

    @Test
    void testToTrainer_NullTrainingType() {
        Trainer result = trainerMapper.toTrainer(trainerRequestDTO, null);
        assertNotNull(result);
        assertNull(result.getSpecialization());
    }
}
