package org.datacite.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ClientModel {
    private String clientId;
    private String type;
    private String name;
    private List<String> prefixes;

}