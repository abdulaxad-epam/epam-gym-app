package epam.mapper;


import epam.entity.TrainingType;
import epam.request_dto.RegisterTrainerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingTypeMapper {

    public TrainingType toTrainingType(RegisterTrainerRequestDTO userRequestDTO) {
        if (userRequestDTO == null || userRequestDTO.getSpecialization() == null) {
            return null;
        }
        return TrainingType.builder()
                .description(userRequestDTO.getSpecialization())
                .build();
    }

    public TrainingType toTrainingType(String specialization) {
        if (specialization == null) {
            return null;
        }
        return TrainingType.builder()
                .description(specialization)
                .build();
    }
}
