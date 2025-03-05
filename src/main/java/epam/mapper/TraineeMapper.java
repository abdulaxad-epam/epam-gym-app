package epam.mapper;

import epam.entity.Trainee;
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
}
