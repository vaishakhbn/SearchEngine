package edu.uci.ranking;

/**
 * Created by swanand on 3/13/2015.
 */
import java.util.List;

/**
 * Calculates the NDCG score using Google as both the oracle AND the relevance score (first document is relevance 5, second is 4, etc.)
 */
public class NDCG {
    public double getNDCG(List<String> urls, List<String> oracleUrls, int r) {
        // get DCG of the target URLs
        double urlDCG = getDCG(urls, oracleUrls, r);

        // get DCG of perfect ranking
        double perfectDCG = getDCG(oracleUrls, oracleUrls, r);

        // normalize by dividing
        double normalized = urlDCG / perfectDCG;
        return normalized;
    }

    private double getDCG(List<String> urls, List<String> oracleUrls, int p) {
        double score = 0;

        for (int i = 0; i < p; i++) {
            double relevance = getRelevance(urls.get(i), oracleUrls);
            int ranking = i + 1;

            if (ranking > 1) {
                // for all positions after the first one, reduce the "gain" as ranking increases
                relevance /= logBase2(ranking);
            }

            score += relevance;
        }

        return score;
    }

    private static double getRelevance(String url, List<String> oracleUrls) {
        // Use the position in the oracle ranking as the relevance
        int oracleIndex = oracleUrls.indexOf(url);
        if (oracleIndex > -1)
            return oracleUrls.size() - oracleIndex;
        else
            return 0;
    }

    private static double logBase2(double value) {
        return Math.log(value) / Math.log(2);
    }
}

