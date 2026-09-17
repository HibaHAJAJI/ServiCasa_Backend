package ServiCasa.mapper;


import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.request.UserRegisterRequest;
import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterRequest dto);

    UserResponse toDto(User user);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "email",ignore = true)
    @Mapping(target = "role",ignore = true)
    void updateUserDto(UserUpdateRequestDTO dto, @MappingTarget User user);

}
