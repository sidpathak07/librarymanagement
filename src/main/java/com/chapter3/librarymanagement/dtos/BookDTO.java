package com.chapter3.librarymanagement.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    private Long id;
    private String bookTitle;
    private LocalDate createdAt;
    private MiniAuthorDTO author;
}
