import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        //пишем тг бота, состоит из двух вещей:
        // 1 - административная, мы должны договориться с тг, что хотим создать такого бота, идём прямо в тг, botfather - разработка от самой команды тг, он позволяет регистрировать новых ботов.
        //start->newbot->название->2название_bot->
        //Чтобы мы смогли управлять ботом, нам нужны - username и token. Далее устанавливаем библиотеку на pom.xml для работы с тг ботом, через github находим нужную версию TelegramBots->TelegramBots.wiki/ -> Getting-Started.md и копируем With Maven: <dependency>
        //Пишем тг бота в новом классе "NasaTgBot"
        //Создаём нашего бота и добавляем исключение к main
        new NasaTgBot("eIkuzmina_pic_day_bot",
                "7086741548:AAHcGvaGFHDbd7NVJ6SeLqu6flGXeixvsR8");

    }
}
// данного бота подключили к ВМ через яндекс cloud, создали ВМ и соединили через терминал для автономной работы нашего бота
// ссылка на бота - https://t.me/eIkuzmina_pic_day_bot