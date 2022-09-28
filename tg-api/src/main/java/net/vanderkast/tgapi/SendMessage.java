package net.vanderkast.tgapi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class SendMessage implements Method {
    private final long chatId;
    private final String text;
}
