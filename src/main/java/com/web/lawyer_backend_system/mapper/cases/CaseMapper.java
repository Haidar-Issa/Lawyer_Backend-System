package com.web.lawyer_backend_system.mapper.cases;

import com.web.lawyer_backend_system.dto.cases.CaseRequestDto;
import com.web.lawyer_backend_system.dto.cases.CaseResponseDto;
import com.web.lawyer_backend_system.entity.Case_;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.mapper.client.ClientMapper;
import com.web.lawyer_backend_system.mapper.user.UserMapper;
import com.web.lawyer_backend_system.repository.ClientRepository;
import com.web.lawyer_backend_system.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {ClientMapper.class, UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CaseMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedLawyer", source = "assignedLawyer")
    @Mapping(target = "client", source = "client")
    public abstract Case_ toCaseEntity(CaseRequestDto caseRequestDto);

    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedLawyer", source = "assignedLawyer")
    @Mapping(target = "client", source = "client")
    public abstract CaseResponseDto toCaseResponseDto(Case_ caseEntity);

    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "assignedLawyer", source = "assignedLawyer")
    @Mapping(target = "client", source = "client")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(CaseRequestDto caseRequestDto, @MappingTarget Case_ caseEntity);

    protected User mapStringToUser(String userId) {
        if (userId == null || userId.isBlank()) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }

    protected Client mapStringToClient(String clientId) {
        if (clientId == null || clientId.isBlank()) {
            return null;
        }
        return clientRepository.findById(clientId).orElse(null);
    }

    protected String mapUserToId(User user) {
        return user == null ? null : user.getUserId();
    }

    protected String mapClientToId(Client client) {
        return client == null ? null : client.getClientId();
    }
}
