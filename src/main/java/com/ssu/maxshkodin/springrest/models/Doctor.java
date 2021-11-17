package com.ssu.maxshkodin.springrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Doctor extends User {

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @OneToMany(mappedBy="doctor", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Record> records;

    public Doctor(String fullName, String phoneNumber, String email, String login, String password, Speciality speciality) {
        super(fullName, phoneNumber, email, login, password);
        this.speciality = speciality;
    }

    public Doctor() {

    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

}
