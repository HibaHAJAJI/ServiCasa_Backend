package ServiCasa.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {

    private String type;

    private String message;

    private LocalDateTime date;

    private Long reservationId;
}
