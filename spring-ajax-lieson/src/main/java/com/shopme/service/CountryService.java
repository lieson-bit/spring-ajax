package com.shopme.service;

import com.shopme.dto.CountryDto;

import java.util.List;

public interface CountryService {

    CountryDto createCountry(CountryDto countryDto);

    CountryDto getCountryById(Integer countryId);

    List<CountryDto> getAllCountries();

    CountryDto updateCountry(Integer countryId, CountryDto updatedCountry);

    void deleteCountry(Integer countryId);
}
