package com.shopme.dto;

import com.shopme.entity.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Integer id;
    private String name;
}
