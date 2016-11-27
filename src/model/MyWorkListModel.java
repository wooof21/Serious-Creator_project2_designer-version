/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author Liming Chu
 *
 * @param
 * @return
 */
public class MyWorkListModel {

    private String title;
    private ArrayList<String> picAddr;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<String> getPicAddr() {
        return picAddr;
    }
    public void setPicAddr(ArrayList<String> picAddr) {
        this.picAddr = picAddr;
    }
    
    
}
