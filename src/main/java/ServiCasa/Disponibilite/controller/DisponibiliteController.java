package ServiCasa.Disponibilite.controller;

import ServiCasa.Disponibilite.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/disponibilites")
@RequiredArgsConstructor
public class DisponibiliteController {


    private final DisponibiliteService disponibiliteService;


}
