package ServiCasa.Artisan.mapper;

import ServiCasa.Artisan.dto.ArtisanRequestDTO;
import org.mapstruct.Mapper;
import ServiCasa.Artisan.dto.ArtisanResponseDTO;
import ServiCasa.Artisan.entity.Artisan;
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
