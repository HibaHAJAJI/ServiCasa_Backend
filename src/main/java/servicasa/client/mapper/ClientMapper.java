package servicasa.client.mapper;

import org.mapstruct.Mapper;
import servicasa.client.dto.ClientResponseDTO;
import servicasa.client.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {


     Client toEntity(ClientResponseDTO dto);
}
