package com.datasecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datasecurity.entity.AadharCard;
import com.datasecurity.entity.AadharCardDTO;
import com.datasecurity.entity.AadharCardEntity;
import com.datasecurity.service.AadharCardService;

@RestController
@RequestMapping("/aadhar")
public class AadharCardController {

    @Autowired
    private AadharCardService aadharCardService;

    @PostMapping("/save")
    public ResponseEntity<String> saveAadharCard(@RequestBody AadharCardDTO aadharCardDTO) {
        try {
            aadharCardService.saveAadharCard(aadharCardDTO);
            return new ResponseEntity<>("AadharCard saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving AadharCard", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public List<AadharCardEntity> getAllAadharCards() {
        return aadharCardService.getAllAadharCards();
    }
}
