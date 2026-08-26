package ServiCasa.reservation.mapper;

import ServiCasa.reservation.dto.ReservationRequestDTO;
import ServiCasa.reservation.dto.ReservationResponseDTO;
import ServiCasa.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

     @Mapping(target = "id",ignore = true)
     Reservation toEntity(ReservationRequestDTO dto);

     ReservationResponseDTO toDto(Reservation reservation);

     List<ReservationResponseDTO> toDtoList(List<Reservation>reservations);

     @Mapping(target = "id",ignore = true)
     void updateReservationDto(ReservationRequestDTO dto, @MappingTarget Reservation reservation);
}
