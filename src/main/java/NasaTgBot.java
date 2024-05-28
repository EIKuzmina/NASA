import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class NasaTgBot extends TelegramLongPollingBot {
    //копируем весь код из документации и добавляем наследование. Помимо этого необходимо прописать для программы: имя бота, токен бота, ссылка на сервер Nasa
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key" +
            "=3ubfybCcsCxscUgh3MIJ0FPMzrgdiPcB6qGKosjZ";

    //делаем конструктор, необходимо зарегистрировать нашу программу, чтобы она могла управлять ботом, в самом тг и связать её с ботом. Через "TelegramBotsApi" регистрируем нашего бота и регистрируемся
    //java предлагает проверить на ошибки, НО если тг не работает, то пусть и наша программа не работает, мы согласны чтобы наша программа упала с ошибкой
    public NasaTgBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    //метод вызывается тогда, когда нашему боту кто-то пишет
    //копируем из документации и выносим в отдельный метод, "String action" - это то, что нам напишет человек
    // в зависимости от того, что нам напишут, мы будеи реагировать по-разному, делаем через оператор-множества switch
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId(); //номер чата
            String answer = update.getMessage().getText();
            String[] sepAnswer = answer.split(" ");
            String action = sepAnswer[0];

            switch (action) {
                case "/start":
                    sendMessage("Привет! Я бот NASA, присылаю картинку дня.:)" +
                            "\nЧтобы получить картинку от другого числа выберете: Дата", chatId);
                    break;
                case "/help":
                    sendMessage("Выберете: Фото для получения картинки дня", chatId);
                    break;
                case "/photo":
                    String image = Utils.getURL(NASA_URL);
                    sendMessage(image, chatId);
                    break;
                case "/date":
                    sendMessage("Введите дату в формате - год ГГГГ-ММ-ДД" +
                            "\nНапример: год 2023-12-31", chatId);
                    break;
                case "Год":
                case "год":
                    String date = sepAnswer[1];
                    image = Utils.getURL(NASA_URL + "&date=" + date);
                    sendMessage(image, chatId);
                    break;
                default:
                    sendMessage("Ошибка! Такой команды не существует", chatId);
            }

        }
    }

    //выносим в отдельный метод, метод - сообщения, которые отправляются боту, чтобы не дублировать строки в коде
//нам необходимы 2 вещи: текст и номер чата
    void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Сообщение не отправлено!");
        }
    }

    @Override
//возвращает имя бота
    public String getBotUsername() {
        // TODO
        return BOT_NAME;
    }

    @Override
//возвращает токен бота
    public String getBotToken() {
        // TODO
        return BOT_TOKEN;
    }
}