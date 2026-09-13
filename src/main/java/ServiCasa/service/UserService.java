package ServiCasa.service;


import ServiCasa.dto.request.UserRegisterRequest;
import ServiCasa.dto.response.UserResponse;
import ServiCasa.entity.User;
import ServiCasa.mapper.UserMapper;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user;

    }

    public UserResponse updateCurrentUser(String email, UserRegisterRequest dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        mapper.updateUserDto(dto, user);

        User updatedUser = userRepository.save(user);

        return mapper.toDto(updatedUser);
    }
}
