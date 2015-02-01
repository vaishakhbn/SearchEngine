package edu.uci;

/**
 * Created by swanand on 2/1/2015.
 */
public class ToJSON {
    private static ToJSON toJSON = new ToJSON();
    private ToJSON(){
    }
    public static ToJSON getInstance(){
        if(toJSON == null){
            return new ToJSON();
        }
        return toJSON;
    }
    public void convert(PageDetails pageDetails) {


    }
}
