package com.ssu.maxshkodin.springrest.services.impl;

import com.ssu.maxshkodin.springrest.models.Appointment;
import com.ssu.maxshkodin.springrest.repository.AppointmentRepository;
import com.ssu.maxshkodin.springrest.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment add(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getById(int id) {
        return  appointmentRepository.getOne(id);
    }

    @Override
    public List<Appointment> getByClientId(int id) {
        return appointmentRepository.findAll().stream().filter(a->a.getRecord().getClient().getId() == id).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getByDoctorId(int id) {
        return appointmentRepository.findAll().stream().filter(a->a.getRecord().getDoctor().getId() == id).collect(Collectors.toList());
    }


}
