package com.yourcompany.mysimpleapi.controller;

import com.yourcompany.mysimpleapi.model.Item;
import com.yourcompany.mysimpleapi.repository.ItemRepository;
import com.yourcompany.mysimpleapi.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyControllerTest {

    @Autowired
    private ItemService   itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void givenItems_whenGetItems_thenReturnsJsonArray() throws Exception {
        Item item1 = new Item(null, "Item 1", "Description 1");
        itemRepository.save(item1);
        Item item2 = new Item(null, "Item 2", "Description 2");
        itemRepository.save(item2);

        ResponseEntity<Item[]> response = restTemplate.getForEntity("/items", Item[].class);
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
    }

   @Test
    public void whenGetItemById_thenReturnsItem() throws Exception {
        Item item = new Item(null, "Test Item", "Test Desc");
        itemRepository.save(item);

        ResponseEntity<Item> response = restTemplate.getForEntity("/items/{id}", Item.class, 1L);
                assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
    }
}
