package com.ural.authserver.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.ural.authserver.entities.Role;
import com.ural.authserver.entities.UserEntity;

import java.io.IOException;
import java.util.List;


public class UserDeserializer extends JsonDeserializer<UserEntity> {

    @Override
    public UserEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();

        JsonNode jsonNode = mapper.readTree(jsonParser);
        Long id = readJsonNode(jsonNode, "id").asLong();
        String loginAccount = readJsonNode(jsonNode, "email").asText();
        String password = readJsonNode(jsonNode, "password").asText();
        String firstName = readJsonNode(jsonNode, "firstName").asText();
        String lastName = readJsonNode(jsonNode, "lastName").asText();
        String rolesString = readJsonNode(jsonNode, "roles").asText();
        System.out.println(jsonNode);
        System.out.println(rolesString);
        System.out.println("deneme");
        List<Role> roleList = mapper.readValue(rolesString, new TypeReference<List<Role>>() {
        });

        //  return new UserEntity(id, firstName, lastName, loginAccount, password, roleList);
        return null;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}