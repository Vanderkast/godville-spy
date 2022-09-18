package net.vanderkast.tgapi;

import net.vanderkast.godvillespy.core.Joinable;
import net.vanderkast.tgapi.model.Update;

import java.util.function.Consumer;

public interface UpdatesLoop extends AutoCloseable, Joinable {
    void addListener(Consumer<Update> onUpdate);
}
