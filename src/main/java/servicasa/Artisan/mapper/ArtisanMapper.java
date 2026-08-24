package servicasa.Artisan.mapper;

import org.mapstruct.Mapper;
import servicasa.Artisan.dto.ArtisanResponseDTO;
import servicasa.Artisan.entity.Artisan;

@Mapper(componentModel = "spring")
public interface ArtisanMapper {

     Artisan toEntity(ArtisanResponseDTO dto);
}
