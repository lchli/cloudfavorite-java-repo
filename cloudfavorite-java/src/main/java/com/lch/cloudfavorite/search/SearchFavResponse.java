package com.lch.cloudfavorite.search;

import com.lch.cloudfavorite.base.FavBaseResponse;

import java.util.List;

public class SearchFavResponse extends FavBaseResponse {
    public List<Item> items;

    public static class Item{
        public String uid;
        public String title;
        public String url;
        public long createDate;
    }

}
