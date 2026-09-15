package ServiCasa.mapper;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ReservationMapper {

     @Mapping(target = "id",ignore = true)
     Reservation toEntity(ReservationRequestDTO dto);

     @Mapping(target = "clientId", source = "client.id")
     @Mapping(target = "artisanId", source = "artisan.id")
     @Mapping(target = "demandeServiceId", source = "demandeService.id")
     @Mapping(target = "paiementId", source = "paiement.id")
     ReservationResponseDTO toDto(Reservation reservation);

     @Mapping(target = "id",ignore = true)
     void updateReservationDto(ReservationRequestDTO dto, @MappingTarget Reservation reservation);
}
