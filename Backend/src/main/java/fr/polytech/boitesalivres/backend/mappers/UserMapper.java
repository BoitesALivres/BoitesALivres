package fr.polytech.boitesalivres.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import fr.polytech.boitesalivres.backend.dtos.AuthenticationResponse;
import fr.polytech.boitesalivres.backend.dtos.UserRequest;
import fr.polytech.boitesalivres.backend.dtos.UserResponse;
import fr.polytech.boitesalivres.backend.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))")
    AuthenticationResponse toAuthenticationResponse(User user);

    UserResponse toResponse(User user);

    User toEntity(UserRequest request);
}