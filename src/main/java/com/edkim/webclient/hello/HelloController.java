package com.edkim.webclient.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RestController
public class HelloController {

    @Autowired
    private WebClient webClient;
    int requestCount = 100;
//    int requestCount = 5;
    @GetMapping("test/{num}")
    public void test(@PathVariable("num") int num) {
        requestCount = num;
        List<String> urls = getUrls();
        vs(urls);
    }

    public void vs(List<String> urls) {
        System.out.println("request test");
        Random random = new Random();

        long webClientStartTime = System.currentTimeMillis();
//        processRequests(urls);
        long webClientEndTime = System.currentTimeMillis();
        long webClientDuration = webClientEndTime - webClientStartTime;
        System.out.println("WebClient Duration: " + webClientDuration + "ms");

        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();
        long restTemplateStartTime = System.currentTimeMillis();
        for (int i = 0; i < requestCount; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity("https://randomuser.me/api/", String.class);
            response.getBody();
        }
        long restTemplateEndTime = System.currentTimeMillis();
        long restTemplateDuration = restTemplateEndTime - restTemplateStartTime;
        System.out.println("RestTemplate Duration: " + restTemplateDuration + "ms");
    }

    public void processRequests(List<String> dataList) {
        int numRequests = dataList.size();
        CountDownLatch latch = new CountDownLatch(numRequests);

        Flux.fromIterable(dataList)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(data -> makeRequest(data)
                        .doOnSuccess(response -> updateDatabase(data, response))
                        .doFinally(signal -> latch.countDown()))
                .sequential()
                .subscribe();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Mono<String> makeRequest(String data) {
        return webClient.get()
                .uri(data)
                .retrieve()
                .toEntity(String.class)
                .map(responseEntity -> responseEntity.getStatusCode().toString());
    }

    private void updateDatabase(String data, String response) {
        // Perform database update based on the data and response
        System.out.println("Updating database with data: " + data + ", response: " + response);
    }

    private List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://randomuser.me/api/?nat=us");
        urls.add("https://api.chucknorris.io/jokes/random");

        List<String> url1000 = new ArrayList<>();
        Random random = new Random();

        for (int i = 0 ; i < requestCount ; i++) {
            int r = random.nextInt(1);
            url1000.add(urls.get(r));
        }
        return url1000;
    }
}
