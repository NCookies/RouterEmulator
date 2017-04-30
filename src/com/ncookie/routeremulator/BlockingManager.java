package com.ncookie.routeremulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class BlockingManager {
    private ArrayList<String> blockingUrlList = new ArrayList<String>();

    public void addBlockingUrl(String url) {
        blockingUrlList.add(url);
    }

//    public void modifyBlockingUrl(int index, String url) {
//
//    }
//
//    public void deleteBlockingUrl(int index) {
//
//    }

    ArrayList<String> getBlockingUrlList() {
        return blockingUrlList;
    }
}
