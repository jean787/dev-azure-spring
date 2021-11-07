package com.jherrera.projectazure.collectors;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping
public class CollectController {

    @GetMapping("/collect")
    public String init(){
        //toList();
        //toSortedList();
        //toMap();
        collect();

        return "Operator collections";
    }

    public void toList(){
        Observable.just("Alpha", "Beta", "Gamma")
                .toList()
                .subscribe(s -> System.out.println("Received: " + s));
        //Result: [Alpha, Beta, Gamma]

        Observable.range(1, 1000)
                .toList(1000)
                .subscribe(s -> System.out.println("Received: " + s));
        //Result: [1, 2, 3, ..., 1000]

        //para pasar una nueva implentacion de otro list
        Observable.just("Beta", "Gamma", "Alpha")
                .toList(ArrayList::new)
                .subscribe(s -> System.out.println("Received: " + s));
        //Result: [Alpha, Beta, Gamma]
    }


    public void toSortedList(){
        List list = Arrays.asList(6 ,3 ,4 ,4 ,5 ,2 , 44, 3, 4, 99, 1);

        Observable.fromIterable(list)
                //tiene const sobregardo para diferente ordenes
                .toSortedList()
                .subscribe(System.out::print);

        //Result: [1, 2, 3, 3, 4, 4, 4, 5, 6, 44, 99]
    }

    public void toMap(){
        Observable.just("Alpha", "Beta","Gamma")
                .toMap(e -> e.charAt(0))
                .subscribe(e -> System.out.println("Received: "+e));
        //Result: {A=Alpha, B=Beta, G=Gamma}

        Observable.just("Alpha", "Beta","Gamma")
                .toMap( e -> e.charAt(0),
                        e -> e.length())
                .subscribe(e -> System.out.println("Received: "+e));
        //Result: {A=5, B=4, G=5}

        Observable.just("Alpha", "Beta","Gamma")
                .toMap( e -> e.charAt(0),
                        e -> e.toUpperCase(),
                        //hasmap por default, lo cambio por ConcurrentHashMap
                        ConcurrentHashMap::new)
                .subscribe(e -> System.out.println("Received: "+e));
        //Result: {A=ALPHA, B=BETA, G=GAMMA}

        //lo reempla por el ultimo valor
        Observable.just("Alpha", "Beta","Gamma")
                .toMap(String::length)
                .subscribe(e -> System.out.println("Received: "+e));
        //Result: {4=Beta, 5=Gamma}


        //Agrega un array a cada elemento por el KEY
        Observable.just("Alpha", "Beta","Gamma")
                .toMultimap(String::length)
                .subscribe(e -> System.out.println("Received: "+e));
        //Result: {4=[Beta], 5=[Alpha, Gamma]}

    }

    public void collect(){
        Observable.just("Alpha", "Beta", "Gamma", "Beta")
                .collect(HashSet<String>::new, (e, i) -> e.add(i))
                .subscribe(s -> System.out.println("Received: " + s));
        //Result: [Gamma, Alpha, Beta]
    }
}
