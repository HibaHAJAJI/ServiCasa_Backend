package ServiCasa.mapper;

import ServiCasa.dto.request.ArtisanRequestDTO;
import org.mapstruct.Mapper;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtisanMapper {

     @Mapping(target = "id",ignore = true)
     Artisan toEntity(ArtisanRequestDTO dto);

     ArtisanResponseDTO toDto(Artisan artisan);

     List<ArtisanResponseDTO> toDtoList(List<Artisan>artisans);

     @Mapping(target = "id",ignore = true)
     void updateArtisanDto(ArtisanRequestDTO dto, @MappingTarget Artisan artisan);
}
