package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ManagerCreateEditDto;
import org.bgs.entity.Role;
import org.bgs.entity.users.Manager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManagerCreateEditMapper implements Mapper<ManagerCreateEditDto, Manager> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Manager map(ManagerCreateEditDto object) {
        Manager manager = new Manager();
        copy(object, manager);
        return manager;
    }

    @Override
    public Manager map(ManagerCreateEditDto fromObject, Manager toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ManagerCreateEditDto object, Manager manager) {
        manager.setLogin(object.getLogin());
        manager.setJobTitle(object.getJobTitle());
        manager.setFirstname(object.getFirstname());
        manager.setLastname(object.getLastname());
        manager.setPhone(object.getPhone());
        manager.setBirthDate(object.getBirthDate());
        manager.setRole(Role.MANAGER);

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(manager::setPassword);
    }
}
