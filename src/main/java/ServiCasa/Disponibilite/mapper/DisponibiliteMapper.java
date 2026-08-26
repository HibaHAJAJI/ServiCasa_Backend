package ServiCasa.Disponibilite.mapper;

import ServiCasa.Disponibilite.dto.DisponibiliteRequestDTO;
import ServiCasa.Disponibilite.dto.DisponibiliteResponseDTO;
import ServiCasa.Disponibilite.entity.Disponibilite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisponibiliteMapper {

     @Mapping(target = "id",ignore = true)
     Disponibilite toEntity(DisponibiliteRequestDTO dto);

     DisponibiliteResponseDTO toDto(Disponibilite disponibilite);

     List<DisponibiliteResponseDTO> toDtoList(List<Disponibilite>disponibilites);

     @Mapping(target = "id",ignore = true)
     void updateDisponibiliteDto(DisponibiliteRequestDTO dto, @MappingTarget Disponibilite disponibilite);
}
