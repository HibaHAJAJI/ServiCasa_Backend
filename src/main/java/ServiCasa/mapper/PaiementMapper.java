package ServiCasa.mapper;

import ServiCasa.dto.request.PaiementRequestDTO;
import ServiCasa.dto.response.PaiementResponseDTO;
import ServiCasa.entity.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PaiementMapper {

     @Mapping(target = "id",ignore = true)
     Paiement toEntity(PaiementRequestDTO dto);

     PaiementResponseDTO toDto(Paiement paiement);

     @Mapping(target = "id",ignore = true)
     void updatePaiementDto(PaiementRequestDTO dto, @MappingTarget Paiement paiement);
}
