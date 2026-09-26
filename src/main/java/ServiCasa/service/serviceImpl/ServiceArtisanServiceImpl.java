package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ServiceArtisanRequestDTO;
import ServiCasa.dto.response.ServiceArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Categorie;
import ServiCasa.entity.ServiceArtisan;
import ServiCasa.mapper.ServiceArtisanMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.repository.ServiceArtisanRepository;
import ServiCasa.service.ServiceArtisanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ServiceArtisanServiceImpl implements ServiceArtisanService {

    private final ServiceArtisanMapper mapper;
    private final ServiceArtisanRepository repository;
    private final ArtisanRepository artisanRepository;
    private final CategorieRepository categorieRepository;

    @Override
    public ServiceArtisanResponseDTO createService(ServiceArtisanRequestDTO dto, String artisanEmail) {
        Artisan artisan = getArtisanFromEmail(artisanEmail);

        Categorie categorie = null;
        if (dto.getCategorieId() != null) {
            categorie = categorieRepository.findById(dto.getCategorieId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie introuvable"));
        }

        ServiceArtisan service = mapper.toEntity(dto);
        service.setArtisan(artisan);
        service.setCategorie(categorie);

        return mapper.toDto(repository.save(service));
    }

    @Override
    public Page<ServiceArtisanResponseDTO> getServicesByArtisan(Long artisanId,Pageable pageable) {
        return repository.findByArtisanId(artisanId,pageable).map(mapper::toDto);
    }

    @Override
    public ServiceArtisanResponseDTO getServiceById(Long id) {
        ServiceArtisan service = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service introuvable"));
        return mapper.toDto(service);
    }

    @Override
    public ServiceArtisanResponseDTO updateService(Long id, ServiceArtisanRequestDTO dto, String artisanEmail) {
        Artisan artisan = getArtisanFromEmail(artisanEmail);

        ServiceArtisan service = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service introuvable"));

        if (!service.getArtisan().getId().equals(artisan.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ce service ne vous appartient pas");
        }

        Categorie categorie = null;
        if (dto.getCategorieId() != null) {
            categorie = categorieRepository.findById(dto.getCategorieId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie introuvable"));
        }

        mapper.updateServiceArtisanDto(dto, service);
        service.setCategorie(categorie);

        return mapper.toDto(repository.save(service));
    }

    @Override
    public void deleteService(Long id, String artisanEmail) {
        Artisan artisan = getArtisanFromEmail(artisanEmail);

        ServiceArtisan service = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service introuvable"));

        if (!service.getArtisan().getId().equals(artisan.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ce service ne vous appartient pas");
        }

        repository.deleteById(id);
    }

    @Override
    public Page<ServiceArtisanResponseDTO> getServicesByArtisanEmail(String artisanEmail, Pageable pageable) {
        Artisan artisan = getArtisanFromEmail(artisanEmail);
        return getServicesByArtisan(artisan.getId(),pageable);
    }

    private Artisan getArtisanFromEmail(String email) {
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Artisan non authentifié");
        }
        return artisanRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));
    }
}