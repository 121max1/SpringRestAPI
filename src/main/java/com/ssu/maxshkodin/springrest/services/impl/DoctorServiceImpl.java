package com.ssu.maxshkodin.springrest.services.impl;

import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.repository.DoctorRepository;
import com.ssu.maxshkodin.springrest.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor add(Doctor doctor) {
         return doctorRepository.save(doctor);
    }

    @Override
    public void delete(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public Doctor update(Doctor doctor) {
       return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getById(int id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorByUsername(String username) {
        return  doctorRepository.findAll().stream().filter(d->d.getLogin().equals(username)).findFirst().orElse(null);
    }
}
