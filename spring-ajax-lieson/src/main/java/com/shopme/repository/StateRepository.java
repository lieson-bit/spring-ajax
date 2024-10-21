package com.shopme.repository;

import com.shopme.entity.Country;
import com.shopme.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    public List<State> findByCountry(Country country);
}
