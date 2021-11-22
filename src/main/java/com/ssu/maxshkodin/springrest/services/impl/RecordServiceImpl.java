package com.ssu.maxshkodin.springrest.services.impl;

import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Record;
import com.ssu.maxshkodin.springrest.models.Speciality;
import com.ssu.maxshkodin.springrest.repository.RecordRepository;
import com.ssu.maxshkodin.springrest.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<GregorianCalendar> GetFreeGapsByDoctorOnDay(Doctor doctor, GregorianCalendar date) {
        List<Record> doctorRecords = recordRepository.findAll().stream()
                .filter(r->isOneDate(date,r.getRecordDateTime())
                && r.getDoctor().getId() == doctor.getId())
                .collect(Collectors.toList());
        List<GregorianCalendar> gaps = new ArrayList<>();
        for(int i = 9; i<21;i++){
            GregorianCalendar timeByGap = new GregorianCalendar(
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH),
                    i,
                    0,
                    0);
            if(doctorRecords.stream().noneMatch(r->isOneHour(r.getRecordDateTime(),timeByGap)))
                gaps.add(timeByGap);
        }
        return gaps;
    }

    @Override
    public List<GregorianCalendar> GetFreeGapsByDoctorOnWeek(Doctor doctor, GregorianCalendar date) {
        GregorianCalendar maxDate = new GregorianCalendar(date.get(GregorianCalendar.YEAR),
                date.get(GregorianCalendar.MONTH),
                date.get(GregorianCalendar.DAY_OF_MONTH)+7);
        List<Record> recordsByDoctorOnWeek = recordRepository.findAll().
                stream().
                filter(record -> record.getRecordDateTime().get(GregorianCalendar.DATE) >= date.get(GregorianCalendar.DATE)
                        && record.getRecordDateTime().get(GregorianCalendar.DATE) <= maxDate.get(GregorianCalendar.DATE)
                        && record.getDoctor().getId() == doctor.getId()).collect(Collectors.toList());
        List<GregorianCalendar> gaps = new ArrayList<>();
        for(int dayOfWeek =0; dayOfWeek<7;dayOfWeek++) {
            for (int i = 9; i < 21; i++) {
                GregorianCalendar timeByGap = new GregorianCalendar(
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH),
                        i,
                        0,
                        0);
                timeByGap.add(Calendar.DAY_OF_MONTH,dayOfWeek);
                if (recordsByDoctorOnWeek.stream().noneMatch(r -> isOneHour(r.getRecordDateTime(), timeByGap)))
                    gaps.add(timeByGap);
            }
        }
        return gaps;
    }

    @Override
    public Map<Doctor,List<GregorianCalendar>> GetFreeGapsByDoctorTypeOnDay(Speciality speciality, GregorianCalendar date) {
        List<Record> allRecords = recordRepository.findAll();
        Map<Doctor,List<GregorianCalendar>> toReturn = new HashMap<>();
        for (Record record: allRecords) {
            if(record.getDoctor().getSpeciality() == speciality) {
                toReturn.put(record.getDoctor(), GetFreeGapsByDoctorOnDay(record.getDoctor(),date));
            }
        }
        return toReturn;
    }

    @Override
    public Map<Doctor,List<GregorianCalendar>> GetFreeGapsByDoctorTypeOnWeek(Speciality speciality, GregorianCalendar dateFrom) {
        List<Record> allRecords = recordRepository.findAll();
        Map<Doctor,List<GregorianCalendar>> toReturn = new HashMap<>();
        for (Record record: allRecords) {
            if(record.getDoctor().getSpeciality() == speciality) {
                toReturn.put(record.getDoctor(), GetFreeGapsByDoctorOnWeek(record.getDoctor(),dateFrom));
            }
        }
        return toReturn;
    }

    @Override
    public List<Record> GetFutureRecordsByClient(Client client) {
        return recordRepository.findAll().stream().filter(r->r.getRecordDateTime().getTimeInMillis()>System.currentTimeMillis() &&
                r.getClient().getId() == client.getId()).collect(Collectors.toList());
    }

    @Override
    public Record add(Record record){
        return recordRepository.save(record);
    }

    @Override
    public void delete(Record record) {
        recordRepository.delete(record);
    }

    @Override
    public boolean isRecordExists(GregorianCalendar date) {
        List<Record> allRecords = recordRepository.findAll();
        for(Record record: allRecords){
            if(record.getRecordDateTime().getTimeInMillis() == date.getTimeInMillis())
                return true;
        }
        return false;
    }

    public boolean isValidTimeForRecord(GregorianCalendar time){
        if(time.get(Calendar.MINUTE)!=0 || time.get(Calendar.SECOND)!=0){
            return false;
        }
        GregorianCalendar currentTime = new GregorianCalendar();
        if(time.getTimeInMillis()<currentTime.getTimeInMillis()){
           return false;
        }
        return true;
    }

    @Override
    public List<Record> getRecordsByClient(Client client) {
        return recordRepository.findAll().stream().filter(r->r.getClient().getId() == client.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Record> getRecordsByDoctor(Doctor doctor) {
        return recordRepository.findAll().stream().filter(r->r.getDoctor().getId() == doctor.getId()).collect(Collectors.toList());
    }

    private boolean isOneDate(GregorianCalendar date1, GregorianCalendar date2){
        return date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isOneHour(GregorianCalendar time1, GregorianCalendar time2){
        return time1.get(Calendar.HOUR_OF_DAY) == time2.get(Calendar.HOUR_OF_DAY);
    }
}
