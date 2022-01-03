package com.dictionary.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KeyEncoder {

    /**
     * Encodes file/folder name to Base64
     *
     * @param name of word
     * @return encoded word name
     */
    public static String encodeKey(String name){
        return Base64.getEncoder().encodeToString(name.getBytes(StandardCharsets.UTF_8));
    }
}
