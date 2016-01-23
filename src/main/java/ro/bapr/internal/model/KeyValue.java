package ro.bapr.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by valentin.spac on 1/20/2016.
 */
public class KeyValue {

    private String key;
    private List<String> values;

    public KeyValue() {
        this(null, new ArrayList<>());
    }

    public KeyValue(String key) {
        this(key, new ArrayList<>());
    }
    public KeyValue(String key, String value) {
        this.key = key;

        values = new ArrayList<>();
        values.add(value);
    }

    public KeyValue(String key, List<String> values) {
        this.key = key;
        this.values = values;
    }

    public void put(String value) {
        values.add(value);
    }

    @JsonIgnore
    public String getSingleValue() throws Exception {
        if(values.size() != 1) {
            throw new Exception("Multiple values found!");
        }
        return values.get(0);
    }

    @JsonIgnore
    public List<String> getValues() {
        return values;
    }

    @JsonIgnore
    public String getkey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Stream<String> stream() {
        return values.stream();
    }

    public void merge(KeyValue value) {
        if(this.key == null || this.key.equalsIgnoreCase(value.getkey())) {
            List<String> missing = value.stream()
                    .filter(v -> !values.contains(v))
                    .collect(Collectors.toList());

            values.addAll(missing);
        }
    }

    @JsonAnyGetter
    private Map<String, List<String>> serialize() {
        return Collections.singletonMap(key, values);
    }


}
