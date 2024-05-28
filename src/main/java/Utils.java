import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Utils {
    //возвращает нам ссылку на картинку и получает ссылку на сервер Nasa
    //мы хотим, чтобы по запросу присылались картинки на разные дни
    public static String getURL(String nasaURL) {
        // создание клиента, который будет отправлять эти запросы
        CloseableHttpClient client = HttpClients.createDefault(); //конструкция "HttpClients.createDefault();", создаёт нам http client с настройками по умолчанию, однако мы должны это сохранить через переменную
        //нам нужно создать объект, взять его из библиотеки "jackson-databind".....
        ObjectMapper mapper = new ObjectMapper();
        // создаём запрос
        HttpGet request = new HttpGet(nasaURL);
        //если возникнет ошибка, то пишем:Давай работать дальше, и программа продолжит работать
        //делаем исключение, попытайся...
        try {
            //посылаем запрос, выводим исключение и получаем ответ через переменную, "response" - здесь лежит ответ от сервера
            CloseableHttpResponse response = client.execute(request);
            //.....который будет преобразовывать ответ в формате json в формат данных java(наш класс "NasaAnswer")
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
            //возвращаем из нашего метода ответ, который хранится в переменной - "answer", .url - это ссылка для скачивания
            return answer.url;
            // а если возникнет какая-то ошибка, то мы её перехватим и указываем тип ошибки
        } catch (IOException e) {
            System.out.println("Произошла ошибка");
        } //в любом случае потом возвращаем пустую строчку
        return "";
    }
}
