package com.web.lawyer_backend_system.service.client;

import com.web.lawyer_backend_system.dto.client.ClientFilterDto;
import com.web.lawyer_backend_system.dto.client.ClientRequestDto;
import com.web.lawyer_backend_system.dto.client.ClientResponseDto;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.client.ClientMapper;
import com.web.lawyer_backend_system.repository.ClientRepository;
import com.web.lawyer_backend_system.repository.specification.ClientSpecifications;
import com.web.lawyer_backend_system.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;

    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        Client client = clientMapper.toClientEntity(clientRequestDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientResponseDto(savedClient);
    }

    public void deleteClient(String clientId) {
        if(!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("client not found with id: " + clientId);
        }
         clientRepository.deleteByClientId(clientId);
    }

    public ClientResponseDto getClientById(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }



    public Page<ClientResponseDto> filterClients(ClientFilterDto filterDto, Pageable pageable) {
        Specification<Client> spec = Specification
                .where(ClientSpecifications.hasClientId(filterDto.getClientId()))
                .and(ClientSpecifications.containsFullName(filterDto.getFullName()))
                .and(ClientSpecifications.hasEmail(filterDto.getEmail()))
                .and(ClientSpecifications.hasNationalNumber(filterDto.getNationalNumber()))
                .and(ClientSpecifications.hasPhoneNumber(filterDto.getPhoneNumber()))
                .and(ClientSpecifications.hasLawyerId(filterDto.getLawyerId()));

        return clientRepository.findAll(spec, pageable)
                .map(clientMapper::toClientResponseDto);
    }

}
