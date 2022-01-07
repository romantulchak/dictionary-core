package com.dictionary.projection;

import java.util.List;

public interface WordKeyProjection {

    long getId();

    String getKey();

    List<String> getKeys();
}
