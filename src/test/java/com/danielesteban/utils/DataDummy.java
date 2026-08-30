package com.danielesteban.utils;

import com.danielesteban.dto.RoomDto;

import java.util.HashMap;
import java.util.List;
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

    public static final List<RoomDto> default_rooms_list = List.of(
            new RoomDto("A", 2),
            new RoomDto("B", 3),
            new RoomDto("C", 2),
            new RoomDto("D", 2),
            new RoomDto("E", 3),
            new RoomDto("F", 3));
}
