package com.ssu.maxshkodin.springrest.repository;

import com.ssu.maxshkodin.springrest.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

}
