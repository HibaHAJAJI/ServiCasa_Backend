package ServiCasa.mapper;

import ServiCasa.dto.request.AvisRequestDTO;
import ServiCasa.dto.response.AvisResponseDTO;
import ServiCasa.entity.Avis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AvisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "artisan", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    Avis toEntity(AvisRequestDTO dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientNom", source = "client.nom")
    @Mapping(target = "clientPrenom", source = "client.prenom")
    @Mapping(target = "artisanId", source = "artisan.id")
    @Mapping(target = "reservationId", source = "reservation.id")
    AvisResponseDTO toDto(Avis avis);
}