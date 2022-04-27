package com.np.commons.model;

import java.util.Arrays;
import java.util.stream.Stream;

public class Timeseries
{
    final public String [] fields;
    final public Long [] timestamps;
    final public Object [][] values;
    
    final private Integer capacity;
    final private Integer positions [];
    
    public Timeseries (String [] fields, int capacity)throws Exception 
    {
        this.fields = new String[fields.length];
        this.timestamps = new Long[capacity];
        this.values = new Object[this.fields.length][];
        this.positions = new Integer[fields.length];
        
        for (int index = 0; index < fields.length; index++) 
        {
            this.fields[index] = fields[index];
            this.values[index] = new Object[capacity];
            this.positions[index] = 0;
            
            Arrays.fill(this.values[index], null);
        }
        
        this.capacity = capacity;
    }
    
    public Timeseries (String [] fields, Object[][] values) throws Exception 
    {
        if (values.length > 0 && values[0].length > 0)
        {
            this.fields = new String[fields.length];
            this.timestamps = new Long[values[0].length];
            this.values = new Object[this.fields.length][];
            this.positions = new Integer[fields.length];
            
            this.fields[0] = fields[0];
            this.values[0] = new Object[values[0].length];
            this.positions[0] = 0;
            
            Arrays.fill(this.values[0], null);
            Arrays.fill(Arrays.stream(this.values[0]).toArray(Object[]::new), values[0]);
            
            for (int index = 1; index < fields.length; index++) 
            {
                if (this.values[0].length == values[index].length)
                {
                    this.fields[index] = fields[index];
                    this.values[index] = new Object[values[0].length];
                    this.positions[index] = 0;
                    
                    Arrays.fill(this.values[index], null);
                    Arrays.fill(Arrays.stream(this.values[index]).toArray(Object[]::new), values[index]);
                }
                else
                {
                    throw new Exception("error: the size of the time series diverge.");
                }
            }
            
            this.capacity = values[0].length;
        }
        else
        {
            throw new Exception("error: there is no time series, or there is no entries in any time series.");
        }
    }
    
    int getFieldPositionByFieldName(String fieldName)
    {
        int fPosition = 0;
        for ( ; fPosition < this.fields.length; fPosition++)
        {
            if (this.fields[fPosition].equals(fieldName)) 
            {
                return fPosition;
            }
        }
        
        return -1;
    }
    
    public void emplace(final String fieldName, final Object value, final Long timestamp) throws Exception
    {
        int fPosition = 0;
        if ((fPosition = this.getFieldPositionByFieldName(fieldName)) == -1)
        {
            throw new Exception("error: this time series does not exists.");
        }
        
        this.values[fPosition][this.positions[fPosition]] = value;
        this.timestamps[this.positions[fPosition]] = timestamp;
        this.positions[fPosition]++;
    }
    
    public void emplaceAt(final String fieldName, final Object value, int position, final Long timestamp) throws Exception
    {
        int fPosition = 0;
        if ((fPosition = this.getFieldPositionByFieldName(fieldName)) == -1)
        {
            throw new Exception("error: this time series does not exists.");
        }
        
        if (this.capacity <= position) 
        {
            throw new Exception("error: this position does not exists in thistime series.");
        }
        
        this.values[fPosition][position] = value;
        this.timestamps[position] = timestamp;
    }
    
    public Stream<Object> getStream(final String fieldName) throws Exception
    {
        int fPosition = 0;
        if ((fPosition = this.getFieldPositionByFieldName(fieldName)) == -1)
        {
            throw new Exception("error: this time series does not exists.");
        }
        return Arrays.stream(this.values[fPosition]);
    }
}