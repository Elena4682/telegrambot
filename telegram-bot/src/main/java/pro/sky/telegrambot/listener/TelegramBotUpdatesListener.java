package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot bot;
    private final List<Command> commands;

    public TelegramBotUpdatesListener(TelegramBot bot, List<Command> commands){
        this.bot = bot;
        this.commands = commands;
    }
    @PostConstruct
    void init(){
        bot.setUpdatesListener(this);
    }

    @java.lang.Override
    public int process(List<Update> updates) {
        for (Update update : updates){
            commands.stream()
                    .filter(command -> command.ifSuitable(update))
                    .forEach(command -> command.handle(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
