package com.ssu.maxshkodin.springrest.controller;


import com.ssu.maxshkodin.springrest.models.Appointment;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Record;
import com.ssu.maxshkodin.springrest.services.AppointmentService;
import com.ssu.maxshkodin.springrest.services.DoctorService;
import com.ssu.maxshkodin.springrest.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    RecordService recordService;

    @GetMapping(value = "/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.getDoctorByUsername(username);
        List<Appointment> appointments = appointmentService.getByDoctorId(doctor.getId());
        return appointments != null && !appointments.isEmpty()
                ? new ResponseEntity<>(appointments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/appointments/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Integer id,
                                              @RequestParam(value = "description") String description){
        Appointment appointment = appointmentService.getById(id);
        appointment.setDescription(description);
        appointmentService.update(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/records")
    public ResponseEntity<List<Record>> getRecords(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorService.getDoctorByUsername(username);
        List<Record> records = recordService.getRecordsByDoctor(doctor);
        return records!=null && !records.isEmpty()
            ? new ResponseEntity<>(records,HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
