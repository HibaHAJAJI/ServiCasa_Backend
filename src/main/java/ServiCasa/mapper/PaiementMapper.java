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
     @Mapping(target = "reservation", ignore = true)
     @Mapping(target = "montant", ignore = true)
     @Mapping(target = "modePaiement", ignore = true)
     @Mapping(target = "statutPaiement", ignore = true)
     @Mapping(target = "datePaiement", ignore = true)
     Paiement toEntity(PaiementRequestDTO dto);

     @Mapping(target = "reservationId", source = "reservation.id")
     PaiementResponseDTO toDto(Paiement paiement);

     @Mapping(target = "id",ignore = true)
     @Mapping(target = "reservation", ignore = true)
     @Mapping(target = "montant", ignore = true)
     @Mapping(target = "modePaiement", ignore = true)
     @Mapping(target = "statutPaiement", ignore = true)
     @Mapping(target = "datePaiement", ignore = true)
     void updatePaiementDto(PaiementRequestDTO dto, @MappingTarget Paiement paiement);
}