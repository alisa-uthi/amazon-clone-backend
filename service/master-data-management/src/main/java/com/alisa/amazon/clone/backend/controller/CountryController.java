package com.alisa.amazon.clone.backend.controller;

import com.alisa.amazon.clone.backend.annotation.DefaultApiResponse;
import com.alisa.amazon.clone.backend.model.Country;
import com.alisa.amazon.clone.backend.model.CountryDetail;
import com.alisa.amazon.clone.backend.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@DefaultApiResponse
@Validated
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all countries", description = "Returns a list of country", operationId = "getAllCountries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get country by ID", description = "Returns country's detail of a given ID", operationId = "getCountryById")
    public CountryDetail getCountryById(@PathVariable("id") String id) {
        return countryService.getCountryById(id);
    }
}
