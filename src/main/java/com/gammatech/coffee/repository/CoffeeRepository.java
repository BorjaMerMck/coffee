package com.gammatech.coffee.repository;
import com.gammatech.coffee.models.Coffee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    boolean existsByName(String name);

}