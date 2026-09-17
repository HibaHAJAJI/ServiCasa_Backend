package ServiCasa.mapper;

import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import org.mapstruct.Mapper;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ArtisanMapper {

     @Mapping(target = "id",ignore = true)
     Artisan toEntity(ArtisanRequestDTO dto);

     ArtisanResponseDTO toDto(Artisan artisan);

     @Mapping(target = "id",ignore = true)
     void updateArtisanDto(ArtisanUpdateRequestDTO dto, @MappingTarget Artisan artisan);
}
