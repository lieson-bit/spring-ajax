package com.shopme.dto;


import com.shopme.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {
    private Integer id;
    private String name;
    private Country country;
}
