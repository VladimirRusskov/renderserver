package com.server.web.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TaskRequest {
    @NotNull
    @Positive
    private Long applicationId;
}
