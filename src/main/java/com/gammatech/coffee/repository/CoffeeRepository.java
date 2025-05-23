package com.gammatech.coffee.repository;

import com.gammatech.coffee.models.Coffee;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoffeeRepository  {

    private final Map<Long, Coffee> coffes = new HashMap<>();
    // clave: id, valor: coffee
    // campo unico: name

    public Coffee save(Coffee coffee) {
        coffes.put(coffee.getId(), coffee);
        return coffes.get(coffee.getId());
    }

    public Coffee delete(Long coffeeId) {
        return coffes.remove(coffeeId);
    }

    public List<Coffee> getAll() {
        return List.copyOf(coffes.values());
    }

    public Coffee getById(Long coffeeId) {
        return coffes.get(coffeeId);
    }

    // Returna true si se encuentra en la coleccion de los cafes.
    public boolean existsById(Long id) {
        return coffes.containsKey(id);
    }

    public boolean existsByName(String name) {
        return coffes.values().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name));
    }
}