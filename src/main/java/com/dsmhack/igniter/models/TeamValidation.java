package com.dsmhack.igniter.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TeamValidation {
    String teamName;
    List<String> members;
    Map<String,String> ancilaryDetails = new HashMap<>();
}
