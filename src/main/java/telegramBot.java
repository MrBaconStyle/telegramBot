import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.PrivateKey;

public class telegramBot extends TelegramLongPollingBot {

    private static ListStorage listStorage = new ListStorage();
    private static Skrejp skrejp = new Skrejp();

    @Override
    public String getBotUsername() {
        return "skrejp_bot";
    }

    @Override
    public String getBotToken() {
        return "5914952957:AAHMHDBQCReDVrxHLPrrcMNXrZoUknpKlOg";
    }

    @Override
    public void onUpdateReceived(Update update) {

        //System.out.println(update.getMessage().getText());
        SendMessage sndMessage = new SendMessage();


        if (update.hasMessage()) {

            String command = update.getMessage().getText();
            Message rcvMessage = update.getMessage();

            if (rcvMessage.isCommand()) {

                if (command.startsWith(Command.EPIC)) {

                    //String url = "https://novi.kupujemprodajem.com/knjige/epska-fantastika/grupa/8/1095/1";
                    listStorage.addUrl("https://novi.kupujemprodajem.com/knjige/epska-fantastika/grupa/8/1095/");

                    sndMessage.setText("Tražim u Epskoj Fantastici...\n Unesi naziv knjige: ");

                    sndMessage.setChatId(update.getMessage().getChatId());

                    try {
                        execute(sndMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                } else if (command.startsWith(Command.SCIENCE)) {

                    //String url = "https://novi.kupujemprodajem.com/knjige/naucna-fantastika/grupa/8/355/1";
                    listStorage.addUrl("https://novi.kupujemprodajem.com/knjige/naucna-fantastika/grupa/8/355/");

                    sndMessage.setText("Tražim u Naučnoj Fantastici...\n Unesi naziv knjige: ");

                    sndMessage.setChatId(update.getMessage().getChatId());

                    try {
                        execute(sndMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }



                }
            } else {
                sndMessage.setText("Tražim Knjigu: " + command);

                sndMessage.setChatId(update.getMessage().getChatId());

                try {
                    execute(sndMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                String url = listStorage.getUrlList().get(0);
                System.out.println(url);
                System.out.println(command);
                listStorage.removeUrl();

                skrejp.scrape(url, command);

                for (int i=0; i<listStorage.bookListSize(); i++){
                    String bookNamePricePage = listStorage.printBookList(i);

                    sndMessage.setText(bookNamePricePage);

                    sndMessage.setChatId(update.getMessage().getChatId());

                    try {
                        execute(sndMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    listStorage.removeBook();

                }

                //myScrape(command, url);

            }

        }

    }

}

