package com.dsmhack.igniter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionLogger {
    private String integrationServiceName;
    private String actionAttempted;
    private String result;
    private String error;
    private String warning;

    private boolean isOk() {
        return error.isEmpty();
    }

}
