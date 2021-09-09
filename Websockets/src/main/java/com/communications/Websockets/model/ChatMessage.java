package com.communications.Websockets.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage
{
    @Getter  @Setter
    private  MessageType type;

    @Getter @Setter
    private  String content;

    @Getter @Setter
    private  String sender;

    @Getter @Setter
    private  String time;

}
