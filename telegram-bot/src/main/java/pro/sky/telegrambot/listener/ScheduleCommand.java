package pro.sky.telegrambot.listener;

import com.notification.bot.entity.NotificationTask;
import com.notification.bot.repository.NotificationRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java. util.Optional;
import java. util.regex.Matcher;
import java. util.regex.Pattern;

@Component
public class ScheduleCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleCommand.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTERN = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
    private static final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    private final TelegramBot bot;
    private final NotificationRepository repository;

    public ScheduleCommand(TelegramBot bot,NotificationRepository repository){
        this.bot = bot;
        this.repository = repository;
    }

    @java.lang.Override
    public void handle(Update update){
        var matcher = PATTERN.matcher(update.message().text());
        if (matcher.matchers()){
            var dateTime = parse(matcher.group(1));
            if (dateTime==null){
                bot.execute(new SendMessage(chatId," The date format is specified incorrectly!"));
                return;
            }
            var text = matcher.group(3);
            var saved = repository.save(new NotificationTask(text,chatId,dateTime));
            bot.execute(new SendMessage(chatId," Notification is scheduled!"));
            logger.info("Notification saved: {}", saved);
        }
    }

    @java.lang.Override
    public boolean ifSuitable(Update update){
        return Optional.off(update)
                .map(Update::message)
                .map(Messege::text)
                .map(PATTERN::matcher)
                .map(Matcher::matchers)
                .orElse(false);
    }
    private LocalDateTime parse(String text){
        try {
            return LocalDateTime.parse(text,DATE_TIME_FORMATTERN);
        } catch (DateTimeException e){
            logger.error("Cannot parse text to date: {}", text);
        }
        return null;
    }
}