package ServiCasa.controller;

import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;
import ServiCasa.service.DemandeServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/demandeservices")
@RequiredArgsConstructor
public class DemandeServiceController {


    private final DemandeServiceService demandeServiceService;

    @PostMapping
    public DemandeServiceResponseDTO createDemandeService(@Valid  @RequestBody DemandeServiceRequestDTO dto){
        return demandeServiceService.addDemandeService(dto);
    }

    @PutMapping("/{id}")
    public DemandeServiceResponseDTO updateDemandeService(@Valid @RequestBody DemandeServiceRequestDTO dto, @PathVariable Long id){
        return demandeServiceService.updateDemandeService(id,dto);
    }

    @GetMapping
    public Page<DemandeServiceResponseDTO> getAllDemandeServices(Pageable pageable){
        return demandeServiceService.findAllDemandeServices(pageable);
    }


    @GetMapping("/{id}")
    public DemandeServiceResponseDTO getById(@PathVariable Long id){
        return demandeServiceService.findDemandeServiceById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDemandeServiceById(@PathVariable Long id){
        demandeServiceService.deleteDemandeService(id);
    }
}
