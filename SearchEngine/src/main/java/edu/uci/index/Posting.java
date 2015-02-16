package edu.uci.index;

import java.io.Serializable;
import java.util.List;

/**
 * Created by swanand on 2/14/2015.
 */
public class Posting implements Serializable {
    private final String docId;
    private final List<Integer> positions;
    private Float tf = new Float(0.0);

    public Posting(String docId, List<Integer> positions) {
        this.docId = docId;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posting posting = (Posting) o;

        if (!docId.equals(posting.docId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return docId.hashCode();
    }

    public Float getTf() {
        return tf;

    }

    public void setTf(Float tf) {
        this.tf = tf;
    }
}
