package ServiCasa.notification.dto;

import ServiCasa.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {

    private NotificationType type;

    private String message;

    private LocalDateTime date;

    private Long reservationId;
}
