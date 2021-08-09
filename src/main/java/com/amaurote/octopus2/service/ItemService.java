package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.Item;
import com.amaurote.octopus2.dto.ItemDTO;
import com.amaurote.octopus2.exception.ItemException;
import com.amaurote.octopus2.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Random;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final RatingService ratingService;

    public ItemService(ItemRepository itemRepository, RatingService ratingService) {
        this.itemRepository = itemRepository;
        this.ratingService = ratingService;
    }

    @Transactional
    public ItemDTO getItemByCatalogNumber(String catId) {
        var item = itemRepository.findOneByCatalogId(catId).orElseThrow(() -> new ItemException("Item not found."));
        var dto = itemToDTO(item);
        dto.setRating(ratingService.getRatingByItem(item));
        return dto;
    }

    public ItemDTO addNewItem(ItemDTO dto) {
        Item newItem = Item.builder()
                .catalogId(generateCatalogId())
                .name(dto.getName())
                .description(dto.getDescription())
                .dateAdded(Instant.now())
                .build();

        return itemToDTO(itemRepository.save(newItem));
    }

    private ItemDTO itemToDTO(Item item) {
        return ItemDTO.builder()
                .catalogId(item.getCatalogId())
                .name(item.getName())
                .description(item.getDescription())
                .dateAdded(item.getDateAdded())
                .build();
    }

    private String generateCatalogId() {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }

        int low = 100000;
        int high = 999999;
        sb.append(random.nextInt(high - low) + low);

        return sb.toString();
    }
}
