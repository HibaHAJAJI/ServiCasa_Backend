package ServiCasa.client.service;


import ServiCasa.client.dto.ClientRequestDTO;
import ServiCasa.client.dto.ClientResponseDTO;

import java.util.List;

public interface ClientService {

      ClientResponseDTO addClient(ClientRequestDTO dto);

      List<ClientResponseDTO> findAllClients();

      ClientResponseDTO findById(Long id);

      ClientResponseDTO updateClient(ClientRequestDTO dto,Long id);

     void deleteClient(Long id);


}
