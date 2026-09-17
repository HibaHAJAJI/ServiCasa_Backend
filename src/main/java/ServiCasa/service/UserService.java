package ServiCasa.service;


import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import ServiCasa.enums.Role;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.mapper.UserMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    private final ArtisanRepository artisanRepository;
    private final ArtisanMapper artisanMapper;

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user;

    }

    public User getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ARTISAN) {
            return artisanRepository.findById(user.getId())
                    .map(artisan -> (User) artisan)
                    .orElse(user);
        }

        if (user.getRole() == Role.CLIENT) {
            return clientRepository.findById(user.getId())
                    .map(client -> (User) client)
                    .orElse(user);
        }

        return user;
    }

    public UserResponse updateUserProfile(String email, UserUpdateRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email: " + email));

        mapper.updateUserDto(dto, user);
        userRepository.save(user);

        return mapper.toDto(user);
    }

    public UserResponse updateClientProfile(String email, ClientUpdateRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email: " + email));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Client introuvable!"));

        clientMapper.updateClientDto(dto, client);
        clientRepository.save(client);

        return clientMapper.toDto(client);
    }

    public UserResponse updateArtisanProfile(String email, ArtisanUpdateRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email: " + email));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Artisan introuvable!"));

        artisanMapper.updateArtisanDto(dto, artisan);
        artisanRepository.save(artisan);

        return artisanMapper.toDto(artisan);
    }
}
