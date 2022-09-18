package net.vanderkast.godvillespy.core;

import java.util.Objects;
import java.util.Optional;

public class ConfigurationDto implements Configuration {
    private final String godName;
    private final String accessToken;

    public ConfigurationDto(String godName) {
        this.godName = godName;
        this.accessToken = null;
    }

    public ConfigurationDto(String godName, String accessToken) {
        this.godName = godName;
        this.accessToken = accessToken;
    }

    @Override
    public String getGodName() {
        return godName;
    }

    @Override
    public Optional<String> getAccessToken() {
        return Optional.ofNullable(accessToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationDto that = (ConfigurationDto) o;
        return godName.equals(that.godName) && Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(godName);
    }
}
