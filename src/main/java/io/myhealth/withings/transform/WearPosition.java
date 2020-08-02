package io.myhealth.withings.transform;

import java.util.HashMap;
import java.util.Map;

public enum WearPosition {

    RIGHT_WRIST(0, "Right Wrist"),
    LEFT_WRIST(1, "Left Wrist"),
    RIGHT_ARM(2, "Right Arm"),
    LEFT_ARM(3, "Left Arm"),
    RIGHT_FOOT(4, "Right Foot"),
    LEFT_FOOT(5, "Left Foot"),
    UNKNOWN(-1, "Unknown");

    private static final Map<Integer, WearPosition> BY_ID = new HashMap<>();

    static {
        for (WearPosition wp : values()) {
            BY_ID.put(wp.id, wp);
        }
    }

    private final int id;
    private final String name;

    private WearPosition(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static WearPosition valueOf(int id) {
        return BY_ID.getOrDefault(id, UNKNOWN);
    }
}