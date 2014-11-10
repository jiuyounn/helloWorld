package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ItemService {
	
	Item getById(Long item_id);

    Item getByName(String name);

	void delete(Item item);
	
	List<Item> findAll();

    List<Item> getItemsWithNonZeroQuantity();

	void saveAll(List<Item> items);

    void deleteItems(List<Item> items);

    void decreaseQuantity(Item item, Long quantity);

    ServiceResult<Item> saveItem(Item item);

    boolean checkItemsQuantityIsMoreThanZero(long itemId);
}
