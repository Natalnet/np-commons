package com.np.commons.stats;

import java.util.LinkedList;
import java.util.Queue;

import com.np.commons.model.Timeseries;

public class StatisticsUtil
{
    private final Queue<Double> Dataset = new LinkedList<Double>();
    private final int windowSize;
    private double sum;
    
    public StatisticsUtil(Integer windowSize)
    {
        this.windowSize = windowSize;
    }

    private void addData(double value)
    {
        sum += value;
        Dataset.add(value);
        
        if (Dataset.size() > windowSize)
        {
            sum -= Dataset.remove();
        }
    }

    private double getMean()
    {
        return sum / windowSize;
    }

    public Timeseries getMovingAverage(String dateFieldName, Timeseries timeseries) throws Exception 
    {
        final Timeseries avgTimeseries = new Timeseries(timeseries.fields, timeseries.timestamps.length);
        
        for (int fieldIndex = 0; fieldIndex < timeseries.fields.length; fieldIndex++)
        {
            if (timeseries.values[fieldIndex].length < this.windowSize) 
            {
                throw new RuntimeException("error: the length of the series is less than the size of the window.");
            }
            
            int limit = this.windowSize - 1;
            
            for (int valueIdx = 0; valueIdx < limit; valueIdx++)
            {
                addData(timeseries.values[fieldIndex][valueIdx]);
                
                avgTimeseries.emplaceAt(timeseries.fields[fieldIndex], timeseries.values[fieldIndex][valueIdx], valueIdx, timeseries.timestamps[valueIdx]);
            }
            
            final int timeseriesLength = timeseries.values[fieldIndex].length;
            
            for (int valueIdx = limit; valueIdx < timeseriesLength; valueIdx++)
            {
                addData(timeseries.values[fieldIndex][valueIdx]);
                
                avgTimeseries.emplaceAt(timeseries.fields[fieldIndex], getMean(), valueIdx, timeseries.timestamps[valueIdx]);
            }
        }
        
        return avgTimeseries;
    }
}
