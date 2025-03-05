package epam.mapper;

import epam.entity.Trainer;
import epam.response_dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerMapper {

    private final UserMapper userMapper;

    public TrainerResponseDTO toTrainerResponseDTO(Trainer trainer) {
        return TrainerResponseDTO.builder()
                .trainerSpecialization(trainer.getSpecialization())
                .user(userMapper.toUserResponseDTO(trainer.getUser()))
                .build();
    }
}
