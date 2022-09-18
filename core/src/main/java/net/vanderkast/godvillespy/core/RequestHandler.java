package net.vanderkast.godvillespy.core;

import java.util.function.Consumer;

public interface RequestHandler {
    void call(Consumer<HeroState> state);
}
