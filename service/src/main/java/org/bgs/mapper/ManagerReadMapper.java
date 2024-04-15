package org.bgs.mapper;

import org.bgs.dto.ManagerReadDto;
import org.bgs.entity.users.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerReadMapper implements Mapper<Manager, ManagerReadDto> {

    @Override
    public ManagerReadDto map(Manager object) {
        return new ManagerReadDto(
                object.getId(),
                object.getLogin(),
                object.getPassword(),
                object.getFirstname(),
                object.getLastname(),
                object.getPhone(),
                object.getRole(),
                object.getBirthDate(),
                object.getJobTitle()
        );
    }
}
