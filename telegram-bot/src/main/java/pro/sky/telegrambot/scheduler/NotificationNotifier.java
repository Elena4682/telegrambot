package pro.sky.telegrambot.scheduler;

import com.notification.bot.repository.NotificationRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java. util.concurrent.TimeUnit;

@Service
public class NotificationNotifier {
    private static final Logger logger = LoggerFactory.getLogger(NotificationNotifier.class);

    private final TelegramBot bot;
    private final NotificationRepository repository;

    public NotificationNotifier(TelegramBot bot,NotificationRepository repository){
        this.bot = bot;
        this.repository = repository;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixeDelay=1)
    public void notifyTask(){
        repository.findAllByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(task ->{
                    bot.execute(new SendMessage(task.getChatId(),task.getText()));
                    logger.info("Notification sent: {}", task);
                    repository.delete(task);
                });

    }

}