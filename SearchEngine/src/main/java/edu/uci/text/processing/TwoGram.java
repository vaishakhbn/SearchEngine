package edu.uci.text.processing;

/**
 * Created by swanand on 2/5/2015.
 */
public class TwoGram {
    private String firstGram;
    private String secondGram;

    public TwoGram(String firstGram, String secondGram) {
        this.firstGram = firstGram;
        this.secondGram = secondGram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoGram)) return false;

        TwoGram twoGram = (TwoGram) o;

        if (!firstGram.equals(twoGram.firstGram)) return false;
        if (!secondGram.equals(twoGram.secondGram)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstGram.hashCode();
        result = 31 * result + secondGram.hashCode();
        return result;
    }

    public String getSecondGram() {
        return secondGram;
    }

    public String getFirstGram() {
        return firstGram;
    }

}
