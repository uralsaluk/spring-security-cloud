package com.ural.authserver.repository;

import com.ural.authserver.entities.MongoRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//@Repository
public interface MongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, String> {

    Optional<MongoRefreshToken> findByTokenId(String tokenId);


    boolean deleteByTokenId(String tokenId);

}
