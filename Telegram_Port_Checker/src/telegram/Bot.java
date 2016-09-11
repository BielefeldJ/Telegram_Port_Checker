package telegram;

import data.Backup;
import exceptions.NoContentException;
import exceptions.NoServicesException;
import exceptions.ServiceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import logging.Logging;
import main.Client;
import main.Service;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Bot extends TelegramLongPollingBot implements Serializable
{

    private ArrayList<Client> clients = new ArrayList<>();

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
            String username = message.getChat().getFirstName();
            String command;
            String content = null;
            if (message.hasText())
            {
                String msg = message.getText();
                if (msg.contains(" "))
                {
                    command = msg.substring(0, msg.indexOf(" "));
                    content = msg.substring(msg.indexOf(" ") + 1);
                }
                else
                {
                    command = msg;
                }
                switch (command)
                {
                    case "/start":
                        clients.add(new Client(chatid, username));
                        findClient(chatid).start();
                        SendMessage newclient = new SendMessage();
                        newclient.setChatId(chatid);
                        newclient.setText("You will now receive offline messages! Check /help!");
                        sendTelegramMessage(newclient);
                        Logging.log("New client: ID: " + chatid + " " + username);

                        break;
                    case "/add":
                        try
                        {
                            checkForContent(content);
                            findClient(chatid).addService(content);
                            Logging.log("New Service added by " + chatid + " " + content + " " + username);
                            SendMessage add = new SendMessage();
                            add.setChatId(chatid);
                            add.setText("Add new service " + content);
                            sendTelegramMessage(add);
                        }
                        catch (NumberFormatException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Wrong Port.... try again");
                            sendTelegramMessage(err);
                        }
                        catch (NoContentException e)
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
                    case "/remove":
                        try
                        {
                            checkForContent(content);
                            findClient(chatid).delService(content);
                            Logging.log("Service removed by " + chatid + content + " " + username);
                            SendMessage add = new SendMessage();
                            add.setChatId(chatid);
                            add.setText("Removed service " + content);
                            sendTelegramMessage(add);
                        }
                        catch (NumberFormatException e)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Wrong Port.... try again");
                            sendTelegramMessage(err);
                        }
                        catch (NoContentException e)
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
                        Logging.log("Removed Client " + chatid + " " + username);
                        sendTelegramMessage(remove);
                        break;
                    case "/help":
                        SendMessage help = new SendMessage();
                        help.setChatId(chatid);
                        help.setText("List of available commands: \n\n/start: The bot stard inform you about offline services \n/add <IP:PORT>: You can add a service. Example: /add example.com:80 \n/remove <IP:PORT>: You can remove a service. Example: /remove example.com:80\n/list: Receve a list of all services. \n/status: Resece the status of your services.\n/info - Info about this bot.\n/help: displays this message. \n/stop - The bot stop inform you about offline services. (This command will delete all userdata!) ");
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
                    case "/status":
                        try
                        {
                            SendMessage status = new SendMessage();
                            status.setChatId(chatid);
                            status.setText(findClient(chatid).listStatus());
                            sendTelegramMessage(status);
                        }
                        catch (NoServicesException ex)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("You dont have any services.");
                            sendTelegramMessage(err);
                        }
                        break;
                    case "/info":
                        SendMessage info = new SendMessage();
                        info.setChatId(chatid);
                        info.setText("This bot will notify you whenever a service fails. \n If you have a question or you found a bug, let me know! Use /report <message>. \n Don't forget your email!");
                        sendTelegramMessage(info);
                        break;
                    case "/report":
                        SendMessage report = new SendMessage();
                        report.setChatId(BotConfig.BOT_ADMIN);
                        report.setText("Report: " + content + " From:" + username);
                        sendTelegramMessage(report);
                        break;
                    case "/login":
                        try
                        {
                            checkForContent(content);
                            if (BotConfig.check_PASSWORD(content))
                            {
                                BotConfig.BOT_ADMIN = chatid;
                                Logging.log(username + " - "+chatid + " Logged in!" );
                            }
                            else
                            {
                                Logging.log(username + " - "+chatid + " tryed to log in!" );
                            }
                        }
                        catch (NoContentException ex)
                        {
                            SendMessage err = new SendMessage();
                            err.setChatId(chatid);
                            err.setText("Nope!");
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

    public void fireOnlineMessage(Service s, String clid)
    {
        SendMessage message = new SendMessage();
        message.setText("Service " + s.toString() + " is up!");
        Logging.log("Service " + s.toString() + " is up!");
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

    public void sendAll(String text)
    {
        if (!clients.isEmpty())
        {
            SendMessage msg = new SendMessage();
            msg.setText(text);
            clients.stream().map((c)
                    -> 
                    {
                        msg.setChatId(c.getClientid());
                        return c;
            }).forEach((_item)
                    -> 
                    {
                        sendTelegramMessage(msg);
            });
        }
    }

    public void save()
    {
        Backup.save(clients);
    }

    public void load()
    {
        this.clients = Backup.load();
    }

    public void startAll()
    {
        clients.stream().forEach((c)
                -> 
                {
                    c.start();
        });
    }

    private static void checkForContent(String content) throws NoContentException
    {
        if (content == null)
        {
            throw new NoContentException();
        }
    }

}
