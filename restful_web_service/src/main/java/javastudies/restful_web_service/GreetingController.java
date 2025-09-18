package javastudies.restful_web_service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") //Faz com que qualquer HTTP GET para /greeting chame a função returnGreeting()
    public Greeting returnGreeting(@RequestParam(defaultValue = "World") String name){ //@RequestParams amarra o valor da variavel name na Request à variavel name da função. Caso a request não tenha variavel name, o nome padrão é World.
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
