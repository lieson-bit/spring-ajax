package com.shopme.service.impl;

import com.shopme.dto.CountryDto;
import com.shopme.entity.Country;
import com.shopme.exception.ResourceNotFoundException;
import com.shopme.mapper.CountryMapper;
import com.shopme.repository.CountryRepository;
import com.shopme.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        Country country = CountryMapper.mapToCountry(countryDto);
        Country savedCountry = countryRepository.save(country);
        return CountryMapper.mapToCountryDto(savedCountry);
    }

    @Override
    public CountryDto getCountryById(Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + countryId));
        return CountryMapper.mapToCountryDto(country);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(CountryMapper::mapToCountryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto updateCountry(Integer countryId, CountryDto updatedCountry) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + countryId));

        country.setName(updatedCountry.getName());

        Country updatedCountryEntity = countryRepository.save(country);
        return CountryMapper.mapToCountryDto(updatedCountryEntity);
    }

    @Override
    public void deleteCountry(Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + countryId));
        countryRepository.delete(country);
    }
}
