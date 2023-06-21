package com.alisa.amazon.clone.backend.service;

import com.alisa.amazon.clone.backend.entity.CountryEntity;
import com.alisa.amazon.clone.backend.exception.BusinessException;
import com.alisa.amazon.clone.backend.mapper.CountryMapper;
import com.alisa.amazon.clone.backend.model.Country;
import com.alisa.amazon.clone.backend.model.CountryDetail;
import com.alisa.amazon.clone.backend.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.alisa.amazon.clone.backend.constant.CountryErrorConstant.*;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    public List<Country> getAllCountries() {
        return countryRepository.findAllCountries();
    }

    public CountryDetail getCountryById(String id) {
        Optional<CountryEntity> country = countryRepository.findByCountryId(Integer.parseInt(id));
        return country.map(countryMapper::mapCountryDetailResponse)
                .orElseThrow(() -> new BusinessException(COUNTRY_NOT_FOUND_CODE, COUNTRY_NOT_FOUND_MESSAGE, String.format(COUNTRY_NOT_FOUND_DESCRIPTION, id)));
    }
}
