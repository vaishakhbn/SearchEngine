package edu.uci.index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swanand on 2/10/2015.
 */
public class StemmedTerm {
    private String docId;
    private String stem;
    private List<Integer> positions = new ArrayList<Integer>();
    private int frequency;

    public StemmedTerm(String docId, String stem) {
        this.docId = docId;
        this.stem = stem;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public String getDocId() {
        return docId;
    }

    public String getStem() {
        return stem;
    }


    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StemmedTerm that = (StemmedTerm) o;

        if (stem != null ? !stem.equals(that.stem) : that.stem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return stem != null ? stem.hashCode() : 0;
    }
}