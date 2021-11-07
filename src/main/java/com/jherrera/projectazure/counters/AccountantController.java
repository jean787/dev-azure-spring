package com.jherrera.projectazure.counters;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AccountantController {

    @GetMapping("/accountant")
    public String init(){
        //count();
        reduce();
        return "accountant";
    }

    public void count(){
        Observable.just("Alpha", "Beta", "Gamma")
                .count()
                .subscribe(s -> System.out.println("Received: " + s));

        //Result: 3
    }

    //para acumalar el valor y realizar cualquier operacion sobre el
    //te devolvera el valor cuando llame al onComplete
    public void reduce(){
        Observable.just(5,3,7)
                .reduce((acumulado, indice)-> acumulado+indice)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: 15

        Observable.just("Jean","Piere","Herrera")
                .reduce("",(total, next) ->
                        total + (total.equals("") ? "" : "-") + next)
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: Jean-Piere-Herrera
    }

}
