package com.ssu.maxshkodin.springrest.repository;

import com.ssu.maxshkodin.springrest.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record,Integer> {

}
