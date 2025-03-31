package epam.trainee.mapper;

import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTraineeResponseDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.entity.Trainee;
import epam.user.entity.User;
import epam.user.mapper.UserMapper;
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

    @Named("toRegisterTraineeResponseDTO")
    @Mappings({
            @Mapping(source = "user.password", target = "password"),
            @Mapping(source = "dateOfBirth", target = "traineeDateOfBirth"),
            @Mapping(source = "user", target = "user", qualifiedByName = "toUserResponseDTO")
    })
    RegisterTraineeResponseDTO toRegisterTraineeResponseDTO(Trainee insert);
}

