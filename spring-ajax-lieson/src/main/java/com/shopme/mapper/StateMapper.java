package com.shopme.mapper;

import com.shopme.dto.StateDto;
import com.shopme.entity.State;

public class StateMapper {

    // Map State entity to StateDto
    public static StateDto mapToStateDto(State state) {
        StateDto stateDto = new StateDto();
        stateDto.setId(state.getId());
        stateDto.setName(state.getName());

        // Map country from State entity
        stateDto.setCountry(state.getCountry());

        return stateDto;
    }

    // Map StateDto to State entity
    public static State mapToState(StateDto stateDto) {
        State state = new State();
        state.setId(stateDto.getId());
        state.setName(stateDto.getName());

        // Set the country in State entity
        state.setCountry(stateDto.getCountry());
        // Mapping country should be done in the service layer
        return state;
    }
}
