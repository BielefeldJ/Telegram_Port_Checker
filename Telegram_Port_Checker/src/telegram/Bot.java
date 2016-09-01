package telegram;

import java.util.ArrayList;
import java.util.List;
import main.Service;
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
            //check if the message has text. it could also contain for example a location ( message.hasLocation() )
            if (message.hasText())
            {
                //create an object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get from the message the sender that sent it.
                chatids.add(message.getChatId());
                sendMessageRequest.setText("Okay. Starting..");
                try
                {
                    sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                }
                catch (TelegramApiException e)
                {
                    //do some error handling
                }
            }
        }
    }
    
    public void fireOfflineMessage(Service s)
    {
        SendMessage message = new SendMessage();
        message.setText("Service " + s.toString() + "is down!");
        for(Object id : chatids)
        {
            message.setChatId(id.toString());
            try
            {
                sendMessage(message);
            }
            catch (TelegramApiException ex)
            {
                System.out.println("Error sending offline message!");
            }
        }
        
    }

    @Override
    public String getBotUsername()
    {
        return BotConfig.BOT_USERNAME;
    }

}
