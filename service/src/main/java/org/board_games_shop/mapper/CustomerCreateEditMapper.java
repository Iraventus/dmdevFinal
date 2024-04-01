package org.board_games_shop.mapper;

import org.board_games_shop.dto.CustomerCreateEditDto;
import org.board_games_shop.entity.users.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateEditMapper implements Mapper<CustomerCreateEditDto, Customer> {

    @Override
    public Customer map(CustomerCreateEditDto fromObject, Customer toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Customer map(CustomerCreateEditDto object) {
        Customer customer = new Customer();
        copy(object, customer);
        return customer;
    }

    private void copy(CustomerCreateEditDto object, Customer customer) {
        customer.setLogin(object.getLogin());
        customer.setAddress(object.getAddress());
        customer.setFirstname(object.getFirstname());
        customer.setPassword(object.getPassword());
        customer.setLastname(object.getLastname());
        customer.setPhone(object.getPhone());
        customer.setBirthDate(object.getBirthDate());
    }
}
