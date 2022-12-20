package com.ural.authserver.entities;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "access_token")
public class MongoAccessToken {
/*
    @Id
    private String id;
    private String tokenId;
    private OAuth2AccessToken token;
    private String authenticationId;
    private String username;
    private String clientId;
    private String authentication;
    private String refreshToken;


    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }*/
}
