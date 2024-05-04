package org.bgs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bgs.dto.CustomerReadDto;
import org.bgs.dto.OrderCreateEditDto;
import org.bgs.dto.OrderReadDto;
import org.bgs.entity.Order;
import org.bgs.entity.Status;
import org.bgs.mapper.OrderCreateEditMapper;
import org.bgs.mapper.OrderReadMapper;
import org.bgs.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

    private final OrderCreateEditMapper orderCreateEditMapper;
    private final OrderReadMapper orderReadMapper;
    private final OrderRepository orderRepository;

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAllByUserLoginAndStatus(String userLogin, Status status) {
        return orderRepository.findAllByUserLoginAndStatus(userLogin, status).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderCreateEditDto) {
        return Optional.of(orderCreateEditDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public OrderReadDto createOrder(CustomerReadDto customer) {
        OrderCreateEditDto orderCreateEditDto = new OrderCreateEditDto(customer.getId(), null);
        return Optional.of(orderCreateEditDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto orderCreateEditDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderCreateEditDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public void setStatus(Long orderId, Status status) {
        orderRepository.findById(orderId).
                ifPresent(currentOrder -> currentOrder.setStatus(status));
        if (status == Status.RESERVED) {
            orderRepository.findById(orderId).
                    ifPresent(currentOrder ->
                            currentOrder.setReservationEndDate(Instant.now().plus(2, DAYS)));
        }
        orderRepository.saveAndFlush(orderRepository.findById(orderId).orElseThrow());
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Scheduled(cron = "1 * * * * *")
    public void someMethod() {
        long count = orderRepository.count();
        log.info("Orders count :   {}", count);
        var time = Instant.now().minus(2, HOURS);
        List<Order> list = orderRepository
                .findAllByReservationEndDateBeforeAndStatus(time, Status.RESERVED);
        log.info("Orders gonna to delete -> {}", list.stream()
                .map(Order::toString)
                .collect(Collectors.joining(", ", "[", "]")));
        orderRepository.deleteAll(list);
        log.info("Orders difference :   {}", count - orderRepository.count());
    }
}
