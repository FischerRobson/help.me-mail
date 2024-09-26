package com.helpme.mail_ms.mail_ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Email {
    private String receiver;
    private String title;
    private String body;
}
