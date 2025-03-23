package epam.mapper;

import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.entity.Trainee;
import epam.trainee.mapper.TraineeMapper;
import epam.user.dto.UserRequestDTO;
import epam.user.dto.UserResponseDTO;
import epam.user.entity.User;
import epam.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private final TraineeMapper traineeMapper = Mappers.getMapper(TraineeMapper.class);

    private User user;
    private Trainee trainee;
    private TraineeRequestDTO traineeRequestDTO;
    private RegisterTraineeRequestDTO registerTraineeRequestDTO;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        userResponseDTO = UserResponseDTO.builder().build();
        userRequestDTO = UserRequestDTO.builder().build();

        trainee = Trainee.builder()
                .user(user)
                .address("Test Address")
                .dateOfBirth(LocalDate.of(2000, 1, 1).atStartOfDay())
                .build();

        traineeRequestDTO = TraineeRequestDTO.builder()
                .user(userRequestDTO)
                .address("Test Address")
                .dateOfBirth(LocalDate.from(LocalDate.of(2000, 1, 1).atStartOfDay()))
                .build();

        registerTraineeRequestDTO = RegisterTraineeRequestDTO.builder()
                .address("Test Address")
                .dateOfBirth(LocalDate.from(LocalDate.of(2000, 1, 1).atStartOfDay()))
                .build();
    }

    @Test
    void testToTraineeResponseDTO() {
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);

        TraineeResponseDTO result = traineeMapper.toTraineeResponseDTO(trainee);

        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        assertEquals(LocalDate.of(2000, 1, 1).atStartOfDay(), result.getTraineeDateOfBirth());
        assertEquals(userResponseDTO, result.getUser());

        verify(userMapper).toUserResponseDTO(user);
    }

    @Test
    void testToTrainee() {
        when(userMapper.toUser(userRequestDTO)).thenReturn(user);

        Trainee result = traineeMapper.toTrainee(traineeRequestDTO);

        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        assertEquals(LocalDate.of(2000, 1, 1).atStartOfDay(), result.getDateOfBirth());
        assertEquals(user, result.getUser());

        verify(userMapper).toUser(userRequestDTO);
    }

    @Test
    void testToTrainee_WithRegisterRequest() {
        Trainee result = traineeMapper.toTrainee(registerTraineeRequestDTO, user);

        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        assertEquals(LocalDate.of(2000, 1, 1).atStartOfDay(), result.getDateOfBirth());
        assertEquals(user, result.getUser());
    }

    @Test
    void testToTraineeResponseDTO_NullUser() {
        trainee.setUser(null);

        TraineeResponseDTO result = traineeMapper.toTraineeResponseDTO(trainee);

        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        assertEquals(LocalDate.of(2000, 1, 1).atStartOfDay(), result.getTraineeDateOfBirth());
        assertNull(result.getUser());
    }
}
