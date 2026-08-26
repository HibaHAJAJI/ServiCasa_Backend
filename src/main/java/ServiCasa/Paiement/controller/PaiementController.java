package ServiCasa.Paiement.controller;

import ServiCasa.Paiement.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {


    private final PaiementService paiementService;


}
