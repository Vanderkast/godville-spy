package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TgResponse<T> {
    private final boolean ok;
    private final T result;

    public TgResponse(@JsonProperty("ok") boolean ok,
                      @JsonProperty("result") T result) {
        this.ok = ok;
        this.result = result;
    }
}
