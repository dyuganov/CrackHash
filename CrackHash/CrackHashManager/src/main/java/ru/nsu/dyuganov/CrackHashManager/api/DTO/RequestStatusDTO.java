package ru.nsu.dyuganov.CrackHashManager.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class RequestStatusDTO {
    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "data")
    private List<String> data;
}
