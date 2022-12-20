package com.ural.authserver.config;

public class MongoTokenStore //implements TokenStore
{/*

    private MongoAccessTokenRepository mongoAccessTokenRepository;
    private MongoRefreshTokenRepository mongoRefreshTokenRepository;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    public MongoTokenStore(MongoAccessTokenRepository mongoAccessTokenRepository,
                           MongoRefreshTokenRepository mongoRefreshTokenRepository) {
        this.mongoAccessTokenRepository = mongoAccessTokenRepository;
        this.mongoRefreshTokenRepository = mongoRefreshTokenRepository;

    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken oAuth2AccessToken) {
        return readAuthentication(oAuth2AccessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        Optional<MongoAccessToken> accessToken = mongoAccessTokenRepository.findByTokenId(extractTokenKey(token));
        if (accessToken.isPresent()) {
            return accessToken.get().getAuthentication();
        }
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        String refreshToken = null;
        if (oAuth2AccessToken.getRefreshToken() != null) {
            refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
        }

        if (readAccessToken(oAuth2AccessToken.getValue()) != null) {
            this.removeAccessToken(oAuth2AccessToken);
        }

        MongoAccessToken mongoAccessToken = new MongoAccessToken();
        mongoAccessToken.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
        mongoAccessToken.setTokenId(extractTokenKey(oAuth2AccessToken.getValue()));
        mongoAccessToken.setToken(oAuth2AccessToken);
        mongoAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(oAuth2Authentication));
        mongoAccessToken.setUsername(oAuth2Authentication.isClientOnly() ? null : oAuth2Authentication.getName());
        mongoAccessToken.setClientId(oAuth2Authentication.getOAuth2Request().getClientId());
        mongoAccessToken.setAuthentication(oAuth2Authentication);
        mongoAccessToken.setRefreshToken(extractTokenKey(refreshToken));

        mongoAccessTokenRepository.save(mongoAccessToken);


    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Optional<MongoAccessToken> accessToken = mongoAccessTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        if (accessToken.isPresent()) {
            return accessToken.get().getToken();
        }
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {

        Optional<MongoAccessToken> accessToken = mongoAccessTokenRepository.findByTokenId(extractTokenKey(oAuth2AccessToken.getValue()));
        if (accessToken.isPresent()) {
            mongoAccessTokenRepository.delete(accessToken.get());
        }

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication oAuth2Authentication) {

        MongoRefreshToken mongoRefreshToken = new MongoRefreshToken();
        mongoRefreshToken.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
        mongoRefreshToken.setTokenId(extractTokenKey(oAuth2RefreshToken.getValue()));
        mongoRefreshToken.setToken(oAuth2RefreshToken);
        mongoRefreshToken.setAuthentication(oAuth2Authentication);
        mongoRefreshTokenRepository.save(mongoRefreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Optional<MongoRefreshToken> refreshToken = mongoRefreshTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        return refreshToken.isPresent() ? refreshToken.get().getToken() : null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        Optional<MongoRefreshToken> mongoRefreshToken = mongoRefreshTokenRepository
                .findByTokenId(extractTokenKey(oAuth2RefreshToken.getValue()));
        return mongoRefreshToken.isPresent() ? mongoRefreshToken.get().getAuthentication() : null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        Optional<MongoRefreshToken> mongoRefreshToken = mongoRefreshTokenRepository
                .findByTokenId(extractTokenKey(oAuth2RefreshToken.getValue()));
        if (mongoRefreshToken.isPresent()) {
            mongoRefreshTokenRepository.delete(mongoRefreshToken.get());
        }

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {

        Optional<MongoAccessToken> accessToken = mongoAccessTokenRepository
                .findByRefreshToken(extractTokenKey(oAuth2RefreshToken.getValue()));
        if (accessToken.isPresent()) {
            mongoAccessTokenRepository.delete(accessToken.get());
        }

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        OAuth2AccessToken accessToken = null;
        String authenticationId = authenticationKeyGenerator.extractKey(oAuth2Authentication);
        Optional<MongoAccessToken> token = mongoAccessTokenRepository.findByAuthenticationId(authenticationId);

        if (token.isPresent()) {
            accessToken = token.get().getToken();
            if (accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
                this.removeAccessToken(accessToken);
                this.storeAccessToken(accessToken, oAuth2Authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
        List<MongoAccessToken> result = mongoAccessTokenRepository.findByClientIdAndUsername(clientId, userName);
        result.forEach(e -> tokens.add(e.getToken()));
        return tokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
        List<MongoAccessToken> result = mongoAccessTokenRepository.findByClientId(clientId);
        result.forEach(e -> tokens.add(e.getToken()));
        return tokens;
    }


    private String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
            }

            try {
                byte[] e = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new Object[]{new BigInteger(1, e)});
            } catch (UnsupportedEncodingException var4) {
                throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
            }
        }
    }*/
}
