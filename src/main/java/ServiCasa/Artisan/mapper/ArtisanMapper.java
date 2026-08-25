package ServiCasa.Artisan.mapper;

import org.mapstruct.Mapper;
import ServiCasa.Artisan.dto.ArtisanResponseDTO;
import ServiCasa.Artisan.entity.Artisan;

@Mapper(componentModel = "spring")
public interface ArtisanMapper {

     Artisan toEntity(ArtisanResponseDTO dto);
}
