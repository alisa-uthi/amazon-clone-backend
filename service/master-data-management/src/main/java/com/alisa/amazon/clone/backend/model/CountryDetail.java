package com.alisa.amazon.clone.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryDetail {
    @Schema(example = "1", type = "Integer")
    int id;

    @Schema(example = "AF", type = "String")
    String countryCode;

    @Schema(example = "AFG", type = "String")
    String countryCodeIso3;

    @Schema(example = "Afghanistan", type = "String")
    String countryName;

    @Schema(example = "Kabul", type = "String")
    String capitalName;

    @Schema(example = "93", type = "Integer")
    int phoneCode;

    @Schema(example = "؋", type = "String")
    String currencySymbol;

    @Schema(example = "AFN", type = "String")
    String currency;

    @Schema(example = "AS", type = "String")
    String continentCode;

    @Schema(example = "Asia", type = "String")
    String continentName;
}
