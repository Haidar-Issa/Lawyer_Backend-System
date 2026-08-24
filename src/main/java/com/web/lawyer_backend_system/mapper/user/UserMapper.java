package com.web.lawyer_backend_system.mapper.user;

import com.web.lawyer_backend_system.dto.user.UserRequestDto;
import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    UserResponseDto toUserResponseDto(User user);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "nationalNumber", expression = "java(userRequestDto.getNationalNumber() != null ? userRequestDto.getNationalNumber() : null)")
    User toUserEntity(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserRequestDto userRequestDto,@MappingTarget User user);
}
