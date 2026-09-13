package ServiCasa.controller;

import ServiCasa.dto.request.UserRegisterRequest;
import ServiCasa.dto.response.UserResponse;
import ServiCasa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public UserResponse updateProfile(@RequestParam String email, @RequestBody UserRegisterRequest dto) {
        return userService.updateCurrentUser(email,dto);
    }
}
