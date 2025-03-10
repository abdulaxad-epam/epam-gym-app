package epam.mapper;

import epam.entity.Trainee;
import epam.entity.User;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.TraineeRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TraineeMapper {
    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    @Named("toTraineeResponseDTO")
    @Mappings({
            @Mapping(source = "dateOfBirth", target = "traineeDateOfBirth"),
            @Mapping(source = "user", target = "user", qualifiedByName = "toUserResponseDTO")
    })
    TraineeResponseDTO toTraineeResponseDTO(Trainee trainee);

    @Named("toTrainee")
    @Mappings({
            @Mapping(source = "trainee.user", target = "user", qualifiedByName = "toUser"),
            @Mapping(source = "trainee.address", target = "address"),
            @Mapping(source = "trainee.dateOfBirth", target = "dateOfBirth")
    })
    Trainee toTrainee(TraineeRequestDTO trainee);

    @Named("toTrainee")
    @Mappings({
            @Mapping(source = "userRequestDTO.address", target = "address"),
            @Mapping(source = "userRequestDTO.dateOfBirth", target = "dateOfBirth"),
            @Mapping(source = "connectedUser", target = "user")
    })
    Trainee toTrainee(RegisterTraineeRequestDTO userRequestDTO, User connectedUser);
}

