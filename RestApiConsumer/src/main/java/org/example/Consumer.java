package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();

//https://reqres.in/ сайт для теста фронтенд приложений
//пока просто получим строку JSON
System.out.println(restTemplate.getForObject("https://reqres.in/api/users/2", String.class));

//        отправим пост запрос на создание нового юзера
        Map<String,String>jsonToSend=new HashMap<>();//мапа очень напоминает жсон
        jsonToSend.put("name","Test name");
        jsonToSend.put("job","Test job");

        HttpEntity<Map<String,String>> request=new HttpEntity<>(jsonToSend);//этот обьект полетит пост шттп запросом на сервер

        String response=restTemplate.postForObject("https://reqres.in/api/users",request, String.class);//обратно вернется жсон с ответом
        System.out.println(response);

//        Распарсим вопрос и ответ json
        JsonToSend jsonToSend2=new JsonToSend();
        jsonToSend2.setName("Test name");
        jsonToSend2.setJob("Test job");

        HttpEntity<JsonToSend> request2=new HttpEntity<>(jsonToSend2);

        Response response2=restTemplate.postForObject("https://reqres.in/api/users",request, Response.class);
        System.out.println("===Юзер создался===\n"+
                "Его имя: "+response2.getName()+"\n"+
                "Его работа: "+response2.getJob()+"\n"+
                "Он создался с id: "+response2.getId()+"\n"+
                "Время создания: "+response2.getCreatedAt());
    }
}
class JsonToSend{
    private String name;
    private  String job;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getJob() {return job;}
    public void setJob(String job) {this.job = job;}
}
class Response{
    private String name;
    private String job;
    private  String id;
    private  String createdAt;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getJob() {return job;}
    public void setJob(String job) {this.job = job;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
}