package com.ural.authserver.repository;

import com.ural.authserver.entities.MongoAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface MongoAccessTokenRepository extends MongoRepository<MongoAccessToken, String> {

    List<MongoAccessToken> findByClientId(String clientId);

    List<MongoAccessToken> findByClientIdAndUsername(String clientId, String username);

    Optional<MongoAccessToken> findByTokenId(String tokenId);

    Optional<MongoAccessToken> findByRefreshToken(String refreshToken);

    Optional<MongoAccessToken> findByAuthenticationId(String authenticationId);

}
