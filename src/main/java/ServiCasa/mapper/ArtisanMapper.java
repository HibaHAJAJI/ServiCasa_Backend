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
public abstract class ArtisanMapper extends ReferenceMapper {

     @Mapping(target = "id",ignore = true)
     public abstract Artisan toEntity(ArtisanRequestDTO dto);

     @Mapping(target = "moyenneAvis", ignore = true)
     @Mapping(target = "nombreAvis", ignore = true)
     public abstract ArtisanResponseDTO toDto(Artisan artisan);

     /**
      * Un champ absent (null) du payload ne doit JAMAIS écraser la valeur existante en base.
      * Sans cela, un GET qui ne renvoie pas les champs professionnels suivis d'un PUT
      * détruirait specialite / anneesExperience / tarifHoraire / zoneIntervention / description.
      * Effacer un champ reste possible en envoyant une chaîne vide ("").
      */
     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     @Mapping(target = "id",ignore = true)
     public abstract void updateArtisanDto(ArtisanUpdateRequestDTO dto, @MappingTarget Artisan artisan);
}
