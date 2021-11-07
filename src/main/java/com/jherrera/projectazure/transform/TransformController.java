package com.jherrera.projectazure.transform;

import io.reactivex.rxjava3.core.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping
public class TransformController {

    @GetMapping("/transform")
    public String init(){

        //starWithItem();
        //sorted();
        scan();

        return "transform";
    }

    public void starWithItem(){
        Observable.just("Jean","Herrera","Llerena")
                .startWithItem("Datos Personales")
                .subscribe(System.out::println);

        /*Observable.just("Jean","Herrera","Llerena")
                .startWithArray("Datos Personales", "----------------")
                .subscribe(System.out::println);*/

        //Result Datos Personales/n, Jean/n, Herrera/n, Llrerena
    }

    public void sorted(){
        List list = Arrays.asList(6 ,3 ,4 ,4 ,5 ,2 , 44, 3, 4, 99, 1);

        //Orden natural ASC
        Observable.fromIterable(list)
            .sorted()
            .subscribe(System.out::print);

        //Result: 1234...

        System.out.println("");

        //Orden DESC
        Observable.fromIterable(list)
                .sorted(Comparator.reverseOrder())
                .subscribe(System.out::print);

        //Result: 987...

        System.out.println("");

        Observable.just("Alpha", "Beta", "Gamma")
                .sorted(Comparator.comparingInt(e -> e.length()))
                .subscribe(System.out::println);

        //Result: Beta, Alpha, Gamma
    }

    public void scan(){
        Observable.just(5,3,7)
                .scan( (acumulador, indice)-> {
                    System.out.println("Acumulador: "+acumulador);
                    System.out.println("Indice: "+indice);
                    return acumulador + indice;
                } )
                .subscribe(e -> System.out.println("Received: "+e));

        //Result: 5, 8, 15


        Observable.just("Hola", "Mundo", "RxJava")
                .scan((acumulado, valor) -> acumulado+" "+valor)
                .subscribe(s -> System.out.println("Received: " + s));

        //Result: Hola, Hola Mundo, Hola Mundo RxJava


        Observable.just("Alpha", "Beta", "Gamma")
                .scan(0, (total, next) -> {
                    System.out.println("Total: "+total);
                    System.out.println("Next: "+next);
                    return total + 1;
                })
                .subscribe(s -> System.out.println("Received: " + s));

        //Result: 0, 1, 2, 3
    }
}
