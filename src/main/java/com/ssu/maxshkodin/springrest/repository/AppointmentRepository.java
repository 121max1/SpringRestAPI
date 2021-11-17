package com.ssu.maxshkodin.springrest.repository;

import com.ssu.maxshkodin.springrest.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Integer> {
}
