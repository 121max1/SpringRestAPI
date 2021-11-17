package com.ssu.maxshkodin.springrest.controller;

import com.ssu.maxshkodin.springrest.models.Appointment;
import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Speciality;
import com.ssu.maxshkodin.springrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/client")
    public ResponseEntity<?> registerClient(@RequestParam(value = "fullName") String fullName,
                                            @RequestParam(value = "phoneNumber") String phoneNumber,
                                            @RequestParam(value = "email") String email,
                                            @RequestParam(value = "login") String login,
                                            @RequestParam(value = "password") String password) throws Exception {
        Client client = new Client(fullName, phoneNumber, email, login, password);
        userService.registerUser(client, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<?> registerAdmin(@RequestParam(value = "fullName") String fullName,
                                           @RequestParam(value = "phoneNumber") String phoneNumber,
                                           @RequestParam(value = "email") String email,
                                           @RequestParam(value = "login") String login,
                                           @RequestParam(value = "password") String password) throws Exception {
        Client client = new Client(fullName, phoneNumber, email, login, password);
        userService.registerUser(client, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doctor")
    public ResponseEntity<?> registerDoctor(@RequestParam(value = "fullName") String fullName,
                                            @RequestParam(value = "phoneNumber") String phoneNumber,
                                            @RequestParam(value = "email") String email,
                                            @RequestParam(value = "login") String login,
                                            @RequestParam(value = "password") String password,
                                            @RequestParam(value = "speciality") String speciality) throws Exception {
        Speciality doctorSpeciality = Speciality.valueOf(speciality);
        Doctor doctor = new Doctor(fullName, phoneNumber, email, login, password, doctorSpeciality);
        userService.registerUser(doctor, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
