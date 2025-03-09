package epam.mapper;


import epam.entity.TrainingType;
import epam.request_dto.RegisterTrainerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {
    TrainingTypeMapper INSTANCE = Mappers.getMapper(TrainingTypeMapper.class);

    @Named("toTrainingType")
    @Mapping(source = "specialization", target = "description")
    TrainingType toTrainingType(RegisterTrainerRequestDTO userRequestDTO);

    default TrainingType toTrainingType(String specialization) {
        if (specialization == null) {
            return null;
        }
        return TrainingType.builder()
                .description(specialization)
                .build();
    }
}
