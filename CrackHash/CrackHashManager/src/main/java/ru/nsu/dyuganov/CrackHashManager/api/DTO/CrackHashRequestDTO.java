package ru.nsu.dyuganov.CrackHashManager.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class CrackHashRequestDTO {
    @JsonProperty(value = "hash", required = true)
    private String hash;

    @JsonProperty(value = "maxLength", required = true)
    private int maxLength;
}