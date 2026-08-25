package ServiCasa.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ServiCasa.client.dto.ClientRequestDTO;
import ServiCasa.client.dto.ClientResponseDTO;
import ServiCasa.client.entity.Client;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {


     @Mapping(target = "id",ignore = true)
     Client toEntity(ClientRequestDTO dto);

     ClientResponseDTO toDto(Client client);

     List<ClientResponseDTO>toDtoList(List<Client>clients);

     @Mapping(target = "id",ignore = true)
     void updateClientDto(ClientRequestDTO dto, @MappingTarget Client client);
}
