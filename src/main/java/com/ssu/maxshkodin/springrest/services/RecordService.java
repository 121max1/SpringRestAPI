package com.ssu.maxshkodin.springrest.services;

import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Record;
import com.ssu.maxshkodin.springrest.models.Speciality;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Service
public interface RecordService {

    List<GregorianCalendar> GetFreeGapsByDoctorOnDay(Doctor doctor, GregorianCalendar date);

    List<GregorianCalendar> GetFreeGapsByDoctorOnWeek(Doctor doctor, GregorianCalendar dateFrom);

    Map<Doctor,List<GregorianCalendar>> GetFreeGapsByDoctorTypeOnDay(Speciality speciality, GregorianCalendar date);

    Map<Doctor,List<GregorianCalendar>> GetFreeGapsByDoctorTypeOnWeek(Speciality speciality, GregorianCalendar dateFrom);

    List<Record> GetFutureRecordsByClient(Client client);

    Record add(Record record) throws Exception;

    void delete(Record record);

    boolean isRecordExists(GregorianCalendar date);

    boolean isValidTimeForRecord(GregorianCalendar time);

    List<Record> getRecordsByClient(Client client);

    List<Record> getRecordsByDoctor(Doctor doctor);

}
