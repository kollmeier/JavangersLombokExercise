package ckollmeier.de.Entities;

import lombok.Builder;
import lombok.With;

import java.util.Map;

@Builder
@With
public record University(
        String id,
        String name,
        Map<String, Course> courses
) {
}
