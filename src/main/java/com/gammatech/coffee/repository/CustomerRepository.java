package com.gammatech.coffee.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gammatech.coffee.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  boolean existsByEmail(String email);

  Customer findByEmail(String email);
}
