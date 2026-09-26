package ServiCasa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvisResponseDTO {

    private Long id;

    private Integer note;

    private String commentaire;

    private LocalDateTime dateCreation;

    private Long clientId;

    private String clientNom;

    private String clientPrenom;

    private Long artisanId;

    private Long reservationId;
}