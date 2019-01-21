package com.freestyle.wenda.service;

import org.springframework.stereotype.Service;

@Service
public class WendaService {

    public String getMessage(int code) {
        return "hello service " + code;
    }
}
