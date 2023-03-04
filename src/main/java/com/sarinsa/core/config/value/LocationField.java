package com.sarinsa.core.config.value;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;

public class LocationField extends AbstractConfigField<String> {

    public LocationField(String key, Location defaultValue) {
        super(key, defaultValue.toString(), LocationField::isValidLocation);
    }

    /** Checks is the given String can be parsed as a Location. */
    private static boolean isValidLocation(String s) {
        String[] components = s.split(",");

        if (components.length != 6) {
            return false;
        }
        // Ignore first index; expected to be the name
        // of the world the location is in
        for (int i = 1; i < components.length; i++) {
            if (StringUtils.isNumeric(components[i]))
                continue;
            return false;
        }
        return true;
    }
}
