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

     ReservationResponseDTO toDto(Reservation reservation);

     @Mapping(target = "id",ignore = true)
     void updateReservationDto(ReservationRequestDTO dto, @MappingTarget Reservation reservation);
}
