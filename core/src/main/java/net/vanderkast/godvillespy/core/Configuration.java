package net.vanderkast.godvillespy.core;

import java.util.Optional;

public interface Configuration {
    String getGodName();

    Optional<String> getAccessToken();
}
