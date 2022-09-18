package net.vanderkast.tgapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetUpdates implements Method {
    private final Long offset;
    private final String[] allowedUpdates;

    public GetUpdates(@JsonProperty("offset") Long offset,
                      @JsonProperty("allowed_updates") String[] allowedUpdates) {
        this.offset = offset;
        this.allowedUpdates = allowedUpdates;
    }

    public static GetUpdates messagesOnly(Long offset) {
        return new GetUpdates(offset, new String[]{Type.MESSAGE.toString()});
    }

    public enum Type {
        MESSAGE;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
