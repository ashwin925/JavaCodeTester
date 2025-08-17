package com.yourcompany.mysimpleapi;

import com.yourcompany.mysimpleapi.model.Item;
import com.yourcompany.mysimpleapi.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    public void resetDb() {
        itemRepository.deleteAll();
    }

    @Test
    @BeforeEach
    public void setup() {
        itemRepository.deleteAll();
    }

    @Test
    public void whenGetItems_thenReturnsListOfItems() {
                // given
        itemRepository.save(new Item(null, "Item 1", "Desc 1"));

        // when
        ResponseEntity<Item[]> response = restTemplate.getForEntity("/items", Item[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
         if (response.getBody() != null && response.getBody().length > 0) {
            assertThat(response.getBody()[0].getName()).isEqualTo("Item 1");
        }
    }

    @Test
    public void whenPostItem_thenItemIsCreated() {

        // given
        Item item = new Item(null, "New Item", "New Desc");

        // when
        ResponseEntity<Item> response = restTemplate.postForEntity("/items", item, Item.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("New Item");
        assertThat(itemRepository.count()).isEqualTo(1);
    }
}
