package edu.uci.index;

import com.mongodb.BasicDBObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by swanand on 2/14/2015.

 */
public class Posting implements Serializable  {
    private String docId;
    private List<Integer> positions;
    private Float tf = new Float(0.0);
    private Float idf = new Float(0.0);
    public Posting(String docId, List<Integer> positions) {
        this.docId = docId;
        this.positions = positions;
    }

    public void setDocId(String docId) {
		this.docId = docId;
	}

	public void setPositions(List<Integer> positions) {
		this.positions = positions;
	}

	public Posting() {

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

    public void setIdf(Float idf) {
        this.idf = idf;
    }

    public double get_TF_IDF(){
        if(idf != 0.0)
        return tf * Math.log(idf.doubleValue());
        else return 0;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public String getDocId() {
        return docId;
    }

    public Float getIdf() {
        return idf;
    }
    @Override
    public String toString()
    {
		return docId;
    	
    }
}
