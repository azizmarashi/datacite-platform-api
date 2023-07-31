package org.datacite.springboot.controller;

import jakarta.validation.Valid;
import org.datacite.springboot.model.ClientModel;
import org.datacite.springboot.model.DoiCountModel;
import org.datacite.springboot.model.ProviderModel;
import org.datacite.springboot.model.RequestModel;
import org.datacite.springboot.service.DataciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/datacite")
public class DataciteController {

    @Autowired
    private DataciteService dataciteService;

    @GetMapping("/count-dois-from-client-id/{clientId}")
    public ResponseEntity<DoiCountModel> clientDOI(@PathVariable String clientId) {
        return ResponseEntity.ok(dataciteService.countDoiFromClientId(clientId));
    }

    @GetMapping("/providers-by-consortium-id")
    public ResponseEntity<List<ProviderModel>> providers(@RequestBody @Valid RequestModel model){
        return ResponseEntity.ok(dataciteService.findProvidersByConsortiumId(model));
    }

    @GetMapping("/clients-by-provider-id")
    public ResponseEntity<List<ClientModel>> client(@RequestBody @Valid RequestModel model){
        return ResponseEntity.ok(dataciteService.findClientsByProviderId(model));
    }

}