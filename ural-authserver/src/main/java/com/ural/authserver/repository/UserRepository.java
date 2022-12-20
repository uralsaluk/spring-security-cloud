package com.ural.authserver.repository;

import com.ural.authserver.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    Optional<UserEntity> findByEmail(String email);
}
