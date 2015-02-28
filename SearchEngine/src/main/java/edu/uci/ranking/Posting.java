package edu.uci.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"positions",
"docId",
"tf",
"_TF_IDF",
"idf"
})
public class Posting {

@JsonProperty("positions")
private List<Long> positions = new ArrayList<Long>();
@JsonProperty("docId")
private String docId;
@JsonProperty("tf")
private double tf;
@JsonProperty("_TF_IDF")
private double TFIDF;
@JsonProperty("idf")
private double idf;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The positions
*/
@JsonProperty("positions")
public List<Long> getPositions() {
return positions;
}

/**
* 
* @param positions
* The positions
*/
@JsonProperty("positions")
public void setPositions(List<Long> positions) {
this.positions = positions;
}

public Posting withPositions(List<Long> positions) {
this.positions = positions;
return this;
}

/**
* 
* @return
* The docId
*/
@JsonProperty("docId")
public String getDocId() {
return docId;
}

/**
* 
* @param docId
* The docId
*/
@JsonProperty("docId")
public void setDocId(String docId) {
this.docId = docId;
}

public Posting withDocId(String docId) {
this.docId = docId;
return this;
}

/**
* 
* @return
* The tf
*/
@JsonProperty("tf")
public double getTf() {
return tf;
}

/**
* 
* @param tf
* The tf
*/
@JsonProperty("tf")
public void setTf(double tf) {
this.tf = tf;
}

public Posting withTf(double tf) {
this.tf = tf;
return this;
}

/**
* 
* @return
* The TFIDF
*/
@JsonProperty("_TF_IDF")
public double getTFIDF() {
return TFIDF;
}

/**
* 
* @param TFIDF
* The _TF_IDF
*/
@JsonProperty("_TF_IDF")
public void setTFIDF(double TFIDF) {
this.TFIDF = TFIDF;
}

public Posting withTFIDF(double TFIDF) {
this.TFIDF = TFIDF;
return this;
}

/**
* 
* @return
* The idf
*/
@JsonProperty("idf")
public double getIdf() {
return idf;
}

/**
* 
* @param idf
* The idf
*/
@JsonProperty("idf")
public void setIdf(double idf) {
this.idf = idf;
}

public Posting withIdf(double idf) {
this.idf = idf;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

public Posting withAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
return this;
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(positions).append(docId).append(tf).append(TFIDF).append(idf).append(additionalProperties).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Posting) == false) {
return false;
}
Posting rhs = ((Posting) other);
return new EqualsBuilder().append(positions, rhs.positions).append(docId, rhs.docId).append(tf, rhs.tf).append(TFIDF, rhs.TFIDF).append(idf, rhs.idf).append(additionalProperties, rhs.additionalProperties).isEquals();
}

}