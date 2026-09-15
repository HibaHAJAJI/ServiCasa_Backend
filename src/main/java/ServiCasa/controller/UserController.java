package ServiCasa.controller;

import ServiCasa.dto.request.UserRegisterRequest;
import ServiCasa.dto.response.UserResponse;
import ServiCasa.mapper.UserMapper;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public UserResponse getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
    }

    @PutMapping("/profile")
    public UserResponse updateProfile(Authentication authentication, @RequestBody UserRegisterRequest dto) {
        return userService.updateCurrentUser(authentication.getName(), dto);
    }
}