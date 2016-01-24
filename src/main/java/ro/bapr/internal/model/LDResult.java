package ro.bapr.internal.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by valentin.spac on 1/21/2016.
 */
public class LDResult {

    private Map<String, LDObject> resultItems;

    public LDResult() {
        this.resultItems = new HashMap<>();
    }

    public void add(LDObject obj) {
        LDObject currentObj = resultItems.get(obj.getId());

        if(currentObj == null) {
            resultItems.put(obj.getId(), obj);
        } else {
            currentObj.merge(obj);
        }
    }

    public Collection<LDObject> getMergedResults() {
        return resultItems.values();
    }

    private void merge(List<LDObject> ldObjects, List<LDObject> result) {

        final LDObject[] aux = new LDObject[1];

        ldObjects.forEach(v -> {
            if(aux[0] == null) {
                aux[0] = v;
            } else {
                aux[0].merge(v);
            }

        });
        result.add(aux[0]);


    }
}
