package com.app.Utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchUtils
{

    public static <T> List<List<T>> splitList(List<T> originalList, int chunkSize) {

       List<List<T>> chunks = new ArrayList<List<T>>();
       int listSize = originalList.size();
       for (int i = 0; i < listSize; i += chunkSize) {
           chunks.add(originalList.subList(i, Math.min(listSize, i + chunkSize)));
       }
       return chunks;
    }
}
