package com.example.SpringBatchExample.repositories;

import com.example.SpringBatchExample.models.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Integer> {

    List<Reading> findByRefNumber(int refNumber);
}
