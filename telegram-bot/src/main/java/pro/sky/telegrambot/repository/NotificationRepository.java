package pro.sky.telegrambot.repository;

import com.notification.bot.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findAllByDateTime(LocalDateTime dateTime);
}