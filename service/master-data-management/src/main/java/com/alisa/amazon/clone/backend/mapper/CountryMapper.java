package com.alisa.amazon.clone.backend.mapper;

import com.alisa.amazon.clone.backend.entity.CountryEntity;
import com.alisa.amazon.clone.backend.model.CountryDetail;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper {
    public CountryDetail mapCountryDetailResponse(CountryEntity country) {
        return CountryDetail.builder()
                .id(country.getCountryId())
                .countryCode(country.getCountryCode())
                .countryCodeIso3(country.getCountryCodeIso3())
                .countryName(country.getCountryName())
                .capitalName(country.getCapitalName())
                .phoneCode(country.getPhoneCode())
                .currencySymbol(country.getCurrencySymbol())
                .currency(country.getCurrency())
                .continentCode(country.getContinentCode())
                .continentName(country.getContinentName())
                .build();
    }
}
