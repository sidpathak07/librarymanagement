package com.chapter3.librarymanagement.projections;

import java.time.LocalDate;

public interface MiniBookProjection {
    Long getId();
    String getBookTitle();
    LocalDate getCreatedAt();
}
