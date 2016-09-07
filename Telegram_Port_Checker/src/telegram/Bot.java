package telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.Logging;
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
    private String command;

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
                String msg = message.getText();
                if (msg.contains(" "))
                {
                    command = msg.substring(0, msg.indexOf(" "));
                }
                else
                {
                    command = msg;
                }
                switch (command)
                {
                    case "/start":
                        chatids.add(message.getChatId());
                        SendMessage newclient = new SendMessage();
                        newclient.setChatId(message.getChatId().toString());
                        newclient.setText("You will now receive offline messages!");
                        sendTelegramMessage(newclient);
                        Logging.log("New client: " + message.getChatId().toString());

                        break;
                    case "/add":
                        try
                        {
                            Checker.getServices(msg.substring(msg.indexOf(" ") + 1));
                            Logging.log("New Service added by " + message.getChatId().toString() + (msg.indexOf(" ") + 1));
                            SendMessage add = new SendMessage();
                            add.setChatId(message.getChatId().toString());
                            add.setText("Add new service "+ (msg.indexOf(" ") + 1));
                            sendTelegramMessage(add);
                        }
                        catch (NumberFormatException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(message.getChatId().toString());
                            err.setText("Wrong Port.... try again");
                            sendTelegramMessage(err);
                        }
                        break;
                    case "/stop":
                        chatids.remove(message.getChatId());
                        SendMessage remove = new SendMessage();
                        remove.setChatId(message.getChatId().toString());
                        remove.setText("This was my last message. Have a nice day!");
                        Logging.log("Removed Client " + message.getChatId().toString());
                        sendTelegramMessage(remove);
                        break;
                    case "/help":
                        SendMessage help = new SendMessage();
                        help.setChatId(message.getChatId().toString());
                        help.setText("List of available commands: \n\n /start: The bot stard inform you about offline services \n /stop The bot stop inform you about offline services \n /add <service>: You can add a service. Example: /add 127.0.0.1:80 \n /help: displays this message. \n ");
                        sendTelegramMessage(help);
                        break;
                    default:
                        SendMessage unknown = new SendMessage();
                        unknown.setChatId(message.getChatId().toString());
                        unknown.setText("Unknown commend. Use /help for a list of commands");
                        sendTelegramMessage(unknown);
                        break;

                }
            }
        }
    }

    public void fireOfflineMessage(Service s)
    {
        SendMessage message = new SendMessage();
        message.setText("Service " + s.toString() + " is down!");
        Logging.log("Service " + s.toString() + " is down!");
        chatids.stream().map((id) -> 
                {
                    message.setChatId(id.toString());
                    return id;
        }).forEach((_item) -> 
                {
                    sendTelegramMessage(message);                                        
        });
    }

    @Override
    public String getBotUsername()
    {
        return BotConfig.BOT_USERNAME;
    }

    private void sendTelegramMessage(SendMessage message)
    {
        try
        {
            sendMessage(message);
        }
        catch (TelegramApiException ex)
        {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
