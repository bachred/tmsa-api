package com.example.tmsaapi.repository;

import com.example.tmsaapi.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long>  {

    AppUser findFirstByUsernameOrEmail(String username,String email); 
    AppUser findByUsername(String username);
    
}
