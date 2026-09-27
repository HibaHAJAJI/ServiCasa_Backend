package ServiCasa.controller;

import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.mapper.UserMapper;
import ServiCasa.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ArtisanMapper artisanMapper;
    private final ClientMapper clientMapper;


    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getProfile(email);
        return ResponseEntity.ok(toResponse(user));
    }

    private UserResponse toResponse(User user) {
        if (user instanceof Artisan artisan) {
            return artisanMapper.toDto(artisan);
        }
        if (user instanceof Client client) {
            return clientMapper.toDto(client);
        }
        return userMapper.toDto(user);
    }

    @PutMapping("/client/profile")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UserResponse> updateClientProfile(Authentication authentication, @RequestBody ClientUpdateRequestDTO dto) {
        UserResponse response = userService.updateClientProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/artisan/profile")
    @PreAuthorize("hasRole('ARTISAN')")
    public ResponseEntity<UserResponse> updateArtisanProfile(Authentication authentication, @RequestBody ArtisanUpdateRequestDTO dto) {
        UserResponse response = userService.updateArtisanProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateAdminProfile(Authentication authentication, @RequestBody UserUpdateRequestDTO dto) {
        UserResponse response = userService.updateUserProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }
}
