package ServiCasa.dashboard.artisan;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard/artisan")
@RequiredArgsConstructor
public class DashboardArtisanController {

    private final DashboardArtisan dashboardArtisan;

    @GetMapping
    public DashboardArtisanResponseDTO getDashboard(Authentication authentication) {
        String email = authentication.getName();
        return dashboardArtisan.getDashboard(email);
    }
}