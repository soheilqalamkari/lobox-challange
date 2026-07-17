package com.lobox.challenge.lobxchallenge.utils.pagination;

import java.util.List;

public record CursorPage<T>(
        List<T> items,
        String nextCursor,
        boolean hasNext
) {
}
