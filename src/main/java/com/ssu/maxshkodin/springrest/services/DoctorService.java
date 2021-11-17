package com.ssu.maxshkodin.springrest.services;


import com.ssu.maxshkodin.springrest.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    Doctor add(Doctor doctor);

    void delete(int id);

    Doctor update(Doctor doctor);

    Doctor getById(int id);

    List<Doctor> getAll();

    Doctor getDoctorByUsername(String username);
}
