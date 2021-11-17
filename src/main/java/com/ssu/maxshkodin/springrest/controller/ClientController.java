package com.ssu.maxshkodin.springrest.controller;

import com.ssu.maxshkodin.springrest.models.*;
import com.ssu.maxshkodin.springrest.services.AppointmentService;
import com.ssu.maxshkodin.springrest.services.ClientService;
import com.ssu.maxshkodin.springrest.services.DoctorService;
import com.ssu.maxshkodin.springrest.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    RecordService recordService;

    @Autowired
    ClientService clientService;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping(value = "/doctors")
    public ResponseEntity<List<Doctor>> getDoctors(){
        List<Doctor> doctors = doctorService.getAll();
        return doctors !=null && !doctors.isEmpty()
                ? new ResponseEntity<>(doctors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/clientRecords")
    public ResponseEntity<List<Record>> getRecords(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByUsername(username);
        List<Record> records = recordService.getRecordsByClient(client);
        return records != null && !records.isEmpty()
                ? new ResponseEntity<>(records, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/addRecord")
    public ResponseEntity<?> addRecord(@RequestParam(value="doctorId") Integer doctorId,
                                      @RequestParam(value= "gap") Long gap,
                                      @RequestParam(value="clientId") Integer clientId){
        Record record = new Record();
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(gap);
        record.setRecordDateTime(date);
        record.setClient(clientService.getById(clientId));
        record.setDoctor(doctorService.getById(doctorId));
        Appointment appointment = new Appointment();
        appointment.setDescription("");
        appointment.setRecord(record);
        appointment.setExecutionStatus(ExecutionStatus.IN_PROGRESS);
        record.setAppointment(appointmentService.add(appointment));
        recordService.add(record);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByUsername(username);
        List<Appointment> currentAppointments = appointmentService.
                getByClientId(client.getId()).stream().
                filter(a -> a.getDescription().length()!=0).
                collect(Collectors.toList());
        return !currentAppointments.isEmpty()
                ? new ResponseEntity<>(currentAppointments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
