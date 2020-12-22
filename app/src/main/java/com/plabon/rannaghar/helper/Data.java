package com.plabon.rannaghar.helper;


import com.plabon.rannaghar.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class Data {

    List<Offer> offerList = new ArrayList<>();


    public List<Offer> getOfferList() {
        Offer offer = new Offer("https://globalitsolutionsgroup.com/mobapp/rannaghar/assets/images/ProductImage/product/20200918020957000000.png");
        offerList.add(offer);
        offer = new Offer("https://globalitsolutionsgroup.com/mobapp/rannaghar/assets/images/ProductImage/product/20200918020957000000.png");
        offerList.add(offer);
        offer = new Offer("https://globalitsolutionsgroup.com/mobapp/rannaghar/assets/images/ProductImage/product/20200918020957000000.png");
        offerList.add(offer);

        return offerList;
   }
}
