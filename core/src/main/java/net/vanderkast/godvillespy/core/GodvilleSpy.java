package net.vanderkast.godvillespy.core;

import java.util.function.Consumer;

public interface GodvilleSpy extends Joinable {
    /**
     * Starts spy on hero.
     * @param onStateUpdated hero state updates consumer.
     * @return true is spying started successfully, false otherwise.
     */
    boolean spy(Consumer<HeroState> onStateUpdated);

    /**
     *
     * @throws InterruptedException
     */
    void stop() throws InterruptedException;
}
