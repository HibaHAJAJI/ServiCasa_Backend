package ServiCasa.dashboard.admin;


import ServiCasa.dashboard.artisan.DashboardArtisanResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard/admin")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final dashboardAdminService dashboardAdminService;

    @GetMapping
    public DashboardAdminResponseDTO getDashboard(Authentication authentication){
        String email = authentication.getName();
        return dashboardAdminService.getDashboardAdmin();

    }
}
