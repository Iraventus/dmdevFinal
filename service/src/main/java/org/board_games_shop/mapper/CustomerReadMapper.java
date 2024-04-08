package org.board_games_shop.mapper;

import org.board_games_shop.dto.CustomerReadDto;
import org.board_games_shop.entity.users.Customer;
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
                object.getRole(),
                object.getBirthDate(),
                object.getPhone(),
                object.getAddress()
        );
    }
}
