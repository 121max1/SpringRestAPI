package com.ssu.maxshkodin.springrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "clients")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Client extends User {

    @OneToMany(mappedBy="client")
    @JsonBackReference
    private Set<Record> records;

    public Client(String fullName, String phoneNumber,String email, String login, String password) {
        super(fullName, phoneNumber, email, login, password);
    }

    public  Client(){

    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

}
