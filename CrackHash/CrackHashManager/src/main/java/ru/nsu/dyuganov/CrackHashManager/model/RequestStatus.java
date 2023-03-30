package ru.nsu.dyuganov.CrackHashManager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.dyuganov.CrackHashManager.model.RequestStatus.Status.IN_PROGRESS;

@Data
public class RequestStatus {
    private Status status = IN_PROGRESS;
    private List<String> result = new ArrayList<>();
    public enum Status {
        IN_PROGRESS,
        READY,
        ERROR
    }
}