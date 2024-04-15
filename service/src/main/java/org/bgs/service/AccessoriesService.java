package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.repository.AccessoriesRepository;
import org.bgs.dto.AccessoriesCreateEditDto;
import org.bgs.dto.AccessoriesReadDto;
import org.bgs.mapper.AccessoriesCreateEditMapper;
import org.bgs.mapper.AccessoriesReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessoriesService {

    private final AccessoriesCreateEditMapper accessoriesCreateEditMapper;
    private final AccessoriesReadMapper accessoriesReadMapper;
    private final AccessoriesRepository accessoriesRepository;

    public List<AccessoriesReadDto> findAll() {
        return accessoriesRepository.findAll().stream()
                .map(accessoriesReadMapper::map)
                .toList();
    }

    public Optional<AccessoriesReadDto> findById(Long id) {
        return accessoriesRepository.findById(id)
                .map(accessoriesReadMapper::map);
    }

    @Transactional
    public AccessoriesReadDto create(AccessoriesCreateEditDto accessoriesCreateEditDto) {
        return Optional.of(accessoriesCreateEditDto)
                .map(accessoriesCreateEditMapper::map)
                .map(accessoriesRepository::save)
                .map(accessoriesReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccessoriesReadDto> update(Long id, AccessoriesCreateEditDto accessoriesCreateEditDto) {
        return accessoriesRepository.findById(id)
                .map(entity -> accessoriesCreateEditMapper.map(accessoriesCreateEditDto, entity))
                .map(accessoriesRepository::saveAndFlush)
                .map(accessoriesReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return accessoriesRepository.findById(id)
                .map(entity -> {
                    accessoriesRepository.delete(entity);
                    accessoriesRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
