package com.danielesteban.utils;

import com.danielesteban.dto.RoomDto;

import java.util.HashMap;
import java.util.Map;

public class DataDummy {

    private DataDummy() {}

    public static final Map<RoomDto, Boolean> default_rooms = new HashMap<>() {{
        put(new RoomDto("A", 2), true);
        put(new RoomDto("B", 2), true);
        put(new RoomDto("C", 3), true);
        put(new RoomDto("D", 2), false);
        put(new RoomDto("E", 2), false);
        put(new RoomDto("F", 3), false);
    }};
}
