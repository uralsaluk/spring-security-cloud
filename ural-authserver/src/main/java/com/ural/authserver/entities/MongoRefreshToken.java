package com.ural.authserver.entities;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "refresh_token")
public class MongoRefreshToken {
/*
    @Id
    private String id;
    private String tokenId;
    private OAuth2RefreshToken token;
    private String authentication;


    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }*/
}
