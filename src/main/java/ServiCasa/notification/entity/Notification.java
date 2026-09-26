package ServiCasa.notification.entity;

import ServiCasa.entity.User;
import ServiCasa.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    private LocalDateTime date;

    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
