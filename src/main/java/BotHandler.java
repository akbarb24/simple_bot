import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotHandler extends TelegramLongPollingBot {
    private final String BOT_TOKEN = "your bot token";
    private final String BOT_USERNAME = "your bot username";
    private String [][] textMsg = {
            {"/start"},
            {"Hello ", "Hi "},
            //
            {"hi", "hello", "hoi"},
            {"What's up?", "Yes", "Hi!"},
            //
            {"How r you", "kenalan yuk"},
            {"Fine, thanks!", "Not well", "I'm okay"},
            //default
            {"Sorry I'm Busy"}
    };

    @Override
    public void onUpdateReceived(Update update) {
        String response = responseMsg(update.getMessage().getText());
        if(update.getMessage().getText().equals("/start")){
            response += update.getMessage().getChat().getFirstName();
        }
        SendMessage send = new SendMessage();
        send.setChatId(update.getMessage().getChatId());
        send.setText(response);
        try {
            execute(send);
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private String responseMsg(String txInput){
        String txOutput = "";
        txInput.trim();
        while (
                txInput.charAt(txInput.length()-1) == '.' ||
                        txInput.charAt(txInput.length()-1) == '!' ||
                        txInput.charAt(txInput.length()-1) == '?'
                ){
            txInput.substring(0, txInput.length()-1);
        }
        txInput.trim();
        byte response = 0;
        int j = 0;
        /*
        0: looking for response
        1: response not found
        2: response is found
        */
        while (response == 0){
            if(inResponse(txInput.toLowerCase(), textMsg[j*2])){
                int r = (int)Math.floor(Math.random()*textMsg[(j*2)+1].length);
                txOutput = textMsg[(j*2)+1][r];
                response = 2;
            }
            j++;
            if(j*2 == textMsg.length - 1 && response == 0){
                response = 1;
            }
        }
        if(response == 1){
            int r =(int)Math.floor(Math.random()*textMsg[textMsg.length-1].length);
            txOutput = textMsg[textMsg.length-1][r];
        }
        return txOutput;
    }

    private boolean inResponse(String in, String[] arr){
        boolean match = false;
        for(int i=0; i < arr.length; i++){
            if(arr[i].equals(in)){
                match = true;
            }
        }
        return match;
    }
}
