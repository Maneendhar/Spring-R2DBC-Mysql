package com.example.demo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldXRepository extends ReactiveCrudRepository<city, Long> {

}
