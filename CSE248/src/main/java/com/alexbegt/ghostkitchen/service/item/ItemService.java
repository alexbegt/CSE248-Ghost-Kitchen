package com.alexbegt.ghostkitchen.service.item;

import com.alexbegt.ghostkitchen.model.Item;

import java.util.List;

public interface ItemService {
  void createItem(Item user);

  Item getItemById(Long id);

  void deleteItemById(Long id);

  Item getItemByName(String name);

  List<Item> getAllItems();
}
