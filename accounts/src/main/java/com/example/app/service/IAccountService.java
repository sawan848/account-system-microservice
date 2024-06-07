package com.example.app.service;

import com.example.app.dto.CustomerDto;
import com.example.app.entity.Customer;

import java.util.List;

public interface IAccountService {
    void saveAccount(CustomerDto customerDto);
    CustomerDto findCustomerDetailsByMobileNumber(String mobileNumber);
    boolean deleteAccountByMobileNumber(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);

}
