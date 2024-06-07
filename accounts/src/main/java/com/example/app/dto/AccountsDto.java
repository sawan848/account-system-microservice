package com.example.app.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
    @NotEmpty(message = "Account number can not be null or empty")
    private Long accountNumber;

    @NotEmpty(message = "Account Type can not be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch Address can not be null or empty")
    private String branchAddress;
}
