package epam.trainer.mapper;

import epam.shared.security.dto.RegisterTrainerResponseDTO;
import epam.training_type.entity.TrainingType;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.entity.Trainer;
import epam.user.mapper.UserMapper;
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

    @Named("toRegisterTrainerResponseDTO")
    @Mappings({
            @Mapping(source = "specialization.description", target = "trainerSpecialization"),
            @Mapping(source = "user", target = "user", qualifiedByName = "toUserResponseDTO"),
            @Mapping(source = "user.password", target = "password")
    })
    RegisterTrainerResponseDTO toRegisterTrainerResponseDTO(Trainer insert);
}