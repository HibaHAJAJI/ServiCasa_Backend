package ServiCasa.auth.service.impl;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.auth.service.AuthService;
import ServiCasa.entity.User;
import ServiCasa.repository.UserRepository;
import ServiCasa.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO login(AuthRequestDTO dto){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( dto.getEmail(),
                        dto.getPassword()));

        User user =userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,("Identifiants invalides")));

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token);

    }

}
