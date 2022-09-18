package net.vanderkast.tgapi;

import net.vanderkast.tgapi.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class UpdatesLoopImpl implements UpdatesLoop {
    private static final long UPDATE_PERIOD = 1000;

    private final BotApi api;
    private final List<Consumer<Update>> listeners;

    private volatile Thread loop;

    public UpdatesLoopImpl(BotApi api) {
        this.api = api;
        listeners = new LinkedList<>();
    }

    @Override
    public void join() throws InterruptedException {
        loop.join();
    }

    @Override
    public void addListener(Consumer<Update> listener) {
        initWorker();
        listeners.add(listener);
    }

    @Override
    public void close() {
        loop.interrupt();
    }

    private void initWorker() {
        if (loop == null) {
            synchronized (this) {
                if (loop == null)
                    loop = new Thread(new Loop(UPDATE_PERIOD, new UpdatesLoopWorker(api, this::notifyListeners)));
                loop.start();
            }
        }
    }

    private void notifyListeners(Update update) {
        listeners.forEach(listener -> listener.accept(update));
    }

    static class Loop implements Runnable {
        private static final Logger logger = LoggerFactory.getLogger(Loop.class);

        private final long periodMillis;
        private final Runnable worker;

        Loop(long periodMillis, Runnable worker) {
            this.periodMillis = periodMillis;
            this.worker = worker;
        }

        @SuppressWarnings("BusyWait")
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    long nextRun = System.currentTimeMillis() + periodMillis;
                    worker.run();
                    if (System.currentTimeMillis() < nextRun) {
                        Thread.sleep(nextRun - System.currentTimeMillis());
                    }
                }
            } catch (InterruptedException e) {
                logger.info("Loop was interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    static class UpdatesLoopWorker implements Runnable {
        private static final Logger logger = LoggerFactory.getLogger(UpdatesLoopWorker.class);

        private final BotApi api;
        private final Consumer<Update> onUpdate;
        private volatile long lastHandled;

        UpdatesLoopWorker(BotApi api, Consumer<Update> onUpdate) {
            this.api = api;
            this.onUpdate = onUpdate;
        }

        /**
         * <a href="https://core.telegram.org/bots/api#getupdates">Telegram getUpdates doc</a>
         */
        @Override
        public void run() {
            var oUpdates = api.getUpdates(GetUpdates.messagesOnly(lastHandled));
            if (oUpdates.isEmpty()) {
                logger.error("Getting updated is failed or received unsupported update type.");
                return;
            }
            var updates = oUpdates.get();
            if (updates.isEmpty()) {
                logger.debug("Got no updates.");
                return;
            }
            lastHandled = updates.get(updates.size() - 1).getUpdateId() + 1;
            updates.forEach(onUpdate);
        }
    }
}
