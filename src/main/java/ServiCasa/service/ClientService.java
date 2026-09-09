package ServiCasa.service;


import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

      ClientResponseDTO addClient(ClientRequestDTO dto);

      Page<ClientResponseDTO> findAllClients(Pageable pageable);

      ClientResponseDTO findById(Long id);

      ClientResponseDTO updateClient(ClientRequestDTO dto,Long id);

     void deleteClient(Long id);


}
