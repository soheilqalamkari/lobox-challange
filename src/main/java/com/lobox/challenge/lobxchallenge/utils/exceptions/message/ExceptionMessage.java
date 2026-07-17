package com.lobox.challenge.lobxchallenge.utils.exceptions.message;

import java.time.Instant;

public record ExceptionMessage(Instant timestamp, String message, String path) {
}
