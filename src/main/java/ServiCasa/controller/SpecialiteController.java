package ServiCasa.controller;

import ServiCasa.entity.Specialite;
import ServiCasa.repository.SpecialiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/specialites")
@RequiredArgsConstructor
public class SpecialiteController {

    private final SpecialiteRepository specialiteRepository;

    @GetMapping
    public List<Specialite> getAll() {
        return specialiteRepository.findAll();
    }
}
