package com.ssu.maxshkodin.springrest.services.impl;

import com.ssu.maxshkodin.springrest.models.Client;
import com.ssu.maxshkodin.springrest.models.Doctor;
import com.ssu.maxshkodin.springrest.models.Role;
import com.ssu.maxshkodin.springrest.models.User;
import com.ssu.maxshkodin.springrest.repository.ClientRepository;
import com.ssu.maxshkodin.springrest.repository.DoctorRepository;
import com.ssu.maxshkodin.springrest.repository.RoleRepository;
import com.ssu.maxshkodin.springrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public void registerUser(User user, boolean isAdmin) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user instanceof Doctor && !isAdmin) {
            user.setRoles(Collections.singletonList(getDoctorsRole()));
            doctorRepository.save((Doctor)user);
        }else if(user instanceof Client && isAdmin){
            user.setRoles(Collections.singletonList(getAdminRole()));
            clientRepository.save((Client) user);
        }else if(user instanceof Client){
            user.setRoles(Collections.singletonList(getClientRole()));
            clientRepository.save((Client) user);
        }else{
            throw new Exception("Doctor can't be admin");
        }

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User client = clientRepository.findAll().stream().
                filter(c ->c.getLogin().equals(username)).findFirst().orElse(null);
        User doctor = doctorRepository.findAll().stream().
                filter(c ->c.getLogin().equals(username)).findFirst().orElse(null);
        if (client!=null){
            return toSpringUser(client);
        }else if (doctor!=null){
            return toSpringUser(doctor);
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    private org.springframework.security.core.userdetails.User toSpringUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles()
        );
    }

    private Role getClientRole() {
        return getRole("ROLE_CLIENT");
    }

    private Role getAdminRole() {
        return getRole("ROLE_ADMIN");
    }

    private Role getDoctorsRole(){
        return  getRole("ROLE_DOCTOR");
    }

    private Role getRole(String roleName) {
        Optional<Role> roleUser = roleRepository.findByName(roleName);
        if (!roleUser.isPresent()) {
            Role role = new Role(roleName);
            roleRepository.save(role);

            return role;
        }

        return roleUser.get();
    }

}
