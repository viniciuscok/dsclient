package com.ds.client.controller.exceptions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StandardError implements Serializable {
    Instant timestamp;
    Integer status;
    String error;
    String message;
    String path;
}
