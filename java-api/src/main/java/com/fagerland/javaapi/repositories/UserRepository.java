package com.fagerland.javaapi.repositories;

import com.fagerland.javaapi.models.UserEntry;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntry, Long> {
    UserEntry findFirstByUsername(String username);
}
