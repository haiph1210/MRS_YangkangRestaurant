package com.haiph.common.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class SendMail {
    private String emailRevice;
    private String subject;
    private String message;
}
