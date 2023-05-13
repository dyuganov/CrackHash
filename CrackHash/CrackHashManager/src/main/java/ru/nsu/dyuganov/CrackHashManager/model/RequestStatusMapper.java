package ru.nsu.dyuganov.CrackHashManager.model;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.RequestStatusDTO;

@Mapper
public interface RequestStatusMapper {
    RequestStatusMapper INSTANCE = Mappers.getMapper(RequestStatusMapper.class);

    RequestStatusDTO toRequestStatusDTO(RequestStatus requestStatus);

    @InheritInverseConfiguration
    RequestStatus toRequestStatus(RequestStatusDTO requestStatusDTO);
}
