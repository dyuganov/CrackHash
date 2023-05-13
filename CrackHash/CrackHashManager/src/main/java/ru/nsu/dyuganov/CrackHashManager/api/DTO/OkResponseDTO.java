package ru.nsu.dyuganov.CrackHashManager.api.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize(builder = OkResponseDTO.OkResponseDTOBuilder.class)
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class OkResponseDTO {}
