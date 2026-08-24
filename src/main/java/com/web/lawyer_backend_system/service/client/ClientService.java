package com.web.lawyer_backend_system.service.client;

import com.web.lawyer_backend_system.dto.client.ClientRequestDto;
import com.web.lawyer_backend_system.dto.client.ClientResponseDto;
import com.web.lawyer_backend_system.entity.Client;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.mapper.client.ClientMapper;
import com.web.lawyer_backend_system.repository.ClientRepository;
import com.web.lawyer_backend_system.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
         clientRepository.deleteByClientId(clientId);
    }

    public ClientResponseDto getClientById(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }

    public Page<ClientResponseDto> getAllClients(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        return clients.map(clientMapper::toClientResponseDto);
    }

    public ClientResponseDto getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + email));
    }

    public ClientResponseDto getClientByNationalNumber(BigInteger nationalNumber) {
        return clientRepository.findByNationalNumber(nationalNumber)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with national number: " + nationalNumber));
    }

    public ClientResponseDto getClientByPhoneNumber(BigInteger phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with phone number: " + phoneNumber));
    }

    public ClientResponseDto getClientByFullNameAndPhoneNumber(String fullName, BigInteger phoneNumber) {
        return clientRepository.findByFullNameAndPhoneNumber(fullName, phoneNumber)
                .map(clientMapper::toClientResponseDto)
                .orElseThrow(() -> new RuntimeException("Client not found with full name: " + fullName + " and phone number: " + phoneNumber));
    }

    public List<ClientResponseDto> getClientByFullName(String fullName) {
        List<Client> clients = clientRepository.findByFullName(fullName)
                .orElseThrow(() -> new RuntimeException("No clients found with full name: " + fullName));
        return clients.stream()
                .map(clientMapper::toClientResponseDto)
                .collect(Collectors.toList());
    }

    public Page<ClientResponseDto> getAllClientsByLawyer(String lawyerId, Pageable pageable) {
        User lawyer = userService.findLawyer(lawyerId);
        return clientRepository.findAllByLawyer(lawyer, pageable)
                .map(clientMapper::toClientResponseDto);
    }

    public ClientResponseDto getClientByLawyerAndFullName(String lawyerId, String clientFullName) {
        User lawyer = userService.findLawyer(lawyerId);
        Client client = clientRepository.findByLawyerAndFullNameContainingIgnoreCase(lawyer, clientFullName)
                .orElseThrow(() -> new RuntimeException("Client not found with lawyer id: " + lawyerId + " and full name: " + clientFullName));
        return clientMapper.toClientResponseDto(client);
    }

}
