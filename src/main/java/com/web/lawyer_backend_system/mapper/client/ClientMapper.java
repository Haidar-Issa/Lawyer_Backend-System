package com.web.lawyer_backend_system.mapper.client;

import com.web.lawyer_backend_system.dto.client.ClientRequestDto;
import com.web.lawyer_backend_system.dto.client.ClientResponseDto;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ClientMapper {

    @Mapping(source = "lawyer", target = "userId")
    ClientResponseDto toClientResponseDto(Client client);

    @Mapping(source = "userId", target = "lawyer.userId")
    Client toClientEntity(ClientRequestDto clientRequestDto);
}
