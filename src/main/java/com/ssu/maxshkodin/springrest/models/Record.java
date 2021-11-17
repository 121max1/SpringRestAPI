package com.ssu.maxshkodin.springrest.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.GregorianCalendar;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    private GregorianCalendar recordDateTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    public Record(Doctor doctor, Client client, GregorianCalendar recordDateTime){
        this.doctor = doctor;
        this.client = client;
        this.recordDateTime = recordDateTime;
    }

    public Record() {

    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public GregorianCalendar getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(GregorianCalendar recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
