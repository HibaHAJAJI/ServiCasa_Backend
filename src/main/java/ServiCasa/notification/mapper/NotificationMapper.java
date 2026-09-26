package ServiCasa.notification.mapper;

import ServiCasa.notification.dto.NotificationRequestDTO;
import ServiCasa.notification.dto.NotificationResponseDTO;
import ServiCasa.notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationRequestDTO dto);

    NotificationResponseDTO toResponse(Notification notification);
}
