package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Disponibilite;
import ServiCasa.mapper.DisponibiliteMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.DisponibiliteRepository;
import ServiCasa.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisponibiliteImpl implements DisponibiliteService {

    private final DisponibiliteMapper mapper;
    private final DisponibiliteRepository repository;
    private final ArtisanRepository artisanRepository;

    @Override
    public DisponibiliteResponseDTO addDisponibilite(DisponibiliteRequestDTO dto) {
        Artisan artisan = artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "artisan introuvable!"));

        if (Boolean.TRUE.equals(dto.getDisponible())) {
            if (dto.getHeureDebut() == null || dto.getHeureFin() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les heures de début et de fin sont obligatoires quand la date est disponible");
            }
            if (!dto.getHeureDebut().isBefore(dto.getHeureFin())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'heure de début doit être avant l'heure de fin");
            }
        }

        Disponibilite disponibilite = mapper.toEntity(dto);
        disponibilite.setArtisan(artisan);
        return mapper.toDto(repository.save(disponibilite));
    }

    @Override
    public Page<DisponibiliteResponseDTO> findAllDisponibilites(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public DisponibiliteResponseDTO findDisponibiliteById(Long id) {
        Disponibilite disponibilite = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilite introuvable !"));
        return mapper.toDto(disponibilite);
    }

    @Override
    public Page<DisponibiliteResponseDTO> findByArtisanId(Long artisanId, Pageable pageable) {
        return repository.findByArtisanId(artisanId,pageable).map(mapper::toDto);
    }

    @Override
    public List<DisponibiliteResponseDTO> findByArtisanIdAndDate(Long artisanId, LocalDate date) {
        return repository.findByArtisanIdAndDateAndDisponibleIsTrue(artisanId, date).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DisponibiliteResponseDTO updateDisponibilite(DisponibiliteRequestDTO dto, Long id) {
        Disponibilite disponibilite = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilite introuvable !"));

        if (Boolean.TRUE.equals(dto.getDisponible())) {
            if (dto.getHeureDebut() == null || dto.getHeureFin() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les heures de début et de fin sont obligatoires quand la date est disponible");
            }
            if (!dto.getHeureDebut().isBefore(dto.getHeureFin())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'heure de début doit être avant l'heure de fin");
            }
        }

        mapper.updateDisponibiliteDto(dto, disponibilite);

        Disponibilite update = repository.save(disponibilite);
        return mapper.toDto(update);
    }

    @Override
    public void deleteDisponibilite(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilite introuvable !");
        }
        repository.deleteById(id);
    }
}
