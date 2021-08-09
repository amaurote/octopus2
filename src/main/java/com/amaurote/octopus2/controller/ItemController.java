package com.amaurote.octopus2.controller;

import com.amaurote.octopus2.dto.ItemDTO;
import com.amaurote.octopus2.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/item")
@Validated
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<?> getItem(@RequestParam(name = "catid") @NotNull @NotBlank String catalogId) {
        return new ResponseEntity<>(itemService.getItemByCatalogNumber(catalogId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR')")
    @PostMapping(value = "/new")
    public ResponseEntity<?> addNewItem(@RequestBody ItemDTO dto) {
        ItemDTO newItem = itemService.addNewItem(dto);
        return new ResponseEntity<>(newItem, HttpStatus.OK);
    }

}
