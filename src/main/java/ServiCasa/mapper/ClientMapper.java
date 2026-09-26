package ServiCasa.mapper;

import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.entity.Client;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public abstract class ClientMapper extends ReferenceMapper {


     @Mapping(target = "id",ignore = true)
     public abstract Client toEntity(ClientRequestDTO dto);

     public abstract ClientResponseDTO toDto(Client client);


     @Mapping(target = "id",ignore = true)
     public abstract void updateClientDto(ClientUpdateRequestDTO dto, @MappingTarget Client client);
}
