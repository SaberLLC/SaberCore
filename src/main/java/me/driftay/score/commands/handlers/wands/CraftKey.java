package me.driftay.score.commands.handlers.wands;

import org.bukkit.Material;

public class CraftKey {

    private Material initial;
    private int amountToResult;
    private Material result;


    public CraftKey(Material initial, int amountToResult, Material result) {
        this.initial = initial;
        this.amountToResult = amountToResult;
        this.result = result;
    }

    public int getAmountToResult() {
        return amountToResult;
    }

    public Material getInitial() {
        return initial;
    }

    public Material getResult() {
        return result;
    }
}
