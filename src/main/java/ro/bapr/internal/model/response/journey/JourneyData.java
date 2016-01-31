package ro.bapr.internal.model.response.journey;

import java.util.List;

import ro.bapr.internal.model.LDObject;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.01.2016.
 */
public class JourneyData {
    List<LDObject> locations;
    LDObject journeyData;

    public List<LDObject> getLocations() {
        return locations;
    }

    public JourneyData setLocations(List<LDObject> locations) {
        this.locations = locations;
        return this;
    }

    public LDObject getJourneyData() {
        return journeyData;
    }

    public JourneyData setJourneyData(LDObject journeyData) {
        this.journeyData = journeyData;
        return this;
    }

    public void removeMappingProperty(String property) {
        journeyData.getMap().remove(property);
    }
}