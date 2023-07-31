package org.datacite.springboot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModel {

    @NotNull
    private String id;

    @NotNull
    private int pageNumber;

    @Min(1)
    @Max(1000)
    private int pageSize;
}