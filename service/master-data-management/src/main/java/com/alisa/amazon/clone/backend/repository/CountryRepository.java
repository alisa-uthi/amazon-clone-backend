package com.alisa.amazon.clone.backend.repository;

import com.alisa.amazon.clone.backend.entity.CountryEntity;
import com.alisa.amazon.clone.backend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    @Query(value = "SELECT new com.alisa.amazon.clone.backend.model.Country(c.countryId, c.countryCode, c.countryCodeIso3, c.countryName) FROM CountryEntity c")
    List<Country> findAllCountries();

    @Query(value = "SELECT * FROM COUNTRY c WHERE " +
            " (:countryId is null or c.COUNTRY_ID = :countryId);"
            , nativeQuery = true
    )
    Optional<CountryEntity> findByCountryId(@Param("countryId") int countryId);
}
