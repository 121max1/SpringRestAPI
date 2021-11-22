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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
        List<Doctor> doctorCopy = new ArrayList<>(doctors);
        for(Doctor doctor:doctorCopy){
            doctor.setPassword("");
        }
        return !doctors.isEmpty()
                ? new ResponseEntity<>(doctorCopy, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/records")
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
                                      @RequestParam(value= "gap") String gap,
                                      @RequestParam(value="clientId") Integer clientId) throws Exception {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date parsed = df.parse(gap);
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(parsed);
        Record record = new Record();
        if(recordService.isRecordExists(date))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!recordService.isValidTimeForRecord(date))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        record.setRecordDateTime(date);
        record.setClient(clientService.getById(clientId));
        record.setDoctor(doctorService.getById(doctorId));
        Appointment appointment = new Appointment();
        appointment.setDescription("");
        appointment.setRecord(record);
        appointment.setExecutionStatus(ExecutionStatus.IN_PROGRESS);
        Appointment addedAppointment = appointmentService.add(appointment);
        record.setAppointment(addedAppointment);
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

    @GetMapping(value = "/gapsByDoctorOnDay/{id}")
    public ResponseEntity<List<GregorianCalendar>> getGapsByDoctorOnDay(@PathVariable Integer id){
        Doctor doctor = doctorService.getById(id);
        GregorianCalendar date = new GregorianCalendar();
        List<GregorianCalendar> gaps = recordService.GetFreeGapsByDoctorOnDay(doctor,date);
        return new ResponseEntity<>(gaps,HttpStatus.OK);
    }

    @GetMapping(value = "/gapsByDoctorOnWeek/{id}")
    public ResponseEntity<List<GregorianCalendar>> getGapsByDoctorOnWeek(@PathVariable Integer id){
        Doctor doctor = doctorService.getById(id);
        GregorianCalendar date = new GregorianCalendar();
        List<GregorianCalendar> gaps = recordService.GetFreeGapsByDoctorOnWeek(doctor,date);
        return new ResponseEntity<>(gaps,HttpStatus.OK);
    }

    @GetMapping(value = "/gapsBySpecialityOnDay")
    public ResponseEntity<Map<Integer, List<GregorianCalendar>>> getGapsBySpecialityOnDay(@RequestParam String speciality){
        Speciality specialityDoctor = Speciality.valueOf(speciality);
        GregorianCalendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH,date.get(Calendar.DAY_OF_MONTH)+1);
        Map<Integer, List<GregorianCalendar>> doctorTimeMap = new HashMap<>();
        for(Map.Entry<Doctor,List<GregorianCalendar>> entry :recordService.GetFreeGapsByDoctorTypeOnDay(specialityDoctor, date).entrySet()){
            doctorTimeMap.put(entry.getKey().getId(),entry.getValue());
        }
        return new ResponseEntity<>(doctorTimeMap,HttpStatus.OK);

    }

    @GetMapping(value = "/gapsBySpecialityOnWeek")
    public ResponseEntity<Map<Integer, List<GregorianCalendar>>> getGapsBySpecialityOnWeek(@RequestParam String speciality){
        Speciality specialityDoctor = Speciality.valueOf(speciality);
        GregorianCalendar date = new GregorianCalendar();
        Map<Integer, List<GregorianCalendar>> doctorTimeMap = new HashMap<>();
        for(Map.Entry<Doctor,List<GregorianCalendar>> entry :recordService.GetFreeGapsByDoctorTypeOnWeek(specialityDoctor, date).entrySet()){
            doctorTimeMap.put(entry.getKey().getId(),entry.getValue());
        }
        return new ResponseEntity<>(doctorTimeMap,HttpStatus.OK);

    }

}
