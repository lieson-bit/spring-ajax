package com.shopme.controller;

import com.shopme.dto.CountryDto;
import com.shopme.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // Build Create Country REST API
    @PostMapping("/create")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        CountryDto savedCountry = countryService.createCountry(countryDto);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    // Build Get Country by ID REST API
    @GetMapping("/view/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable("id") Integer countryId) {
        CountryDto countryDto = countryService.getCountryById(countryId);
        return ResponseEntity.ok(countryDto);
    }

    // Build Get All Countries REST API
    @GetMapping("/list")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    // Build Update Country REST API
    @PutMapping("/update/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable("id") Integer countryId,
                                                    @RequestBody CountryDto countryDto) {
        CountryDto updatedCountry = countryService.updateCountry(countryId, countryDto);
        return ResponseEntity.ok(updatedCountry);
    }

    // Build Delete Country REST API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer countryId) {
        countryService.deleteCountry(countryId);
        return ResponseEntity.ok("Country deleted successfully!");
    }

    @GetMapping("/403")
    public String error403(){
        return "403";
    }
}
