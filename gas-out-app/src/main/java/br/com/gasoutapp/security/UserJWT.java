package br.com.gasoutapp.security;

import lombok.Data;

@Data
public class UserJWT {

    private Long id;
    private String login;

    public UserJWT(Long id, String login) {
        this.id = id;
        this.login = login;
    }
}