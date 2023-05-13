package ru.nsu.dyuganov.CrackHashManager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RequestStatus {
    private Status status = Status.IN_PROGRESS;
    private List<String> data = new ArrayList<>();
}