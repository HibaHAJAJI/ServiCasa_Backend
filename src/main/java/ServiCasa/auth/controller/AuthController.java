package ServiCasa.auth.controller;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.auth.service.AuthService;
import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.request.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register/artisan")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody ArtisanRequestDTO dto) {
        return ResponseEntity.ok(authService.registerArtisan(dto));
    }

}
