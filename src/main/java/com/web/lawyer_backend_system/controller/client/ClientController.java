package com.web.lawyer_backend_system.controller.client;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.client.ClientRequestDto;
import com.web.lawyer_backend_system.dto.client.ClientResponseDto;
import com.web.lawyer_backend_system.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final String path = "/api/clients";

    @PreAuthorize(value = "hasRole('LAWYER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ClientResponseDto>> createClient(@RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto clientResponseDto = clientService.createClient(clientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create(
                        HttpStatus.CREATED,
                        "Client created successfully",
                        clientResponseDto,
                        path + "/create"
                )
        );
    }


//    @PreAuthorize(value = "hasRole('LAWYER') or hasRole('ADMIN')")
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<ApiResponse<?>> deleteClient(@PathVariable String clientId) {
         clientService.deleteClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client deleted successfully",
                        null,
                        path + "/delete/" + clientId
                )
        );
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(@PathVariable String clientId) {
        ClientResponseDto clientResponseDto = clientService.getClientById(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/get/" + clientId
                )
        );
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllClients(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        var clients = clientService.getAllClients(org.springframework.data.domain.PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Clients retrieved successfully",
                clients,
                path + "/get-all"
        ));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByEmail(@PathVariable String email) {
        ClientResponseDto clientResponseDto = clientService.getClientByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/email/" + email
                )
        );
    }

    @GetMapping("/national-number/{nationalNumber}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByNationalNumber(@PathVariable String nationalNumber) {
        ClientResponseDto clientResponseDto = clientService.getClientByNationalNumber(new java.math.BigInteger(nationalNumber));
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/national-number/" + nationalNumber
                )
        );
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByPhoneNumber(@PathVariable String phoneNumber) {
        ClientResponseDto clientResponseDto = clientService.getClientByPhoneNumber(new java.math.BigInteger(phoneNumber));
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/phone-number/" + phoneNumber
                )
        );
    }

    @GetMapping("/full-name-and-phone-number")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByFullNameAndPhoneNumber(
            @RequestParam String fullName,
            @RequestParam String phoneNumber) {
        ClientResponseDto clientResponseDto = clientService.getClientByFullNameAndPhoneNumber(fullName, new java.math.BigInteger(phoneNumber));
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/full-name-and-phone-number?fullName=" + fullName + "&phoneNumber=" + phoneNumber
                )
        );
    }

    @GetMapping("/get/full-name/{fullName}")
    public ResponseEntity<ApiResponse<?>> getClientByFullName(@PathVariable String fullName) {
        var clients = clientService.getClientByFullName(fullName);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Clients retrieved successfully",
                clients,
                path + "/get/full-name/" + fullName
        ));
    }

    @GetMapping("/lawyer/{lawyerId}/get-all")
    public ResponseEntity<ApiResponse<?>> getAllClientsByLawyer(
            @PathVariable String lawyerId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        var clients = clientService.getAllClientsByLawyer(lawyerId, org.springframework.data.domain.PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Clients retrieved successfully",
                clients,
                path + "/lawyer/" + lawyerId + "/get-all"
        ));
    }

    @GetMapping("/lawyer/{lawyerId}/full-name/{clientFullName}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientByLawyerAndFullName(
            @PathVariable String lawyerId,
            @PathVariable String clientFullName) {
        ClientResponseDto clientResponseDto = clientService.getClientByLawyerAndFullName(lawyerId, clientFullName);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.create(
                        HttpStatus.OK,
                        "Client retrieved successfully",
                        clientResponseDto,
                        path + "/lawyer/" + lawyerId + "/full-name/" + clientFullName
                )
        );
    }
}
