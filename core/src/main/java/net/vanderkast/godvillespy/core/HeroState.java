package net.vanderkast.godvillespy.core;

import java.util.Optional;

public interface HeroState {
    String getName();

    Integer getMaxHealth();

    Optional<Integer> getHealth();
}
