package epam.mapper;

import epam.entity.Trainee;
import epam.entity.User;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.TraineeRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import epam.response_dto.TrainingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeMapper {

    private final UserMapper userMapper;

    public TraineeResponseDTO toTraineeResponseDTO(Trainee trainee) {
        return TraineeResponseDTO.builder()
                .traineeDateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .user(userMapper.toUserResponseDTO(trainee.getUser()))
                .build();
    }

    public Trainee toTrainee(TraineeRequestDTO trainee) {
        return Trainee.builder()
                .user(userMapper.toUser(trainee.getUser()))
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .build();
    }

    public Trainee toTrainee(RegisterTraineeRequestDTO userRequestDTO, User connectedUser) {
        return Trainee.builder()
                .address(userRequestDTO.getAddress())
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .user(connectedUser)
                .build();
    }
}
