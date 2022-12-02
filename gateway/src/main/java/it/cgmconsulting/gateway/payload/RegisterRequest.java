package it.cgmconsulting.gateway.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;

}
