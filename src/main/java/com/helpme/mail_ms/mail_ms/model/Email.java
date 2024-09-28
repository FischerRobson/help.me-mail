package com.helpme.mail_ms.mail_ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Email {
    private String receiver;
    Map<String, Object> variables;
}
