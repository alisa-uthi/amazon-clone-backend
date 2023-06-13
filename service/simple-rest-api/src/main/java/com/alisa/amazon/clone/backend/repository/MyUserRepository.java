package com.alisa.amazon.clone.backend.repository;

import com.alisa.amazon.clone.backend.entity.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, Integer> {
}
