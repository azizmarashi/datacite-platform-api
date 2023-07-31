package org.datacite.springboot.service;

import org.datacite.springboot.model.ClientModel;
import org.datacite.springboot.model.DoiCountModel;
import org.datacite.springboot.model.ProviderModel;
import org.datacite.springboot.model.RequestModel;
import java.util.List;

public interface DataciteService {

    List<ProviderModel> findProvidersByConsortiumId(RequestModel model);
    List<ClientModel> findClientsByProviderId(RequestModel model);
    DoiCountModel countDoiFromClientId(String clientId);
}