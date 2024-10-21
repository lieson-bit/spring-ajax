package com.shopme.service;

import com.shopme.dto.StateDto;
import com.shopme.entity.State;

import java.util.List;

public interface StateService {

    StateDto createState(StateDto stateDto);

    StateDto getStateById(Integer stateId);

    List<StateDto> getAllStates();

    StateDto updateState(Integer stateId, StateDto updatedState);

    void deleteState(Integer stateId);

    StateDto getStateByName(String stateName);

    List<State> getStatesByCountryId(Integer countryId);
}
