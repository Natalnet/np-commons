package com.np.commons.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Dataset extends Data
{
    static public enum Format { CSV, JSON, TXT, COMPOSED };
    
    public List<Dataset.Entry> entries;
    
    public String format;
    
    public String status;
    
    public String version;
    
    public String url;
    
    public Dataset(List<Dataset.Entry> entries)
    {
        this.entries = entries;
    }
    
    public Dataset()
    {
        this.entries = new ArrayList<Dataset.Entry>();
    }
    
    public class Entry
    {
        final public JSONObject values = new JSONObject();
        
        public Entry(final JSONObject _values)
        {
            _values.keySet().stream().parallel().forEach(_key -> {
                values.put(_key, _values.get(_key));
            });
        }
        
        @Override
        public String toString()
        {
            return "Dataset.Entry [values=" + values.toString() + "]";
        }
    }
}
