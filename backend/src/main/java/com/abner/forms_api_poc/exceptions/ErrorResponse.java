package com.abner.forms_api_poc.exceptions;

import java.time.Instant;

public record ErrorResponse(String type, String message, String path, Instant timestamp) {}
