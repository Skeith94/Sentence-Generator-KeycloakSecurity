package it.cgmconsulting.gateway.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class Credentials {
    private String type = "password";
    private  String value;
    private  boolean temporary = false;
    public Credentials(String value) {
        this.value = value;
    }
}
