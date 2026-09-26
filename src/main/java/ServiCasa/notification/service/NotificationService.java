package ServiCasa.notification.service;

import ServiCasa.notification.dto.NotificationRequestDTO;
import ServiCasa.notification.dto.NotificationResponseDTO;


public interface NotificationService {

    void sendNotificationToUser(String userEmail, NotificationResponseDTO notification);

    void createAndSend(NotificationRequestDTO request, ServiCasa.entity.User user);
}
