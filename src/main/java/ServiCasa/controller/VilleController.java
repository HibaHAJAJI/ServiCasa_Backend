package ServiCasa.controller;


import ServiCasa.entity.Ville;
import ServiCasa.repository.VilleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/api/villes")
@RequiredArgsConstructor
public class VilleController {

    private final VilleRepository villeRepository;

    @GetMapping
    public List<Ville> getAll() {
        return villeRepository.findAll();
    }
}
