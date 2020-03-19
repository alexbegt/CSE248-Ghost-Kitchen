package com.alexbegt.ghostkitchen.transformer;

import com.alexbegt.ghostkitchen.entity.ItemEntity;
import com.alexbegt.ghostkitchen.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
  ItemEntity mapItemToItemEntity(Item item);

  Item mapItemEntityToItem(ItemEntity itemEntity);

  List<Item> mapItemEntityListToUserList(List<ItemEntity> itemEntities);
}
