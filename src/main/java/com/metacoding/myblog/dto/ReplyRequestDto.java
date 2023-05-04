package com.metacoding.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDto {
    private int userId;
    private int boardId;
    private String content;
}
