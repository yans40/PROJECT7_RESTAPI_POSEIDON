package com.nnk.springboot.service;

import org.springframework.context.annotation.Bean;

public class OAuth2AuthorizedClientService {

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(){
        return new OAuth2AuthorizedClientService();
    }
}
