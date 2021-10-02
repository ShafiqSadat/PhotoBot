import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PhotoBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()){
            var messageText = update.getMessage().getText();
            var userId = update.getMessage().getFrom().getId();
            if (messageText.equalsIgnoreCase("photo")){
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(userId.toString());
                sendPhoto.setCaption("This is a test!");
                sendPhoto.setPhoto(new InputFile(new File("./test.jpg.png")));
                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
//        else if (update.getMessage().hasPhoto()){
//            var fileId = update.getMessage().getPhoto().get(0).getFileId();
//            SendPhoto sendPhoto  = new SendPhoto();
//            sendPhoto.setChatId(update.getMessage().getChatId().toString());
//            sendPhoto.setPhoto(new InputFile(fileId));
//            sendPhoto.setCaption("This is a test");
//            try {
//                execute(sendPhoto);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
        else if (update.getMessage().hasPhoto()){
            var fileId = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() -1).getFileId();
            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);
            try {
                org.telegram.telegrambots.meta.api.objects.File linkFile = execute(getFile);
                var link = linkFile.getFileUrl(getBotToken());
                FileUtils.copyURLToFile(new URL(link), new File("./testPhoto.jpg"));
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "AnaabMyBot";
    }

    @Override
    public String getBotToken() {
        return "2032522351:AAHReO0nMJHxwf_S7Of3Y8STf4MLrThpdsU";
    }

}
