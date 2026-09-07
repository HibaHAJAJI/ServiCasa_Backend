package ServiCasa.service;


import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;

import java.util.List;

public interface ClientService {

      ClientResponseDTO addClient(ClientRequestDTO dto);

      List<ClientResponseDTO> findAllClients();

      ClientResponseDTO findById(Long id);

      ClientResponseDTO updateClient(ClientRequestDTO dto,Long id);

     void deleteClient(Long id);


}
