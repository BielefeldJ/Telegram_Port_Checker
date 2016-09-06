package telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Service;
import main.check.Checker;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Bot extends TelegramLongPollingBot
{

    private final List chatids = new ArrayList();

    @Override
    public String getBotToken()
    {
        return BotConfig.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage())
        {
            Message message = update.getMessage();
            if (message.hasText())
            {
                if ("/start".equals(message.getText()))
                {
                    chatids.add(message.getChatId());
                }
                if (message.getText().equals("/add"))
                {
                    try
                    {
                        Checker.getServices(message.getText().substring(message.getText().indexOf("add" + 1)));
                    }
                    catch (NumberFormatException e)
                    {
                        SendMessage err = new SendMessage();
                        err.setChatId(message.getChatId().toString());
                        err.setText("Wrong Port.... try again");
                        try
                        {
                            sendMessage(err);
                        }
                        catch (TelegramApiException ex)
                        {
                            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    public void fireOfflineMessage(Service s)
    {
        SendMessage message = new SendMessage();
        message.setText("Service " + s.toString() + " is down!");
        chatids.stream().map((id)
                -> 
                {
                    message.setChatId(id.toString());
                    return id;
        }).forEach((_item)
                -> 
                {
                    try
                    {
                        sendMessage(message);
                    }
                    catch (TelegramApiException ex)
                    {
                        System.out.println("Error sending offline message!");
                    }
        });
    }

    @Override
    public String getBotUsername()
    {
        return BotConfig.BOT_USERNAME;
    }

}
