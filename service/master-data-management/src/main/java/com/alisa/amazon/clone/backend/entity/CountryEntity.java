package com.alisa.amazon.clone.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "COUNTRY")
public class CountryEntity {
    @Id
    @Column(name = "COUNTRY_ID", unique = true, updatable = false, columnDefinition = "INT(11)")
    int countryId;

    @Column(name = "COUNTRY_CODE", nullable = false, columnDefinition = "CHAR(2)")
    String countryCode;

    @Column(name = "COUNTRY_CODE_ISO3", nullable = false, columnDefinition = "CHAR(3)")
    String countryCodeIso3;

    @Column(name = "COUNTRY_NAME", nullable = false, columnDefinition = "VARCHAR(100)")
    String countryName;

    @Column(name = "CAPITAL_NAME", nullable = false, columnDefinition = "VARCHAR(80)")
    String capitalName;

    @Column(name = "PHONE_CODE", nullable = false, columnDefinition = "INT(5)")
    int phoneCode;

    @Column(name = "CURRENCY_SYMBOL", nullable = false, columnDefinition = "VARCHAR(10)")
    String currencySymbol;

    @Column(name = "CURRENCY", nullable = false, columnDefinition = "VARCHAR(3)")
    String currency;

    @Column(name = "CONTINENT_CODE", nullable = false, columnDefinition = "VARCHAR(2)")
    String continentCode;

    @Column(name = "CONTINENT_NAME", nullable = false, columnDefinition = "VARCHAR(30)")
    String continentName;
}
