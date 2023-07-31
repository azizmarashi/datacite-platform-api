package org.datacite.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProviderModel {
    private String ProviderId;
    private String type;
    private String memberType;
    private String name;
}