package com.np.commons.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.json.JSONObject;

import com.np.commons.model.TimeseriesOrigin;

public class StatisticsUtilOrigin extends DescriptiveStatistics
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public StatisticsUtilOrigin(int windowSize)
    {
        super(windowSize);
    }
    
    public StatisticsUtilOrigin()
    {
        super(3);
    }
    
    public Map<String, JSONObject> getStats(TimeseriesOrigin timeseries, final int windowSize) throws Exception 
    {
        if (!timeseries.entries.iterator().hasNext()) 
        {
            throw new Exception("error: timeseries entries empty.");
        }
        
        final Map<String, JSONObject> stats = new HashMap<String, JSONObject>();
        final Map<String, List<Double>> values = new HashMap<String, List<Double>>();
        
        timeseries.entries.iterator().next().values.keySet().forEach(key -> {
            values.put(key, new ArrayList<Double>());
        });
        
        timeseries.entries.stream()
            .sorted((firstEntry, secondEntry)-> {
                return firstEntry.key.compareTo(secondEntry.key);
            }).forEach(entry -> {
                values.keySet().forEach(key -> {
                    values.get(key).add(Double.valueOf(entry.values.get(key).toString()));
                });
            });
        
        values.keySet()
            .forEach(key -> {
                final DescriptiveStatistics ds = new DescriptiveStatistics(windowSize);
                values.get(key).forEach(ds::addValue);
                
                double median = ds.getPercentile(50);
                double q1 = ds.getPercentile(25);
                double q3 = ds.getPercentile(75);
                
                final JSONObject object = new JSONObject();
                object.put("mean", ds.getMean());
                object.put("std", ds.getStandardDeviation());
                object.put("median", median);
                object.put("q1", q1);
                object.put("q3", q3);
                object.put("iqr", (q3 - q1));
                object.put("trimmedMean", ((q1 + q3 + 2 * median) / 4));
                object.put("skewness", ds.getSkewness());
                stats.put(key, object);
                
                ds.clear();
            });
        
        return stats;
    }
    
    public Map<String, List<Double>> movingAverage(TimeseriesOrigin timeseries, final int windowSize) throws Exception 
    {
        if (!timeseries.entries.iterator().hasNext()) 
        {
            throw new Exception("error: timeseries entries empty.");
        }
        
        final Map<String, List<Double>> averages = new HashMap<String, List<Double>>();
        final Map<String, List<Double>> values = new HashMap<String, List<Double>>();
        
        timeseries.entries.iterator().next().values.keySet().forEach(key -> {
            averages.put(key, new ArrayList<Double>());
            values.put(key, new ArrayList<Double>());
        });
        
        timeseries.entries.stream()
            .sorted((firstEntry, secondEntry)-> {
                return firstEntry.key.compareTo(secondEntry.key);
            }).forEach(entry -> {
                values.keySet().forEach(key -> {
                    values.get(key).add(Double.valueOf(entry.values.get(key).toString()));
                });
            });
        
        values.keySet()
            .forEach(key -> {
                final DescriptiveStatistics ds = new DescriptiveStatistics(windowSize);
                final int size = values.get(key).size();
                if (size < ds.getWindowSize()) 
                {
                    throw new RuntimeException("error: the length of the series is less than the size of the window.");
                }
                
                int index = 0;
                for ( ; index < ds.getWindowSize(); index++)
                {
                    ds.addValue(values.get(key).get(index));
                }
                
                Double percentile = ds.getPercentile(50D);
                averages.get(key).add(percentile);
                
                for ( ; index < size; index++)
                {
                    ds.addValue(values.get(key).get(index));
                    averages.get(key).add(ds.getMean());
                }
                
                ds.clear();
            });
        
        return averages;
    }
    
    public Map<String, List<Double>> normalize(final TimeseriesOrigin timeseries) throws Exception 
    {
        if (!timeseries.entries.iterator().hasNext()) 
        {
            throw new Exception("error: timeseries entries empty.");
        }
        
        final Map<String, List<Double>> values = new HashMap<String, List<Double>>();
        
        timeseries.entries.iterator().next().values.keySet().forEach(key -> {
            values.put(key, new ArrayList<Double>());
        });
        
        timeseries.entries.stream()
            .sorted((firstEntry, secondEntry)-> {
                return firstEntry.key.compareTo(secondEntry.key);
            }).forEach(entry -> {
                values.keySet().forEach(key -> {
                    values.get(key).add(Double.valueOf(entry.values.get(key).toString()));
                });
            });
        
        values.keySet().stream()
            .forEach(key -> {
                final DescriptiveStatistics ds = new DescriptiveStatistics(values.get(key).size());
                values.get(key).forEach(ds::addValue);
                values.put(key, values.get(key).stream()
                    .map(element -> {
                        return (element - ds.getMean())/ds.getStandardDeviation();
                    }).collect(Collectors.toList()));
                ds.clear();
            });
        
        return values;
    }
    
    public Map<String, List<Double>> normalize(final TimeseriesOrigin timeseries, int windowSize) throws Exception
    {
        setWindowSize(windowSize);
        
        return normalize(timeseries);
    }
    
    public static void main(String [] args) throws Exception
    {
        new StatisticsUtilOrigin().test();
    }
    
    public void test() throws Exception
    {
        TimeseriesOrigin timeseries = new TimeseriesOrigin();
        
        List<TimeseriesOrigin.Entry> entries = new ArrayList<TimeseriesOrigin.Entry>();
        JSONObject jsObject = new JSONObject();
        jsObject.put("death", 1);
        jsObject.put("infected", 1);
        TimeseriesOrigin.Entry entry = timeseries.new Entry("1", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 2);
        jsObject.put("infected", 3);
        entry = timeseries.new Entry("2", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 3);
        jsObject.put("infected", 5);
        entry = timeseries.new Entry("3", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 4);
        jsObject.put("infected", 7);
        entry = timeseries.new Entry("4", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 5);
        jsObject.put("infected", 9);
        entry = timeseries.new Entry("5", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 6);
        jsObject.put("infected", 11);
        entry = timeseries.new Entry("6", jsObject);
        entries.add(entry);
        
        jsObject = new JSONObject();
        jsObject.put("death", 7);
        jsObject.put("infected", 13);
        entry = timeseries.new Entry("7", jsObject);
        entries.add(entry);
        
        timeseries.setEntries(entries);
        
        final Map<String, List<Double>> normalizedValues = normalize(timeseries, 5);
        normalizedValues.keySet().forEach(key -> {
            Stream.of(normalizedValues.get(key)).forEach(elemn -> {
                System.out.println("norm: "+key+": "+elemn);
            });
        });
        
        System.out.println();
        final Map<String, List<Double>> avgValues = movingAverage(timeseries, 3);
        avgValues.keySet().forEach(key -> {
            System.out.println("avgValues.get("+key+").size(): "+ avgValues.get(key).size());
            Stream.of(avgValues.get(key)).forEach(elemn -> {
                System.out.println("avg: "+ key+": "+elemn);
            });
        });
        
        System.out.println();
        System.out.println(saveMovingAverage(avgValues));
        
        System.out.println();
        final Map<String, JSONObject> statsValues = getStats(timeseries, 5);
        statsValues.keySet().forEach(key -> {
            System.out.println("stats: "+ key+": "+statsValues.get(key).toString());
        });
    }
    
    private String saveMovingAverage(Map<String, List<Double>> map) 
    {
        final String[] fields = new String[map.keySet().size()]; 
        final Double[][] values = new Double[map.keySet().size()][]; 
        
        final Counter counterField = new Counter();
        map.keySet().forEach(key -> 
        {
            System.out.println(">> map.get("+key+").size(): "+map.get(key).size());
            
            final Counter counterRow = new Counter();
            fields[counterField.value] = key;
            values[counterField.value] = new Double[map.get(key).size()];
            map.get(key).forEach(elemn -> 
            {
                values[counterField.value][counterRow.value++] = elemn.doubleValue();
            });
            counterField.value++;
        });
        
        final StringBuffer buffer = new StringBuffer();
        for (int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++)
        {
            buffer.append(fields[fieldIndex]).append(";");
        }
        buffer.append("\n");
        
        for (int valueIndex = 0; valueIndex < values[0].length; valueIndex++)
        {
            for (int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++)
            {
                buffer.append(values[fieldIndex][valueIndex]).append(";");
            }
            buffer.append("\n");
        }
        
        return buffer.toString();
    }
    
    public class Counter 
    {
        public int value = 0;
    }
}
