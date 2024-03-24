package com.voyage.voyage.repositories;

import com.voyage.voyage.models.Usera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Usera ,Integer> {



Optional<Usera>findByEmail(String email);

}
