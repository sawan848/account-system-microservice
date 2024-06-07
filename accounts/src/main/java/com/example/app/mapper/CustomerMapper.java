package com.example.app.mapper;

import com.example.app.dto.AccountsDto;
import com.example.app.dto.CustomerDto;
import com.example.app.entity.Accounts;
import com.example.app.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomers(CustomerDto customerDto, Customer customer){
       customer.setName(customerDto.getName());
       customer.setEmail(customerDto.getEmail());
       customer.setMobileNumber(customerDto.getMobileNumber());

        return customer;
    }


      public static CustomerDto mapToCustomersDto(Customer customer,CustomerDto customerDto){
          customerDto.setName(customer.getName());
          customerDto.setEmail(customer.getEmail());
          customerDto.setMobileNumber(customer.getMobileNumber());


          return customerDto;
    }



}
