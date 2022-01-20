package com.dictionary.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LanguageWithUserIdProjection {
    long getId();
    
    String getName();
    
    String getCode();
    
    LocalDateTime getCreateAt();
    
    LocalDateTime getUpdateAt();

    UUID getUserId();
}
