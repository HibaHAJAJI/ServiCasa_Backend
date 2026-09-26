package ServiCasa.mapper;

import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import org.mapstruct.Mapper;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public abstract class ArtisanMapper extends ReferenceMapper {

     @Mapping(target = "id",ignore = true)
     public abstract Artisan toEntity(ArtisanRequestDTO dto);

     @Mapping(target = "moyenneAvis", ignore = true)
     @Mapping(target = "nombreAvis", ignore = true)
     public abstract ArtisanResponseDTO toDto(Artisan artisan);

     @Mapping(target = "id",ignore = true)
     public abstract void updateArtisanDto(ArtisanUpdateRequestDTO dto, @MappingTarget Artisan artisan);
}
