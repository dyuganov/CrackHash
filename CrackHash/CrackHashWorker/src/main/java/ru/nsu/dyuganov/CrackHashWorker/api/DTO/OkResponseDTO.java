package ru.nsu.dyuganov.CrackHashWorker.api.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class OkResponseDTO {}
