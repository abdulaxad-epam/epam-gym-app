package epam.mapper;

import epam.entity.User;
import epam.dto.request_dto.UserRequestDTO;
import epam.dto.response_dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Named("toUserResponseDTO")
    @Mappings({
            @Mapping(source = "firstname", target = "firstName"),
            @Mapping(source = "lastname", target = "lastName"),
            @Mapping(source = "isActive", target = "isActive"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "username", target = "username")
    })
    UserResponseDTO toUserResponseDTO(User user);

    @Named("toUser")
    @Mappings({
            @Mapping(source = "userRequestDTO.firstName", target = "firstname"),
            @Mapping(source = "userRequestDTO.lastName", target = "lastname"),
            @Mapping(source = "userRequestDTO.isActive", target = "isActive"),
            @Mapping(source = "userRequestDTO.password", target = "password")
    })
    User toUser(UserRequestDTO userRequestDTO);
}
