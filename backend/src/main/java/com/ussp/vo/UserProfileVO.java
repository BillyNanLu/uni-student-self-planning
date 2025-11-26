package com.ussp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileVO {
    private List<String> interests;
    private List<String> abilities;
    private List<String> selfEvaluation;
}
