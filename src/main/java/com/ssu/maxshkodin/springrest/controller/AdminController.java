package com.ssu.maxshkodin.springrest.controller;

import com.ssu.maxshkodin.springrest.models.Appointment;
import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Record;
import com.ssu.maxshkodin.springrest.services.AppointmentService;
import com.ssu.maxshkodin.springrest.services.ClientService;
import com.ssu.maxshkodin.springrest.services.DoctorService;
import com.ssu.maxshkodin.springrest.services.RecordService;
import jdk.jfr.events.ExceptionStatisticsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ClientService clientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    RecordService recordService;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> clients = clientService.getAll();
        List<Client> clientCopy = new ArrayList<>(clients);
        for(Client client:clientCopy){
            client.setPassword("");
        }
        return clients!=null && !clients.isEmpty()
                ? new ResponseEntity<>(clientCopy,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors(){
        List<Doctor> doctors = doctorService.getAll();
        List<Doctor> doctorCopy = new ArrayList<>(doctors);
        for(Doctor doctor:doctorCopy){
            doctor.setPassword("");
        }
        return doctors!=null && !doctors.isEmpty()
                ? new ResponseEntity<>(doctorCopy, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("clients/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id){
        try{
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("doctors/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id){
        try{
            doctorService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("clients/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id,
                                          @RequestParam(value = "fullName") String fullName,
                                          @RequestParam(value = "phoneNumber") String phoneNumber,
                                          @RequestParam(value = "email") String email
                                          ){
        try {
            Client client = clientService.getById(id);
            client.setFullName(fullName);
            client.setEmail(email);
            client.setPhoneNumber(phoneNumber);
            clientService.update(client);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("doctors/update/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id,
                                          @RequestParam(value = "fullName") String fullName,
                                          @RequestParam(value = "phoneNumber") String phoneNumber,
                                          @RequestParam(value = "email") String email
    ){
        try {
            Doctor doctor = doctorService.getById(id);
            doctor.setFullName(fullName);
            doctor.setEmail(email);
            doctor.setPhoneNumber(phoneNumber);
            doctorService.update(doctor);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("doctors/records/{id}")
    public ResponseEntity<List<Record>> getRecordsByDoctor(@PathVariable Integer id){
        Doctor doctor;
        try{
            doctor = doctorService.getById(id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Record> records = recordService.getRecordsByDoctor(doctor);
        return records!=null && !records.isEmpty()
                ? new ResponseEntity<>(records, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("clients/records/{id}")
    public ResponseEntity<List<Record>> getRecordsByClient(@PathVariable Integer id){
        Client client;
        try{
            client = clientService.getById(id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Record> records = recordService.getRecordsByClient(client);
        return records!=null && !records.isEmpty()
                ? new ResponseEntity<>(records, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("doctors/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Integer id){
        List<Appointment> appointments = appointmentService.getByDoctorId(id);
        return appointments!=null && !appointments.isEmpty()
                ? new ResponseEntity<>(appointments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("clients/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByClient(@PathVariable Integer id){
        List<Appointment> appointments = appointmentService.getByClientId(id);
        return appointments!=null && !appointments.isEmpty()
                ? new ResponseEntity<>(appointments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }










}
