package com.ssu.maxshkodin.springrest.di;


import com.ssu.maxshkodin.springrest.services.AppointmentService;
import com.ssu.maxshkodin.springrest.services.ClientService;
import com.ssu.maxshkodin.springrest.services.DoctorService;
import com.ssu.maxshkodin.springrest.services.RecordService;
import com.ssu.maxshkodin.springrest.services.impl.AppointmentServiceImpl;
import com.ssu.maxshkodin.springrest.services.impl.ClientServiceImpl;
import com.ssu.maxshkodin.springrest.services.impl.DoctorServiceImpl;
import com.ssu.maxshkodin.springrest.services.impl.RecordServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppConfig {

    @Bean
    public ClientService clientService(){
        return new ClientServiceImpl();
    }

    @Bean
    public DoctorService doctorService(){
        return new DoctorServiceImpl();
    }

    @Bean
    public RecordService recordService(){
        return new RecordServiceImpl();
    }

    @Bean
    public AppointmentService appointmentService(){ return new AppointmentServiceImpl();}
}
