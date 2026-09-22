package ServiCasa.notification.service.impl;

import ServiCasa.entity.User;
import ServiCasa.notification.dto.NotificationRequestDTO;
import ServiCasa.notification.dto.NotificationResponseDTO;
import ServiCasa.notification.entity.Notification;
import ServiCasa.notification.mapper.NotificationMapper;
import ServiCasa.notification.repository.NotificationRepository;
import ServiCasa.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotificationToUser(String userEmail, NotificationResponseDTO notification) {
        if (userEmail != null && !userEmail.isEmpty()) {
            messagingTemplate.convertAndSendToUser(
                    userEmail,
                    "/queue/notifications",
                    notification
            );
        }
    }

    @Override
    public void createAndSend(NotificationRequestDTO request, User user) {
        Notification notification = notificationMapper.toEntity(request);
        notification.setUser(user);
        Notification saved = notificationRepository.save(notification);
        NotificationResponseDTO response = notificationMapper.toResponse(saved);
        sendNotificationToUser(user.getEmail(), response);
    }
}
