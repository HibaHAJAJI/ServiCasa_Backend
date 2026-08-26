package ServiCasa.reservation.controller;

import ServiCasa.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {


    private final ReservationService reservationService;


}
