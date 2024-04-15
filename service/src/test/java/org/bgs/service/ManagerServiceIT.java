package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ManagerCreateEditDto;
import org.bgs.dto.ManagerReadDto;
import org.bgs.entity.Role;
import org.bgs.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class ManagerServiceIT extends BaseIT {

    private static final Long USER_1 = 3L;
    private final ManagerService managerService;

    @Test
    void findAll() {
        List<ManagerReadDto> result = managerService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void create() {
        ManagerCreateEditDto manager = new ManagerCreateEditDto(
                "test@gmail.com",
                "qwertyuiop",
                "UserTestName",
                "UserTestLastName",
                "88005553535",
                Role.MANAGER,
                LocalDate.now(),
                "title");

        ManagerReadDto actualResult = managerService.create(manager);

        assertEquals(manager.getLogin(), actualResult.getLogin());
        assertEquals(manager.getLastname(), actualResult.getLastname());
    }

    @Test
    void update() {
        ManagerCreateEditDto managerCreateEditDto = new ManagerCreateEditDto(
                "test@gmail.com",
                "qwertyuiop",
                "UserTestName",
                "UserTestLastName",
                "88005553535",
                Role.MANAGER,
                LocalDate.now(),
                "title");

        Optional<ManagerReadDto> actualResult = managerService.update(USER_1, managerCreateEditDto);
        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(
                customer -> {
                    assertEquals(managerCreateEditDto.getLogin(), customer.getLogin());
                    assertEquals(managerCreateEditDto.getLastname(), customer.getLastname());
                });
    }

    @Test
    void delete() {
        assertTrue(managerService.delete(USER_1));
    }

    @Test
    void deleteNonExistingManager() {
        assertFalse(managerService.delete(-12L));
    }
}
