package ServiCasa.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboards/artisan")
@RequiredArgsConstructor
public class DashboardArtisanController {

    private final DashboardArtisan dashboardArtisan;

    @GetMapping
    public DashboardArtisanResponseDTO getDashboard(Long artisanId) {
        return dashboardArtisan.getDashboard(artisanId);
    }
}