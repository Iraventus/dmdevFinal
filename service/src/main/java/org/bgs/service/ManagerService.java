package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ManagerCreateEditDto;
import org.bgs.dto.ManagerReadDto;
import org.bgs.dto.UserFilter;
import org.bgs.mapper.ManagerCreateEditMapper;
import org.bgs.mapper.ManagerReadMapper;
import org.bgs.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerReadMapper managerReadMapper;
    private final ManagerCreateEditMapper managerCreateEditMapper;

    public List<ManagerReadDto> findAll() {
        return managerRepository.findAll().stream()
                .map(managerReadMapper::map)
                .toList();
    }

    public List<ManagerReadDto> findAll(UserFilter filter) {
        return managerRepository.findAllByFilter(filter).stream()
                .map(managerReadMapper::map)
                .toList();
    }

    public Optional<ManagerReadDto> findById(Long id) {
        return managerRepository.findById(id)
                .map(managerReadMapper::map);
    }

    @Transactional
    public ManagerReadDto create(ManagerCreateEditDto managerDto) {
        return Optional.of(managerDto)
                .map(managerCreateEditMapper::map)
                .map(managerRepository::saveAndFlush)
                .map(managerReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ManagerReadDto> update(Long id, ManagerCreateEditDto managerDto) {
        return managerRepository.findById(id)
                .map(entity -> managerCreateEditMapper.map(managerDto, entity))
                .map(managerRepository::saveAndFlush)
                .map(managerReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return managerRepository.findById(id)
                .map(entity -> {
                    managerRepository.delete(entity);
                    managerRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
