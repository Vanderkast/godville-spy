package net.vanderkast.godvillespy.telegrambot;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.vanderkast.godvillespy.core.HeroState;

import java.util.Optional;

public class HeroStateResponse implements HeroState {
    private final String name;
    private final Integer health;
    private final int maxHealth;

    public HeroStateResponse(@JsonProperty("name") String name,
                             @JsonProperty("health") Integer health,
                             @JsonProperty("max_health") int maxHealth) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Integer> getHealth() {
        return Optional.ofNullable(health);
    }

    @Override
    public Integer getMaxHealth() {
        return maxHealth;
    }

    @Override
    public String toString() {
        return name + "\t" + (health == null ? "???" : health) + "/" + maxHealth;
    }
}
