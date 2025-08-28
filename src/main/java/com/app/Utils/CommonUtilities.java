package com.app.Utils;

import com.app.Entity.Entry;
import com.app.Entity.Vote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CommonUtilities
{

    private static Gson gson = new Gson();

    public static String convertObjectToJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T convertJsonToObject(String json, Class<T> classOfT)
    {
        return gson.fromJson(json, classOfT);
    }

    public static List<Entry>  convertJsonToEntryList(String json)
    {
        return gson.fromJson(json, new TypeToken<List<Entry>>(){}.getType());
    }

    public static List<Vote>  convertJsonToVoteList(String json)
    {
        return gson.fromJson(json, new TypeToken<List<Vote>>(){}.getType());
    }


}