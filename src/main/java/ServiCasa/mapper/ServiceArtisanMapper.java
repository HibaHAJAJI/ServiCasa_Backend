package ServiCasa.mapper;

import ServiCasa.dto.request.ServiceArtisanRequestDTO;
import ServiCasa.dto.response.ServiceArtisanResponseDTO;
import ServiCasa.entity.ServiceArtisan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceArtisanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artisan", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    ServiceArtisan toEntity(ServiceArtisanRequestDTO dto);

    @Mapping(target = "artisanId", source = "artisan.id")
    @Mapping(target = "categorieId", source = "categorie.id")
    @Mapping(target = "categorieNom", source = "categorie.nom")
    ServiceArtisanResponseDTO toDto(ServiceArtisan serviceArtisan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artisan", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    void updateServiceArtisanDto(ServiceArtisanRequestDTO dto, @MappingTarget ServiceArtisan serviceArtisan);
}