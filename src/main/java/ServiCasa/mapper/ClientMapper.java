package ServiCasa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.entity.Client;
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
