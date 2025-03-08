package epam.mapper;

import epam.entity.Trainer;
import epam.entity.TrainingType;
import epam.request_dto.TrainerRequestDTO;
import epam.response_dto.TrainerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerMapper {

    private final UserMapper userMapper;

    public TrainerResponseDTO toTrainerResponseDTO(Trainer trainer) {
        if (trainer == null) {
            return null;
        }

        return TrainerResponseDTO.builder()
                .trainerSpecialization(trainer.getSpecialization() != null ? trainer.getSpecialization().getDescription() : null)
                .user(userMapper.toUserResponseDTO(trainer.getUser()))
                .build();
    }

    public Trainer toTrainer(TrainerRequestDTO trainerRequestDTO, TrainingType trainingType) {
        if (trainerRequestDTO == null) {
            return null;
        }

        return Trainer.builder()
                .specialization(trainingType)
                .user(trainerRequestDTO.getUser() != null ? userMapper.toUser(trainerRequestDTO.getUser()) : null)
                .build();
    }
}
