package com.simlearn.authentication.repository;

import com.simlearn.authentication.entity.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAndLoginRepository extends MongoRepository<AccountEntity, String> {
    AccountEntity findByUsername(@Param("username") String username);
    AccountEntity findByEmail(@Param("email") String email);
}
