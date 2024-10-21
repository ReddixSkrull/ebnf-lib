package de.doering.lib;

import java.util.Arrays;

public class LexingDTO {

    private String[] split;
    private int index;

    public void increment() {
        increment(1);
    }
    public void increment(int increment) {
        index+= increment;
    }

    public String getSymbolAndIncrement() {
        String symbol = getSymbol();
        increment();
        return symbol;
    }

    public String getSymbolAndIncrement(int increment) {
        increment(increment);
        return getSymbol();
    }

    public String getSymbol(){
        return getSymbolAtIndex(index);
    }

    public String getSymbolAtIndex(int index) {
        return split[index];
    }

    public String[] getSplit() {
        return split;
    }

    public void setSplit(String[] split) {
        this.split = split;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LexingDTO() {
    }

    public LexingDTO(String[] split, int index) {
        this.split = split;
        this.index = index;
    }

    @Override
    public String toString() {
        return STR."LexingDTO{split=\{Arrays.toString(split)}, index=\{index}\{'}'}";
    }

}
