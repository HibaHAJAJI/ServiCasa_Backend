package ServiCasa.categorie.mapper;

import ServiCasa.categorie.dto.CategorieRequestDTO;
import ServiCasa.categorie.dto.CategorieResponseDTO;
import ServiCasa.categorie.entity.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategorieMapper {

     @Mapping(target = "id",ignore = true)
     Categorie toEntity(CategorieRequestDTO dto);

     CategorieResponseDTO toDto(Categorie categorie);

     List<CategorieResponseDTO> toDtoList(List<Categorie>categories);

     @Mapping(target = "id",ignore = true)
     void updateCategorieDto(CategorieRequestDTO dto, @MappingTarget Categorie categorie);
}
