package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.mapper.ProducerCreateEditMapper;
import org.bgs.repository.ProducerRepository;
import org.bgs.dto.ProducerCreateEditDto;
import org.bgs.dto.ProducerReadDto;
import org.bgs.mapper.ProducerReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProducerService {

    private final ProducerCreateEditMapper producerCreateEditMapper;
    private final ProducerReadMapper producerReadMapper;
    private final ProducerRepository producerRepository;

    public List<ProducerReadDto> findAll() {
        return producerRepository.findAll().stream()
                .map(producerReadMapper::map)
                .toList();
    }

    public Optional<ProducerReadDto> findById(Long id) {
        return producerRepository.findById(id)
                .map(producerReadMapper::map);
    }

    public Optional<ProducerReadDto> findByAccessoriesName(String name) {
        return producerRepository.findProducerByAccessoriesName(name)
                .map(producerReadMapper::map);
    }

    @Transactional
    public ProducerReadDto create(ProducerCreateEditDto producerCreateEditDto) {
        return Optional.of(producerCreateEditDto)
                .map(producerCreateEditMapper::map)
                .map(producerRepository::save)
                .map(producerReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProducerReadDto> update(Long id, ProducerCreateEditDto producerCreateEditDto) {
        return producerRepository.findById(id)
                .map(entity -> producerCreateEditMapper.map(producerCreateEditDto, entity))
                .map(producerRepository::saveAndFlush)
                .map(producerReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return producerRepository.findById(id)
                .map(entity -> {
                    producerRepository.delete(entity);
                    producerRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
