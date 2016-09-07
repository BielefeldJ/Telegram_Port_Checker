package main.check;

import java.util.List;

import main.Service;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.logging.BotLogger;
import telegram.Bot;

/**
 *
 * @author Admin
 */
public class Check_services implements Runnable
{

    private final List<Service> services;
    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
    private final Bot bot = new Bot();

    

    public Check_services(List services)
    {
        this.services = services;
    }

    @Override
    public void run()
    {
        try
        {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e)
        {
            BotLogger.error("Error register", e);
            System.out.println("Check BotConfig file!");
        }

        while (!Thread.currentThread().isInterrupted())
        {
            services.stream().forEach((s) ->
            {
                if (!s.portIsOpen())
                {
                    if(!s.isMessage_sended())
                    {
                        bot.fireOfflineMessage(s);
                        s.toggleMessageSended();
                    }                    
                }
                else
                {
                    if(s.isMessage_sended())
                    {
                        s.toggleMessageSended();
                    }
                }
            });
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Thread closed....");                
                Thread.currentThread().interrupt();
            }
        }
    }
}
