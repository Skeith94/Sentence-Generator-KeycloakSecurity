package it.cgmconsulting.gateway.controller;


import it.cgmconsulting.gateway.costants.Constants;
import it.cgmconsulting.gateway.payload.AddUserRequest;
import it.cgmconsulting.gateway.payload.AdminResponse;
import it.cgmconsulting.gateway.payload.Credentials;
import it.cgmconsulting.gateway.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    WebClient webClient;
    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> fallbackSubject(@RequestBody RegisterRequest registerRequest){


        String url = Constants.AUTH_URL;
        String addUserUrl=Constants.ADD_USER_URL;


        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", Constants.ADMIN_USERNAME);
        map.add("password", Constants.ADMIN_PASSWORD);
        map.add("grant_type","password");
        map.add("client_id", "admin-cli");


         Mono<String>result=
                webClient.post().uri(url)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(AdminResponse.class)
                .mapNotNull(response->"Bearer "+response.getAccess_token())
                .log();


      return   result.mapNotNull( tokenResult->
                   {
               return   webClient.post()
                               .uri(Constants.ADD_USER_URL)
                               .header(HttpHeaders.AUTHORIZATION, tokenResult)
                               .accept(MediaType.APPLICATION_JSON)
                               .bodyValue(new AddUserRequest(registerRequest.getUsername(), new Credentials(registerRequest.getPassword())))
                               .retrieve()
                               .bodyToMono(String.class)
                              .onErrorReturn("username e password already found")
                              ;


                       }).map(ResponseEntity::ok);


                   }



}

