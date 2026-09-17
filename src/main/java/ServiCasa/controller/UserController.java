package ServiCasa.controller;

import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.User;
import ServiCasa.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User>  getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getProfile(email)) ;
    }

    @PutMapping("/client/profile")
    public ResponseEntity<UserResponse> updateClientProfile(Authentication authentication, @RequestBody ClientUpdateRequestDTO dto) {
        UserResponse response = userService.updateClientProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/artisan/profile")
    public ResponseEntity<UserResponse> updateArtisanProfile(Authentication authentication, @RequestBody ArtisanUpdateRequestDTO dto) {
        UserResponse response = userService.updateArtisanProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateAdminProfile(Authentication authentication, @RequestBody UserUpdateRequestDTO dto) {
        UserResponse response = userService.updateUserProfile(authentication.getName(), dto);
        return ResponseEntity.ok(response);
    }
}