package ro.bapr.internal.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by valentin.spac on 1/20/2016.
 */
public class LDObject {

    @JsonIgnore
    private String id;
    @JsonIgnore
    private Map<String, KeyValue> values;

    public LDObject() {
        values = new HashMap<>();
    }

    public void addKeyValue(KeyValue value) {
        KeyValue kv = values.get(value.getkey());

        if(kv != null) {
            kv.merge(value);
        } else {
            values.put(value.getkey(), value);
        }

        if(value.getkey().equalsIgnoreCase("id")) {
            try {
                id = value.getSingleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return id;
    }

    public void merge(LDObject obj) {
        obj.getMap().forEach((k, v) -> values.merge(k, v, (v1, v2) -> {
            v1.merge(v2);
            return v1;
        }));
    }

    @JsonIgnore
    public Map<String, KeyValue> getMap() { return  values; }
    @JsonIgnore
    public Collection<KeyValue> getValues() {
        return values.values();
    }

    @JsonAnyGetter
    private Map<String, List<String>> serialize() {
        Map<String, List<String>> result = new HashMap<>();

        values.values().forEach(value -> result.put(value.getkey(), value.getValues()));

        return result;
    }
}
