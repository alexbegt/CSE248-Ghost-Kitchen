package com.alexbegt.ghostkitchen.service.item;

import com.alexbegt.ghostkitchen.model.Item;
import com.alexbegt.ghostkitchen.repository.ItemEntityRepository;
import com.alexbegt.ghostkitchen.transformer.ItemMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {

  private final ItemEntityRepository repository;
  private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

  @Override
  public void createItem(Item item) {
    repository.save(mapper.mapItemToItemEntity(item));
  }

  @Override
  public Item getItemById(Long id) {
    return mapper.mapItemEntityToItem(repository.findById(id).orElse(null));
  }

  @Override
  public void deleteItemById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<Item> getAllItems() {
    return mapper.mapItemEntityListToUserList(repository.findAll());
  }

  @Override
  public Item getItemByName(String name) {
    return mapper.mapItemEntityToItem(repository.findByName(name).orElse(null));
  }
}
