package com.haiph.emailservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMail {
    private String emailRevice; // người nhận
    private String subject; // chủ đề
    private String message;  // message
    private String attachment; // tập tin đính kèm
}