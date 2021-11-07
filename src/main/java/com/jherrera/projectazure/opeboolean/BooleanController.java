package com.jherrera.projectazure.opeboolean;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BooleanController {

    @GetMapping("/boolean")
    public String init(){

        //all();
        //any();
        //isEmpty();
        //contains();
        sequenceEqual();

        return "operators boolean";
    }


    public void all(){

        //al encontrar un false, inmediatamente llama al onComplete
        Observable.just(3, 5, 9, 10, 15, 2, 7, 29)
                .all(i -> i < 20)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: false
    }

    public void any(){
        Observable.just(3, 5, 9, 10, 15, 2, 7, 29)
                .any(i -> i == 7)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: true
    }

    public void isEmpty(){
        Observable.just("One", "Two", "Three")
                .filter(s -> s.contains("z"))
                .isEmpty()
                .subscribe(s -> System.out.println("Received1: " + s));
        //Result: true

        Observable.just("One", "Twoz", "Three")
                .filter(s -> s.contains("z"))
                .isEmpty()
                .subscribe(s -> System.out.println("Received2: " + s));
        //Result: false
    }

    public void contains(){
        Observable.just("BR", "AR", "PE", "CH")
                .contains("PE")
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: true
    }

    //verifica que 2 obs tengan los mismos datos en la misma secuencia de flujo
    public void sequenceEqual(){
        Observable<String> obs1 = Observable.just("One","Two","Three");
        Observable<String> obs2 = Observable.just("One","Two","Three");
        Observable<String> obs3 = Observable.just("Two","One","Three");
        Observable<String> obs4 = Observable.just("One","Two");

        Observable.sequenceEqual(obs1, obs2)
                .subscribe(s -> System.out.println("Received: " + s));
        //true

        Observable.sequenceEqual(obs1, obs3)
                .subscribe(s -> System.out.println("Received: " + s));
        //false

        Observable.sequenceEqual(obs1, obs4)
                .subscribe(s -> System.out.println("Received: " + s));
        //false
    }
}
