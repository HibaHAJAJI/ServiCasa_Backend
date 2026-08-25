package ServiCasa.demandeService.controller;

import ServiCasa.demandeService.dto.DemandeServiceRequestDTO;
import ServiCasa.demandeService.dto.DemandeServiceResponseDTO;
import ServiCasa.demandeService.service.DemandeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandeservices")
@RequiredArgsConstructor
public class DemandeServiceController {


    private final DemandeServiceService demandeServiceService;

    @PostMapping
    public DemandeServiceResponseDTO createDemandeService(@RequestBody DemandeServiceRequestDTO dto){
        return demandeServiceService.addDemandeService(dto);
    }

    @PutMapping("/{id}")
    public DemandeServiceResponseDTO updateDemandeService(@RequestBody DemandeServiceRequestDTO dto, @PathVariable Long id){
        return demandeServiceService.updateDemandeService(id,dto);
    }

    @GetMapping
    public List<DemandeServiceResponseDTO> getAllDemandeServices(){
        return demandeServiceService.findAllDemandeServices();
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
