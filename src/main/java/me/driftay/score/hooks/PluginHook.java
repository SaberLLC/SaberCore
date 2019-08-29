package me.driftay.score.hooks;

public interface PluginHook<T> {

    T setup();

    String getName();


}
