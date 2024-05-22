import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // адрес, по которому посылаем запрос
        String url =
                "https://api.nasa.gov/planetary/apod?api_key" +
                        "=3ubfybCcsCxscUgh3MIJ0FPMzrgdiPcB6qGKosjZ";
        // создание клиента, который будет отправлять эти запросы
        CloseableHttpClient client = HttpClients.createDefault(); //конструкция "HttpClients.createDefault();", создаёт нам http client с настройками по умолчанию, однако мы должны это сохранить через переменную
        // создаём запрос
        HttpGet request = new HttpGet(url);
        //посылаем запрос, выводим исключение и получаем ответ через переменную, "response" - здесь лежит ответ от сервера
        CloseableHttpResponse response = client.execute(request);
        // читаем ответ через сканер и передаем ответ в скобках
//        Scanner sc = new Scanner(response.getEntity().getContent());
//        System.out.println(sc.nextLine());
        //после запуска копируем вывод на экран с { до } включительно, создаём новый класс "NasaAnswer", прописываем все переменные из вывода и делаем конструктор, в pom.xml подключаем библиотеку json - Jackson Databind, копируя и вставляя код, далее синхронизация, json нужен для чтения ответа от nasa, т.к. java распознавать этот формат не может, поэтому подключаем библиотеку
        //нам нужно создать объект, взять его из библиотеки "jackson-databind", который будет преобразовывать ответ в формате json в формат данных java(наш класс "NasaAnswer")
        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
        //сохраняем нашу ссылку отдельно в переменную
        String urlImage = answer.url;
        //нам нужно взять имя файла из этой же ссылки
        // с помощью массива, берём ссылку, разбиваем её по частям до знака слеш(/), и мы берём последний элемент массива- название файла
        String [] nameAnswer = urlImage.split("/");
        String fileName = nameAnswer[nameAnswer.length - 1];
        //создаём запрос за этой картинкой и посылаем его и теперь картинка хранится в переменной ""
        HttpGet requestImg = new HttpGet(urlImage);
        CloseableHttpResponse image = client.execute(requestImg);
        //сохраняем нашу картинку на жёсткий диск
        FileOutputStream fos = new FileOutputStream(fileName);
        image.getEntity().writeTo(fos);
        
    }
}
