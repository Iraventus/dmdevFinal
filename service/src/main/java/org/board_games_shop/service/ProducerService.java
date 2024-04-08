package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.ProducerCreateEditDto;
import org.board_games_shop.dto.ProducerReadDto;
import org.board_games_shop.mapper.ProducerCreateEditMapper;
import org.board_games_shop.mapper.ProducerReadMapper;
import org.board_games_shop.repository.ProducerRepository;
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
