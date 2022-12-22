package com.ural.resourceserver.uralresourceserver.util;

import java.util.List;

public interface CustomContext {


    String getUsername();

    String getName();
    String getSurname();

    List<String> getRoles();

    List<String> getScopes();

}
