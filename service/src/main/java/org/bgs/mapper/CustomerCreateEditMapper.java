package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CustomerCreateEditDto;
import org.bgs.entity.Role;
import org.bgs.entity.users.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerCreateEditMapper implements Mapper<CustomerCreateEditDto, Customer> {

    private final PasswordEncoder passwordEncoder;

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
        customer.setLastname(object.getLastname());
        customer.setPhone(object.getPhone());
        customer.setBirthDate(object.getBirthDate());
        customer.setRole(Role.CUSTOMER);

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(customer::setPassword);
    }
}
