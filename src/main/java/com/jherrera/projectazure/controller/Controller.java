package com.jherrera.projectazure.controller;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("/")
    public String hello(){
        return "Hello world";
    }

    @GetMapping("/data")
    public String data(){
        return "Esto es el endpoint de data";
    }

    @GetMapping("/create")
    public Observable<Integer> create(){
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("Alpha");
            emitter.onNext("Beta");
            emitter.onNext("Gamma");
            emitter.onComplete();
        });
        return source
                .map(String::length)
                .filter(e -> e >= 5);
    }

    @GetMapping("/just")
    public Observable<Integer> just(){
        Observable<String> source = Observable.just("Alpha","Beta","Gamma");
        return source
                .map(String::length)
                .filter(e -> e >= 5);
    }

    @GetMapping("/list")
    public Observable<Integer> list(){

        //takeWhile();
        //skipWhile();
        //deafultIfEmpty();
        //switchIfEmpty();
        //take();
        //skip();
        //distinc();
        elementAt();

        List<String> items = Arrays.asList("Alpha","Beta","Gamma");
        Observable<String> source = Observable.fromIterable(items);
        return source
                .map(String::length)
                .filter(e -> e >= 5);
    }


    public void takeWhile(){

        // al primer FALSE cierra todo el proceso
        Observable.range(1, 100)
                .takeWhile(i -> i == 1 || i == 10)
                .subscribe(i -> System.out.println("RECEIVED: " + i));

        //Result: 1
    }

    public void skipWhile(){

        // al primer FALSE comienza toda las emisiones
        Observable.just("AR","PE","CH","BV","BR")
                .skipWhile(i -> i.equals("AR") || i.equals("PE") || i.equals("BR"))
                .subscribe(i -> System.out.println("RECEIVED: " + i));

        //Result: CH, BV, BR
    }

    public void deafultIfEmpty(){
        Observable.just("Alpha", "Beta")
                .filter(e -> e.startsWith("Z"))
                .defaultIfEmpty("none")
                .subscribe(System.out::println);

        //Result: none

        Observable.range(1, 100)
                .filter(e -> e == 101)
                .defaultIfEmpty(0)
                .subscribe(System.out::println);

        //Result: 0
    }

    public void switchIfEmpty(){
        Observable.just("Alpha", "Beta")
                .filter(e -> e.startsWith("Z"))
                .switchIfEmpty(Observable.just("Data","No","Encontrada"))
                .subscribe(i -> System.out.println(i),
                        e -> System.out.println(e.getMessage()));
        //Result: Data, Not, Encontrada
    }

    public void take(){
        Observable.just("Alpha", "Beta", "Gamma")
                .take(2)
                .subscribe(e -> System.out.println("RECEIVED: "+e));

        //Result: Alpha, Beta

        /*DateTimeFormatter f = DateTimeFormatter.ofPattern("ss:SSS");
        System.out.println(LocalDateTime.now().format(f));
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .take(2, TimeUnit.SECONDS)
                .subscribe(i -> System.out.println(LocalDateTime.now()
                        .format(f) + " RECEIVED: " + i));

        sleep(5000);*/
    }

    public void skip(){
        Observable.just("Alpha", "Beta", "Gamma")
                .skip(2)
                .subscribe(e -> System.out.println("RECEIVED: "+e));

        //Result: Gamma
    }

    public void distinc(){
        Observable.just("Alpha", "Beta", "Alpha")
                .distinct()
                .subscribe(i -> System.out.println("RECEIVED: " + i));

        Observable.just("Alpha", "Beta", "Gamma")
                .distinct(e -> e.length()) // como ALPHA tiene asignado el 5 no lo considra al GAMMA porque ya existe el 5
                .subscribe(i -> System.out.println("RECEIVED: " + i));

        //Result: Alpha, Beta
    }

    public void elementAt(){
        Observable.just("Alpha","Beta","Gamma","Eta","Zeta")
                .elementAt(3)
                .subscribe(e -> System.out.println("Received: "+ e));
        //Result: Eta
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Single == 1 Element
    //Maybe == 0 o 1 Element

    //Oberservable.Defert() = crea un estado separado para cuando modifique el valor del obervable
}
