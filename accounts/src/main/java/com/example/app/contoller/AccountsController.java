package com.example.app.contoller;

import com.example.app.constant.AccountConstants;
import com.example.app.dto.CustomerDto;
import com.example.app.dto.ResponseDto;
import com.example.app.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.app.constant.AccountConstants.*;

@Tag(name = "CRUD REST APIs for Accounts",
description = "CRUD REST APIs CREATE,UPDATE,FETCH AND DELETE account details")
@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private final IAccountService accountService;

    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer & Account details based on a account number "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDto(STATUS_500, AccountConstants.MESSAGE_500));
        }
    }
    @Operation(
            summary = "Delete Account Details REST API",
            description = "REST API to delete Customer & Account details based on a mobile  number "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccountByMobileNumber(mobileNumber);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ResponseDto(STATUS_500, MESSAGE_500));
        }
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer & Account details based on a mobile number "
    )
    @ApiResponse(responseCode = "200",description = "HTTP Status OK")
    @GetMapping("/account")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        CustomerDto customerDto = accountService.findCustomerDetailsByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account "
    )
    @ApiResponse(responseCode = "201",description = "HTTP Status CREATED")
    @PostMapping("/save")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountService.saveAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }
}
