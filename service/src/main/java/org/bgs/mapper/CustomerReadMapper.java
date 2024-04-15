package org.bgs.mapper;

import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.users.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    @Override
    public CustomerReadDto map(Customer object) {
        return new CustomerReadDto(
                object.getId(),
                object.getLogin(),
                object.getPassword(),
                object.getFirstname(),
                object.getLastname(),
                object.getPhone(),
                object.getRole(),
                object.getBirthDate(),
                object.getAddress()
        );
    }
}
