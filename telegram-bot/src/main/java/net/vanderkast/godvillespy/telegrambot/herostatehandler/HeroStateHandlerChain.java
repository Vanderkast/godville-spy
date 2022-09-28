package net.vanderkast.godvillespy.telegrambot.herostatehandler;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.telegrambot.notifier.Notifier;

import java.util.List;
import java.util.function.Consumer;

public class HeroStateHandlerChain implements Consumer<HeroState> {
    private final Notifier notifier;
    private final List<HeroStateHandler> chain;

    public HeroStateHandlerChain(Notifier notifier, List<HeroStateHandler> chain) {
        this.notifier = notifier;
        this.chain = chain;
    }

    @Override
    public void accept(HeroState state) {
        chain.forEach(handler -> handler.map(state).ifPresent(notifier::handle));
    }
}
