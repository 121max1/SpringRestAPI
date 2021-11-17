package com.ssu.maxshkodin.springrest.repository;

import com.ssu.maxshkodin.springrest.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {


}
