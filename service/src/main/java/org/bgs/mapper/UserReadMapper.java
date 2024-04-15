package org.bgs.mapper;

import org.bgs.dto.UserReadDto;
import org.bgs.entity.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getLogin(),
                object.getPassword(),
                object.getFirstname(),
                object.getLastname(),
                object.getPhone(),
                object.getRole(),
                object.getBirthDate()
        );
    }
}
