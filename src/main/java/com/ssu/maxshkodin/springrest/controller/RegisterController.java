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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/client")
    public ResponseEntity<?> registerClient(@Valid @RequestBody Client client) throws Exception {
        userService.registerUser(client, false);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody Client client) throws Exception {
        userService.registerUser(client, true);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/doctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody Doctor doctor) throws Exception {
        userService.registerUser(doctor, false);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
}
