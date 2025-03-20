package epam.mapper;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainer;
import epam.entity.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    @Named("toTrainerResponseDTO")
    @Mappings({
            @Mapping(source = "specialization.description", target = "trainerSpecialization"),
            @Mapping(source = "user", target = "user", qualifiedByName = "toUserResponseDTO")
    })
    TrainerResponseDTO toTrainerResponseDTO(Trainer trainer);

    @Named("toTrainer")
    @Mappings({
            @Mapping(source = "trainingType", target = "specialization"),
            @Mapping(source = "trainerRequestDTO.user", target = "user", qualifiedByName = "toUser")
    })
    Trainer toTrainer(TrainerRequestDTO trainerRequestDTO, TrainingType trainingType);
}