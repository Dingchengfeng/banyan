package com.standard.banyan.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestEvent {

    private String type;
    private String value;
}
