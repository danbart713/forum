package com.dvb.forum.dto.emailsending;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendingKafkaEvent {

    private String email;
    private String subject;
    private String body;

}
