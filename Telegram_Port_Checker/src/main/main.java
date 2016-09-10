package main;

import java.util.Scanner;
import logging.Logging;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.logging.BotLogger;
import telegram.Bot;

public class main
{

    private static final TelegramBotsApi TBOTAPI = new TelegramBotsApi();
    public static final Bot TBOT = new Bot();

    public static void main(String[] args)
    {
        Logging logging = new Logging(); // initializiere logger
        boolean exit = false;
        try (Scanner sc = new Scanner(System.in))
        {
            TBOT.load();
            TBOT.sendAll("We are back!");
            try
            {
                TBOTAPI.registerBot(TBOT);
            }
            catch (TelegramApiException e)
            {
                BotLogger.error("Error register", e);
                System.out.println("Check BotConfig file!");
            }
            while (!exit)
            {
                System.out.println("Running.. type q to quit!");
                if ("q".equals(sc.next()))
                {
                    exit = true;
                }
            }
            System.out.println("Enter a reason: ");
            TBOT.sendAll("Bot is going down. Reason: " + sc.next());
        }
        TBOT.save();        
        System.exit(0);
    }

}
