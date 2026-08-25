package ServiCasa.Artisan.dto;

import lombok.*;
import ServiCasa.user.dto.UserRegisterRequest;

import java.time.LocalDate;


@Data
public class ArtisanRequestDTO extends UserRegisterRequest {
    private LocalDate dateNaissance;

}
