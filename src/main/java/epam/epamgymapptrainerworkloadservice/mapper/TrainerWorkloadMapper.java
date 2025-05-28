package epam.epamgymapptrainerworkloadservice.mapper;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadRequestDTO;
import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadResponseDTO;
import epam.epamgymapptrainerworkloadservice.entity.TrainerWorkload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainerWorkloadMapper {
    TrainerWorkloadMapper INSTANCE = Mappers.getMapper(TrainerWorkloadMapper.class);

    @Named("toTrainerWorkload")
    @Mapping(source = "trainerUsername", target = "trainerUsername")
    @Mapping(source = "trainerFirstName", target = "trainerFirstName")
    @Mapping(source = "trainerLastName", target = "trainerLastName")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "trainingDuration", target = "trainingDuration")
    @Mapping(target = "actionType", ignore = true)
    TrainerWorkload toTrainerWorkload(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO);


    @Named("toTrainerWorkloadResponseDTO")
    @Mapping(source = "trainerUsername", target = "trainerUsername")
    @Mapping(source = "trainerFirstName", target = "trainerFirstName")
    @Mapping(source = "trainerLastName", target = "trainerLastName")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "trainingDuration", target = "trainingDuration")
    TrainerWorkloadResponseDTO toTrainerWorkloadResponseDTO(TrainerWorkload trainerWorkload);
}
