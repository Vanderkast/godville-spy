package net.vanderkast.godvillespy.core;

import java.util.Optional;

public class HeroStateDto implements HeroState {
    private final String name;
    private final int maxHealth;
    private final Integer health;

    public HeroStateDto(String name, int maxHealth, Integer health) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = health;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getMaxHealth() {
        return maxHealth;
    }

    @Override
    public Optional<Integer> getHealth() {
        return Optional.ofNullable(health);
    }
}
