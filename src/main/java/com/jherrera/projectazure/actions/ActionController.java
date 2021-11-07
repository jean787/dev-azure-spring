package com.jherrera.projectazure.actions;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Operadores que nos serviran para depurar
@RestController
@RequestMapping
public class ActionController {

    @GetMapping("/action")
    public String init(){

        //doOnNext();
        //doAfterNext();
        //doOnComplete();
        doOnError();

        return "Action Operations";
    }

    public void doOnNext(){
        Observable.just("Alpha","Beta","Gamma")
                .doOnNext(e -> System.out.println("Procesing: "+e))
                .map(String::length)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: Alpha, 5, Beta, 4, Gamma, 5
    }

    public void doAfterNext(){
        Observable.just("Alpha","Beta","Gamma")
                .doAfterNext(e -> System.out.println("Procesing: "+e))
                .map(String::length)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: 5, Alpha, 4, Beta, 5, Gamma
    }

    public void doOnComplete(){
        Observable.just("Alpha","Beta","Gamma")
                .doOnComplete(()-> System.out.println("Termino!"))
                .map(String::length)
                .subscribe(e -> System.out.println("Received: "+e));
    }

    public void doOnError(){
        Observable.just(5,2,4,0,3)
                .doOnError(e -> System.out.println("Fallo en la fuente"))
                .map(e -> 10 / e)
                .doOnError(e -> System.out.println("Fallo en la division!"))
                .subscribe(e -> System.out.println("Received: "+e),
                        e -> System.out.println("Received Error: "+e));
    }
}
