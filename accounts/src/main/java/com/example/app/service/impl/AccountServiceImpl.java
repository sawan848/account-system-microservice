package com.example.app.service.impl;

import com.example.app.constant.AccountConstants;
import com.example.app.dto.AccountsDto;
import com.example.app.dto.CustomerDto;
import com.example.app.entity.Accounts;
import com.example.app.entity.Customer;
import com.example.app.exception.CustomerAlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.mapper.AccountMapper;
import com.example.app.mapper.CustomerMapper;
import com.example.app.repository.AccountRepository;
import com.example.app.repository.CustomerRepository;
import com.example.app.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    @Override
    public void saveAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomers(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException
                    ("Customer already registered with given mobile number " +
                            customerDto.getMobileNumber());
        }
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(createNewAccount(savedCustomer));


    }

    @Override
    public CustomerDto findCustomerDetailsByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).
                orElseThrow(() -> new
                        ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));

        CustomerDto customerDto = CustomerMapper.mapToCustomersDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean deleteAccountByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        System.out.println("customer = " + customer);
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).
                    orElseThrow(()
                            -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));


            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).
                    orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));


            CustomerMapper.mapToCustomers(customerDto, customer);
            customerRepository.save(customer);
            isUpdated=true;

        }


        return isUpdated;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNum = 100000000L + new Random().nextInt(90000000);
        newAccounts.setAccountNumber(randomAccountNum);
        newAccounts.setAccountType(AccountConstants.SAVING);
        newAccounts.setBranchAddress(AccountConstants.ADDRESS);
//        newAccounts.setCreatedAt(LocalDateTime.now());
//        newAccounts.setCreatedBy("Anonymous");
        return newAccounts;
    }


}
