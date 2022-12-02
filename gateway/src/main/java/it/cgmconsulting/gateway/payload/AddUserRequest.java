package it.cgmconsulting.gateway.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AddUserRequest {
    private String username;
    private List<Credentials> credentials;
    private boolean enabled=true;

    public AddUserRequest(String username, Credentials credentials) {
        this.username = username;
      this.credentials =List.of(credentials);
    }
}

/*

{
        "username": "provasperanza",
        "credentials":[{
        "type":"password",
        "value":"1",
        "temporary":false
        }],
        "enabled":"true",
        "groups" : ["User"]
        }

 */