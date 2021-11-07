package com.jherrera.projectazure.errors;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ErrorController {

    @GetMapping("/error")
    public String init(){
        //onErrorReturnItem();
        //onErrorReturn();
        //notBlockFlow();
        //onErrorResumeWith();
        retry();
        return "Operator error";
    }

    public void onErrorReturnItem(){
        Observable.just(5, 2, 4, 0, 3)
                .map(i -> 10 / i)
                .onErrorReturnItem(-1)
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println("RECEIVED ERROR: " + e));
    }

    public void onErrorReturn(){
        Observable.just(5, 2, 4, 0, 3)
                .map(i -> 10 / i)
                .onErrorReturn(e ->
                        e instanceof ArithmeticException ? -2 : 0)
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println("RECEIVED ERROR: " + e));
    }

    //Seguir operando hasta el final sin bloquear
    public void notBlockFlow(){
        Observable.just(5, 2, 4, 0, 3)
                .map(i -> {
                    try {
                        return 10 / i;
                    } catch (ArithmeticException e) {
                        return -1;
                    }
                })
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println("RECEIVED ERROR: " + e));
    }

    public void onErrorResumeWith(){
        Observable.just(5, 2, 4, 0, 3)
                .map(e -> 10 / e)
                .onErrorResumeWith(Observable.just(-1).repeat(3))
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println("RECEIVED ERROR: " + e));
    }

    //volvera ha realizar el mismo flujo 2 veces
    public void retry(){
        Observable.just(5, 2, 4, 0, 3)
                .map(e -> 10 / e)
                .retry(2)
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println("RECEIVED ERROR: " + e));
    }

}
