package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.model.ReserveOrder;

import java.util.List;

public interface ReserveOrderService {

    void save(ReserveOrder reserveOrder);

    List<ReserveOrder> findAllOrdersByAccountId(Long account_id);

    List<ReserveOrder> getAllOrdersByAccount();
}