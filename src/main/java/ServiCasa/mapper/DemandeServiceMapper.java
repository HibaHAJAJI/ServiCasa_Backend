package ServiCasa.mapper;

import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;
import ServiCasa.entity.DemandeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface DemandeServiceMapper {

     @Mapping(target = "id",ignore = true)
     DemandeService toEntity(DemandeServiceRequestDTO dto);

     DemandeServiceResponseDTO toDto(DemandeService demandeService);

     @Mapping(target = "id",ignore = true)
     void updateDemandeServiceDto(DemandeServiceRequestDTO dto, @MappingTarget DemandeService demandeService);
}
