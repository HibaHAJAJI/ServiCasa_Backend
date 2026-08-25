package ServiCasa.demandeService.mapper;

import ServiCasa.demandeService.dto.DemandeServiceRequestDTO;
import ServiCasa.demandeService.dto.DemandeServiceResponseDTO;
import ServiCasa.demandeService.entity.DemandeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DemandeServiceMapper {

     @Mapping(target = "id",ignore = true)
     DemandeService toEntity(DemandeServiceRequestDTO dto);

     DemandeServiceResponseDTO toDto(DemandeService demandeService);

     List<DemandeServiceResponseDTO> toDtoList(List<DemandeService>demandeServices);

     @Mapping(target = "id",ignore = true)
     void updateDemandeServiceDto(DemandeServiceRequestDTO dto, @MappingTarget DemandeService demandeService);
}
