package net.vanderkast.tgapi;

import net.vanderkast.godvillespy.core.Worker;
import net.vanderkast.tgapi.model.Update;

import java.util.function.Consumer;

public interface UpdatesLoop extends Worker {
    void addListener(Consumer<Update> onUpdate);
}
