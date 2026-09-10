package ServiCasa.auth.service.impl;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.auth.service.AuthService;
import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import ServiCasa.enums.Role;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
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

    private final ArtisanMapper artisanMapper;
    private final ArtisanRepository artisanRepository;

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;



    @Override
    public AuthResponseDTO login(AuthRequestDTO dto){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( dto.getEmail(),
                        dto.getPassword()));

        User user =userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,("Identifiants invalides")));

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token);

    }

    @Override
    public AuthResponseDTO registerArtisan(ArtisanRequestDTO request){

            if(userRepository.findByEmail(request.getEmail()).isPresent()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,("Email déjà exists"));
            }

            Artisan artisan =artisanMapper.toEntity(request);
            artisan.setPassword(passwordEncoder.encode(request.getPassword()));
            artisan.setRole(Role.ARTISAN);

            Artisan savedArtisan = artisanRepository.save(artisan);
            String token = jwtService.generateToken(savedArtisan);

            return new AuthResponseDTO(token);

    }

    @Override
    public AuthResponseDTO registerClient(ClientRequestDTO request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,("Email déjà exists"));
        }

        Client client =clientMapper.toEntity(request);
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setRole(Role.CLIENT);

        Client savedClient = clientRepository.save(client);
        String token = jwtService.generateToken(savedClient);

        return new AuthResponseDTO(token);
    }


}
