package com.np.commons.stats;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.np.commons.model.Timeseries;

public class StatisticsUtil extends DescriptiveStatistics
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public StatisticsUtil(int windowSize)
    {
        super(windowSize);
    }
    
    public StatisticsUtil()
    {
        super(3);
    }
    
    public Timeseries getMovingAverage(Timeseries timeseries, final int windowSize) throws Exception 
    {
        final Timeseries avgTimeseries = new Timeseries(timeseries.fields, (timeseries.timestamps.length - windowSize + 1));
        
        for (int fieldIndex = 0; fieldIndex < timeseries.fields.length; fieldIndex++)
        {
            if (timeseries.values[fieldIndex].length < windowSize) 
            {
                throw new RuntimeException("error: the length of the series is less than the size of the window.");
            }
            
            final DescriptiveStatistics ds = new DescriptiveStatistics(windowSize);
            
            int vIndex = 0;
            for (int valueIndex = 0; valueIndex < ds.getWindowSize(); valueIndex++)
            {
                ds.addValue((Double) timeseries.values[fieldIndex][valueIndex]);
                vIndex = valueIndex;
            }
            
            avgTimeseries.emplaceAt(timeseries.fields[fieldIndex], ds.getMean(), (vIndex - windowSize + 1), timeseries.timestamps[vIndex]);
            
            for (int valueIndex = ds.getWindowSize(); valueIndex < timeseries.values[fieldIndex].length; valueIndex++)
            {
                ds.addValue((Double) timeseries.values[fieldIndex][valueIndex]);
                avgTimeseries.emplaceAt(timeseries.fields[fieldIndex], ds.getMean(), (valueIndex - windowSize + 1), timeseries.timestamps[valueIndex]);
            }
            
            ds.clear();
        }
        
        return avgTimeseries;
    }
    
    /*
    public Map<String, List<Double>> normalize(final Timeseries timeseries) throws Exception 
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
    public Map<String, List<Double>> normalize(final Timeseries timeseries, int windowSize) throws Exception
    {
        setWindowSize(windowSize);
        
        return normalize(timeseries);
    }
     */
    
    
    public static void main(String [] args) throws Exception
    {
        new StatisticsUtil().test();
    }
    
    @SuppressWarnings("deprecation")
    public void test() throws Exception
    {
        Timeseries timeseries = new Timeseries(new String [] { "death", "infected" }, 5);
        timeseries.emplace("death", new Double(1D), 0L);
        timeseries.emplace("infected", new Double(1D), 0L);
        
        timeseries.emplace("death", new Double(2D), 1L);
        timeseries.emplace("infected", new Double(3D), 1L);
        
        timeseries.emplace("death", new Double(3D), 2L);
        timeseries.emplace("infected", new Double(5D), 2L);
        
        timeseries.emplace("death", new Double(4D), 3L);
        timeseries.emplace("infected", new Double(7D), 3L);
        
        timeseries.emplace("death", new Double(5D), 4L);
        timeseries.emplace("infected", new Double(9D), 4L);
        
        int windowSize = 3;
        Timeseries avgTimeseries = new StatisticsUtil(windowSize).getMovingAverage(timeseries, windowSize);
        
        System.out.println(printMovingAverage(avgTimeseries));
    }
    
    private String printMovingAverage(Timeseries timeseries) 
    {
        StringBuffer buffer = new StringBuffer("timestamp;");
        
        for (int fieldIndex = 0; fieldIndex < timeseries.fields.length; fieldIndex++) 
        {
            buffer.append(timeseries.fields[fieldIndex]).append(";");
        }
        buffer.append("\n");
        
        int valueIndex = 0;
        while (valueIndex < timeseries.timestamps.length)
        {
            buffer.append(timeseries.timestamps[valueIndex]).append(";");
            
            for (int fieldIndex = 0; fieldIndex < timeseries.fields.length; fieldIndex++) 
            {
                buffer.append(timeseries.values[fieldIndex][valueIndex]).append(";");
            }
            buffer.append("\n");
            
            valueIndex++;
        }
        
        return buffer.toString();
    }
    
    public class Counter 
    {
        public int value = 0;
    }
}
