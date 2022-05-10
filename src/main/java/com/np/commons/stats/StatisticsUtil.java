package com.np.commons.stats;

import java.util.Date;
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
            
            int valueIdx_ = 0;
            try
            {
                for (int valueIdx = 0; valueIdx < limit; valueIdx++)
                {
                    valueIdx_ = valueIdx;
                    addData(timeseries.values[fieldIndex][valueIdx]);
                    
                    avgTimeseries.emplaceAt(timeseries.fields[fieldIndex], timeseries.values[fieldIndex][valueIdx], valueIdx, timeseries.timestamps[valueIdx]);
                }
            }
            catch (Exception e)
            {
                System.out.println("fieldIndex: "+fieldIndex);
                System.out.println("valueIdx: "+valueIdx_);
                System.out.println("timeseries.values["+fieldIndex+"].length: "+timeseries.values[fieldIndex].length);
                System.out.println("timeseries.values["+fieldIndex+"]["+valueIdx_+"]: "+timeseries.values[fieldIndex][valueIdx_]);
                System.out.println("timeseries.fields["+fieldIndex+"]: "+timeseries.fields[fieldIndex]);
                System.out.println("timeseries.timestamps["+valueIdx_+"]: "+timeseries.timestamps[valueIdx_]);
                System.out.println("Date: "+new Date(timeseries.timestamps[valueIdx_]));
                e.printStackTrace();
                throw e;
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
