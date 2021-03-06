package com.opticalshopall.app.service;

import com.opticalshopall.app.domain.OrderItem;
import com.opticalshopall.app.repository.OrderItemRepository;
import com.opticalshopall.app.repository.search.OrderItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderItem.
 */
@Service
@Transactional
public class OrderItemService {

    private final Logger log = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    private final OrderItemSearchRepository orderItemSearchRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemSearchRepository orderItemSearchRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemSearchRepository = orderItemSearchRepository;
    }

    /**
     * Save a orderItem.
     *
     * @param orderItem the entity to save
     * @return the persisted entity
     */
    public OrderItem save(OrderItem orderItem) {
        log.debug("Request to save OrderItem : {}", orderItem);
        OrderItem result = orderItemRepository.save(orderItem);
        orderItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the orderItems.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {
        log.debug("Request to get all OrderItems");
        return orderItemRepository.findAll();
    }


    /**
     * Get one orderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OrderItem> findOne(Long id) {
        log.debug("Request to get OrderItem : {}", id);
        return orderItemRepository.findById(id);
    }

    /**
     * Delete the orderItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);
        orderItemRepository.deleteById(id);
        orderItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the orderItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderItem> search(String query) {
        log.debug("Request to search OrderItems for query {}", query);
        return StreamSupport
            .stream(orderItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
