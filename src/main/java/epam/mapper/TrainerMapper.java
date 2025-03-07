package epam.mapper;

import epam.entity.Trainer;
import epam.request_dto.TrainerRequestDTO;
import epam.response_dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerMapper {

    private final UserMapper userMapper;

    private final TrainingTypeMapper trainingTypeMapper;


    public TrainerResponseDTO toTrainerResponseDTO(Trainer trainer) {
        return TrainerResponseDTO.builder()
                .trainerSpecialization(trainer.getSpecialization().getDescription())
                .user(userMapper.toUserResponseDTO(trainer.getUser()))
                .build();
    }

    public Trainer toTrainer(TrainerRequestDTO trainerRequestDTO) {
        return Trainer.builder()
                .specialization(trainingTypeMapper.toTrainingType(trainerRequestDTO.getSpecialization()))
                .user(userMapper.toUser(trainerRequestDTO.getUser()))
                .build();
    }
}
