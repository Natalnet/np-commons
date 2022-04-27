package com.np.commons.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class TimeseriesOrigin extends Dataset
{
    public List<TimeseriesOrigin.Entry> entries = new ArrayList<TimeseriesOrigin.Entry>();
    
    public void setEntries(List<TimeseriesOrigin.Entry> entries)
    {
        this.entries = entries;
    }
    
    public class Entry extends Dataset.Entry implements Comparable<TimeseriesOrigin.Entry>
    {
        final public String key;
        
        public Entry(final String key, final JSONObject _values)
        {
            super(_values);
            
            this.key = new String(key);
        }
        
        public int compareTo(Entry o)
        {
            if (key == null || o == null || o.key == null) 
            {
                return 0;
            }
            
            return key.compareTo(o.key);
        }
    }
}
