package epam.training.mapper;


import epam.training_type.entity.TrainingType;
import epam.trainee.entity.Trainee;
import epam.trainee.mapper.TraineeMapper;
import epam.trainer.entity.Trainer;
import epam.trainer.mapper.TrainerMapper;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {TrainerMapper.class, TraineeMapper.class})
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Named("toTrainingResponseDTO")
    @Mappings({
            @Mapping(source = "trainee", target = "trainee", qualifiedByName = "toTraineeResponseDTO"),
            @Mapping(source = "trainer", target = "trainer", qualifiedByName = "toTrainerResponseDTO"),
            @Mapping(source = "trainingType.description", target = "trainingType")
    })
    TrainingResponseDTO toTrainingResponseDTO(Training training);

    @Named("toTraining")
    @Mappings({
            @Mapping(source = "trainingRequestDTO.trainingDate", target = "trainingDate"),
            @Mapping(source = "trainingRequestDTO.trainingDuration", target = "trainingDuration"),
            @Mapping(source = "trainingRequestDTO.trainingName", target = "trainingName"),
            @Mapping(source = "trainingType", target = "trainingType"),
            @Mapping(source = "trainer", target = "trainer"),
            @Mapping(source = "trainee", target = "trainee")
    })
    Training toTraining(TrainingRequestDTO trainingRequestDTO, TrainingType trainingType, Trainer trainer, Trainee trainee);
}
