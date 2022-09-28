package net.vanderkast.godvillespy.core;

public interface Worker {
    void join() throws InterruptedException;

    void stop() throws InterruptedException;
}
