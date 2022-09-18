package net.vanderkast.tgapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SendMessage implements Method {
    private final long chatId;
    private final String text;
}
