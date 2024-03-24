package com.voyage.voyage.controllers;


import com.voyage.voyage.dto.UserDto;
import com.voyage.voyage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService service;

    @PostMapping("/")
    public ResponseEntity<Integer> save (
            @RequestBody UserDto userDto
            ){
        return ResponseEntity.ok(service.save(userDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll (){
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping("/welcome")
    public String welcome (){
        String text = "this is a prove";
        return text;
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserDto> findbyId (
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.findById(userId));
    }





    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("user-id") Integer userId
    ){
    service.delete(userId);
    return ResponseEntity.accepted().build();
    }


}
