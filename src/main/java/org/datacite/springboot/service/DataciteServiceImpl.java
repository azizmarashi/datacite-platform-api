package org.datacite.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.datacite.springboot.model.ClientModel;
import org.datacite.springboot.model.DoiCountModel;
import org.datacite.springboot.model.ProviderModel;
import org.datacite.springboot.model.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DataciteServiceImpl implements DataciteService {

    @Value("${providerUrl}")
    private String providerUrl;

    @Value("${clientsUrl}")
    private String clientsUrl;

    @Value("${doisUrl}")
    private String doisUrl;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @SneakyThrows(value = {JsonProcessingException.class})
    public List<ProviderModel> findProvidersByConsortiumId(RequestModel model) {
        String url = String.format(providerUrl, model.getId(), model.getPageNumber(), model.getPageSize());
        String response = getJsonResponse(url);
        JsonNode data = mapper.readTree(response).get("data");

        return StreamSupport.stream(data.spliterator(), false).map(jsonNode -> new ProviderModel(
                jsonNode.get("id").asText(),
                jsonNode.get("type").asText(),
                jsonNode.get("attributes").get("memberType").asText(),
                jsonNode.get("attributes").get("name").asText())).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows(value = {JsonProcessingException.class})
    public List<ClientModel> findClientsByProviderId(RequestModel model) {
        String url = String.format(clientsUrl, model.getId(), model.getPageNumber(), model.getPageSize());
        String response = getJsonResponse(url);
        JsonNode data = mapper.readTree(response).get("data");

        return StreamSupport.stream(data.spliterator(), false).map(jsonNode -> new ClientModel(
                jsonNode.get("id").asText(),
                jsonNode.get("type").asText(),
                jsonNode.get("attributes").get("name").asText(),
                StreamSupport.stream(jsonNode.get("relationships")
                                .get("prefixes").get("data")
                                .spliterator(), false).map(d -> d.get("id").asText())
                        .collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows(value = {JsonProcessingException.class})
    public DoiCountModel countDoiFromClientId(String clientId) {
        String url = String.format(doisUrl, clientId);
        String response = getJsonResponse(url);

        return new DoiCountModel(mapper.readTree(response).get("meta").get("total").asLong());
    }

    @SneakyThrows(value = {IOException.class, InterruptedException.class})
    private String getJsonResponse(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/vnd.api+json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

}