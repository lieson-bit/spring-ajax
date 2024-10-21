package com.shopme.service.impl;

import com.shopme.dto.StateDto;
import com.shopme.entity.State;
import com.shopme.exception.ResourceNotFoundException;
import com.shopme.mapper.StateMapper;
import com.shopme.repository.StateRepository;
import com.shopme.service.StateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Override
    public StateDto createState(StateDto stateDto) {
        State state = StateMapper.mapToState(stateDto);
        State savedState = stateRepository.save(state);
        return StateMapper.mapToStateDto(savedState);
    }

    @Override
    public StateDto getStateById(Integer stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with ID: " + stateId));
        return StateMapper.mapToStateDto(state);
    }

    @Override
    public List<StateDto> getAllStates() {
        List<State> states = stateRepository.findAll();
        return states.stream()
                .map(StateMapper::mapToStateDto)
                .collect(Collectors.toList());
    }

    @Override
    public StateDto updateState(Integer stateId, StateDto updatedState) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with ID: " + stateId));

        state.setName(updatedState.getName());
        state.setCountry(updatedState.getCountry());

        State updatedStateEntity = stateRepository.save(state);
        return StateMapper.mapToStateDto(updatedStateEntity);
    }

    @Override
    public void deleteState(Integer stateId) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with ID: " + stateId));
        stateRepository.delete(state);
    }

    @Override
    public StateDto getStateByName(String stateName) {
        return null;
    }

    @Override
    public List<State> getStatesByCountryId(Integer countryId) {
        String jpql = "SELECT s FROM State s WHERE s.country.id = :countryId";
        EntityManager entityManager = null;
        TypedQuery<State> query = entityManager.createQuery(jpql, State.class);
        query.setParameter("countryId", countryId);
        return query.getResultList();
    }
}
