package com.yourcompany.mysimpleapi.controller;

import com.yourcompany.mysimpleapi.controller.MyController;
import com.yourcompany.mysimpleapi.model.Item;
import com.yourcompany.mysimpleapi.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyController.class)
public class MyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void givenItems_whenGetItems_thenReturnsJsonArray() throws Exception {
        Item item1 = new Item(1L, "Item 1", "Description 1");
        Item item2 = new Item(2L, "Item 2", "Description 2");
        List<Item> allItems = Arrays.asList(item1, item2);

        given(itemService.getAllItems()).willReturn(allItems);

        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(item1.getName())));
    }

    @Test
    public void whenGetItemById_thenReturnsItem() throws Exception {
        Item item = new Item(1L, "Test Item", "Test Desc");
        given(itemService.getItemById(1L)).willReturn(Optional.of(item));

        mvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(item.getName())));
    }
}
