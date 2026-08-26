package ServiCasa.disponibilite.mapper;

import ServiCasa.disponibilite.dto.DisponibiliteRequestDTO;
import ServiCasa.disponibilite.dto.DisponibiliteResponseDTO;
import ServiCasa.disponibilite.entity.Disponibilite;
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
