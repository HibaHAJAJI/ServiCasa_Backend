package ServiCasa.mapper;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.Reservation;
import ServiCasa.entity.Specialite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ReservationMapper {

     default String toSpecialiteNom(Specialite specialite) {
          return specialite == null ? null : specialite.getNom();
     }

     @Mapping(target = "id",ignore = true)
     Reservation toEntity(ReservationRequestDTO dto);

     @Mapping(target = "clientId", source = "client.id")
     @Mapping(target = "clientNom", source = "client.nom")
     @Mapping(target = "clientPrenom", source = "client.prenom")
     @Mapping(target = "artisanId", source = "artisan.id")
     @Mapping(target = "artisanNom", source = "artisan.nom")
     @Mapping(target = "artisanPrenom", source = "artisan.prenom")
     @Mapping(target = "artisanSpecialite", source = "artisan.specialite")
     @Mapping(target = "demandeServiceId", source = "demandeService.id")
     ReservationResponseDTO toDto(Reservation reservation);

     @Mapping(target = "id",ignore = true)
     void updateReservationDto(ReservationRequestDTO dto, @MappingTarget Reservation reservation);
}
