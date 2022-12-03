package it.cgmconsulting.gateway.controller;


import it.cgmconsulting.gateway.costants.Constants;
import it.cgmconsulting.gateway.payload.AddUserRequest;
import it.cgmconsulting.gateway.payload.AdminResponse;
import it.cgmconsulting.gateway.payload.Credentials;
import it.cgmconsulting.gateway.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class AuthController {

    @Autowired  WebClient webClient;

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody RegisterRequest registerRequest){

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", Constants.ADMIN_USERNAME);
        map.add("password", Constants.ADMIN_PASSWORD);
        map.add("grant_type","password");
        map.add("client_id", "admin-cli");

        AtomicInteger status = new AtomicInteger(300);

        return webClient.post().uri(Constants.AUTH_URL)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(AdminResponse.class)
                .flatMap(token -> {
                    return webClient.post().uri(Constants.ADD_USER_URL)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccess_token())
                            .bodyValue(new AddUserRequest(registerRequest.getUsername(), new Credentials(registerRequest.getPassword())))
                            .retrieve()
                            .bodyToMono(String.class)                         
                            .onErrorResume(err -> {
                                status.set(400);
                                return Mono.just(err.getMessage());
                            });
                })
            .map(r -> ResponseEntity.status(status.get()).body(r));

    }
}

