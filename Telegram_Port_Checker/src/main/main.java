package main;


import java.util.Scanner;
import logging.Logging;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.logging.BotLogger;
import telegram.Bot;

public class main
{

    private static final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
    public static final Bot bot = new Bot();

    public static void main(String[] args)
    {
        new Logging(); // initializiere logger
        boolean exit = false;
        Scanner sc = null;
        bot.load();
        try
        {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e)
        {
            BotLogger.error("Error register", e);
            System.out.println("Check BotConfig file!");
        }

        while (!exit)
        {
            System.out.println("Running.. type q to quit!");
            sc = new Scanner(System.in);
            if ("q".equals(sc.next()))
            {
                exit = true;
            }
        }

        sc.close();
        bot.save();
        System.exit(0);
    }

}
