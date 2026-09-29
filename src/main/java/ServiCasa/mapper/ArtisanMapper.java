package ServiCasa.mapper;

import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface  ArtisanMapper {

     @Mapping(target = "id",ignore = true)
    Artisan toEntity(ArtisanRequestDTO dto);

     @Mapping(target = "moyenneAvis", ignore = true)
     @Mapping(target = "nombreAvis", ignore = true)
     ArtisanResponseDTO toDto(Artisan artisan);


     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     @Mapping(target = "id",ignore = true)
      void updateArtisanDto(ArtisanUpdateRequestDTO dto, @MappingTarget Artisan artisan);
}
