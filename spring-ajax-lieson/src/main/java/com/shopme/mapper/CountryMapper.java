package com.shopme.mapper;

import com.shopme.dto.CountryDto;
import com.shopme.entity.Country;

public class CountryMapper {

    // Map Country entity to CountryDto
    public static CountryDto mapToCountryDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());

        return countryDto;
    }

    // Map CountryDto to Country entity
    public static Country mapToCountry(CountryDto countryDto) {
        Country country = new Country();
        country.setId(countryDto.getId());
        country.setName(countryDto.getName());

        // Note: Mapping the Set<State> should be handled in the service layer if needed
        return country;
    }
}
