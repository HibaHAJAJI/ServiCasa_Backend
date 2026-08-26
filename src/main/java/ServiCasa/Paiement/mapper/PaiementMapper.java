package ServiCasa.Paiement.mapper;

import ServiCasa.Paiement.dto.PaiementRequestDTO;
import ServiCasa.Paiement.dto.PaiementResponseDTO;
import ServiCasa.Paiement.entity.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

     @Mapping(target = "id",ignore = true)
     Paiement toEntity(PaiementRequestDTO dto);

     PaiementResponseDTO toDto(Paiement paiement);

     List<PaiementResponseDTO> toDtoList(List<Paiement>paiements);

     @Mapping(target = "id",ignore = true)
     void updatePaiementDto(PaiementRequestDTO dto, @MappingTarget Paiement paiement);
}
