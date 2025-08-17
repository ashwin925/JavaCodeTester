package com.yourcompany.mysimpleapi.repository;

import com.yourcompany.mysimpleapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
