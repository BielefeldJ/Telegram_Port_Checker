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

    List<Service> services;
    boolean running = true;
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

    public Check_services(List services)
    {
        this.services = services;
    }

    @Override
    public void run()
    {
        try
        {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e)
        {
            BotLogger.error("Error register", e);
        }

        while (!Thread.currentThread().isInterrupted())
        {
            for (Service s : services)
            {
                if (!s.portIsOpen())
                {
                    
                }
            }
            try
            {
                System.out.println("Waiting..");
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
