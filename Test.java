package com.np.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class Test
{
    public static void main(String[] args)
    {
        new Test().test();
    }
    
    public void test()
    {
        Queue<String> queue = new 
        
        /*
        Timeseries timeseries = new Timeseries();
        
        List<Timeseries.Entry> entries = new ArrayList<Timeseries.Entry>();
        JSONObject jsObject = new JSONObject();
        jsObject.put("death", 2);
        jsObject.put("infected", 8);
        Timeseries.Entry entry = timeseries.new Entry(2L, jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 3);
        jsObject.put("infected", 6);
        entry = timeseries.new Entry(5L, jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 0);
        jsObject.put("infected", 4);
        entry = timeseries.new Entry(4L, jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 1);
        jsObject.put("infected", 4);
        entry = timeseries.new Entry(1L, jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 0);
        jsObject.put("infected", 5);
        entry = timeseries.new Entry(3L, jsObject);
        entries.add(entry);
        
        timeseries.setEntries(entries);
        
        Timeseries.Entry [] array = timeseries.entries.toArray(new Timeseries.Entry[entries.size()]);
        
        List<Timeseries.Entry> normalizedSample = Stream.of(array).parallel()
            .filter(element -> {
                if (element == null || element.key == null || element.values == null) {
                    throw new RuntimeException("there is a 'null' entry<key, value>");
                }
                return true;
            })
            .sorted((firstElemn, secondElemn)-> {
                return firstElemn.key.compareTo(secondElemn.key);
            })
            .map(element -> {
                element.values.keySet().forEach(key -> {
                   element.values.put(key, (element.values.getDouble(key)+2));
                });
                return element;
            }).collect(Collectors.toList());
        
        normalizedSample.forEach(element -> {
            System.out.println(element);
        });
         */
    }
    
    public void tes() 
    {
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        
        Map<Boolean, List<Integer>> groups = intList.stream().collect(Collectors.partitioningBy(s -> {
            return s > 4;
        }));
        System.out.println(groups);
        
        List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
        System.out.println(subSets);
        //List<Integer> lastPartition = subSets.get(1);
        //List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        
    }
}
