package telegram;

import data.Backup;
import exceptions.NoServicesException;
import exceptions.ServiceNotFoundException;
import java.util.ArrayList;
import logging.Logging;
import main.Client;
import main.Service;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Bot extends TelegramLongPollingBot
{

    private ArrayList<Client> clients = new ArrayList<>();
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
            String chatid = message.getChatId().toString();
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
                        clients.add(new Client(chatid));
                        findClient(chatid).start();
                        SendMessage newclient = new SendMessage();
                        newclient.setChatId(chatid);
                        newclient.setText("You will now receive offline messages! Check /help!");
                        sendTelegramMessage(newclient);
                        Logging.log("New client: ID: " + chatid );

                        break;
                    case "/add":
                        try
                        {
                            findClient(chatid).addService(msg.substring(msg.indexOf(" ") + 1));
                            Logging.log("New Service added by " + chatid + (msg.indexOf(" ") + 1));
                            SendMessage add = new SendMessage();
                            add.setChatId(chatid);
                            add.setText("Add new service " + msg.substring(msg.indexOf(" ") + 1));
                            sendTelegramMessage(add);
                        }
                        catch (NumberFormatException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Wrong Port.... try again");
                            sendTelegramMessage(err);
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("No service found.. check /help!");
                            sendTelegramMessage(err);
                        }
                        catch (NullPointerException e)
                        {
                            Logging.log(chatid + " cant add Service.");
                        }
                        break;
                    case "/del":
                        try
                        {
                            findClient(chatid).delService(msg.substring(msg.indexOf(" ") + 1));
                            Logging.log("Service removed by " + chatid + (msg.indexOf(" ") + 1));
                            SendMessage add = new SendMessage();
                            add.setChatId(chatid);
                            add.setText("Removed service " + msg.substring(msg.indexOf(" ") + 1));
                            sendTelegramMessage(add);
                        }
                        catch (NumberFormatException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Wrong Port.... try again");
                            sendTelegramMessage(err);
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("No service found.. check /help!");
                            sendTelegramMessage(err);
                        }
                        catch (NullPointerException e)
                        {
                            Logging.log(chatid + "cant add Service.");
                        }
                        catch (NoServicesException ex)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("You dont have any services.");
                            sendTelegramMessage(err);
                        }
                        catch (ServiceNotFoundException ex)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Service not found.");
                            sendTelegramMessage(err);
                        }
                        break;
                    case "/stop":
                        Client c = findClient(chatid);
                        clients.remove(c);
                        c.stop();
                        SendMessage remove = new SendMessage();
                        remove.setChatId(chatid);
                        remove.setText("This was my last message. Have a nice day!");
                        Logging.log("Removed Client " + chatid);
                        sendTelegramMessage(remove);
                        break;
                    case "/help":
                        SendMessage help = new SendMessage();
                        help.setChatId(chatid);
                        help.setText("List of available commands: \n\n /start: The bot stard inform you about offline services \n /stop: The bot stop inform you about offline services \n /add <service>: You can add a service. Example: /add 127.0.0.1:80 \n /help: displays this message. \n ");
                        sendTelegramMessage(help);
                        break;
                    case "/list":
                        try
                        {
                            SendMessage list = new SendMessage();
                            list.setChatId(chatid);
                            list.setText(findClient(chatid).listServices());
                            sendTelegramMessage(list);
                        }
                        catch (NoServicesException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("You dont have any services.");
                            sendTelegramMessage(err);
                        }
                        break;
                    default:
                        SendMessage unknown = new SendMessage();
                        unknown.setChatId(chatid);
                        unknown.setText("Unknown commend. Use /help for a list of commands");
                        sendTelegramMessage(unknown);
                        break;

                }
            }
        }
    }

    public void fireOfflineMessage(Service s, String clid)
    {
        SendMessage message = new SendMessage();
        message.setText("Service " + s.toString() + " is down!");
        Logging.log("Service " + s.toString() + " is down!");
        message.setChatId(clid);
        sendTelegramMessage(message);
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
            System.out.println("Send Message failed...");
        }
    }

    private Client findClient(String id)
    {
        for (Client c : clients)
        {
            if (c.getClientid().equals(id))
            {
                return c;
            }
        }
        return null;
    }
    
    public void save()
    {
        Backup.save(clients);
    }
    
    public void load()
    {
        this.clients = Backup.load();
    }

}
