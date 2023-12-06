package sia.tacocloud.web.api;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sia.tacocloud.Taco;
import sia.tacocloud.data.TacoRepository;

import java.net.URI;

@Configuration
public class RouterFunctionConfig {
    private final TacoRepository tacoRepository;
    @Autowired
    public RouterFunctionConfig(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @Bean
    public RouterFunction<?> routerFunction(){
        return route(GET("/api/tacos")
                .and(queryParam("recent",t -> t != null)),
                this::recents)
                .andRoute(POST("/api/tacos"), this::postTaco);
    }
    public Mono<ServerResponse> recents(ServerRequest request){
        return ServerResponse.ok().body(tacoRepository.findAll().take(12), Taco.class);
    }
    public Mono<ServerResponse> postTaco(ServerRequest request){
        return request.bodyToMono(Taco.class)
                .flatMap(taco -> tacoRepository.save(taco))
                .flatMap(savedTaco -> {
                    return ServerResponse.created(
                            URI.create("http://localhost:8080/api/tacos" +
                                    savedTaco.getId()))
                            .body(savedTaco, Taco.class);
                });
    }

}
