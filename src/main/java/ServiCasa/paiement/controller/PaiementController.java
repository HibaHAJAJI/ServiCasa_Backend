package ServiCasa.paiement.controller;

import ServiCasa.paiement.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {


    private final PaiementService paiementService;


}
