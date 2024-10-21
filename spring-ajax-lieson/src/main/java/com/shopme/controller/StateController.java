package com.shopme.controller;

import com.shopme.dto.StateDto;
import com.shopme.entity.Country;
import com.shopme.entity.State;
import com.shopme.repository.StateRepository;
import com.shopme.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/states")
public class StateController {

    private final StateService stateService;

    @Autowired
    private StateRepository repo;

    // Create state
    @PostMapping("/create")
    public ResponseEntity<StateDto> createState(@RequestBody StateDto stateDto) {
        StateDto savedState = stateService.createState(stateDto);
        return new ResponseEntity<>(savedState, HttpStatus.CREATED);
    }

    // Get all states
    @GetMapping("/list")
    public ResponseEntity<List<StateDto>> getAllStates() {
        List<StateDto> states = stateService.getAllStates();
        return ResponseEntity.ok(states);
    }

    // Get states by country ID
    @GetMapping("/country/{id}")
    public List<State> listByCountry(@PathVariable("id") Integer countryId) {
        System.out.println("Country ID: " + countryId);
        return repo.findByCountry(new Country(countryId));
    }

    // Update state
    @PutMapping("/update/{id}")
    public ResponseEntity<StateDto> updateState(@PathVariable("id") Integer stateId,
                                                @RequestBody StateDto stateDto) {
        StateDto updatedState = stateService.updateState(stateId, stateDto);
        return ResponseEntity.ok(updatedState);
    }

    // Delete state
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteState(@PathVariable("id") Integer stateId) {
        stateService.deleteState(stateId);
        return ResponseEntity.ok("State deleted successfully!");
    }
}
