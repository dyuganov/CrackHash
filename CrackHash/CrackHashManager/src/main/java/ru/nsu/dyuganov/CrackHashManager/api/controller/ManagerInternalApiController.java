package ru.nsu.dyuganov.CrackHashManager.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.OkResponseDTO;
import ru.nsu.dyuganov.CrackHashManager.service.CrackHashMangerService;

@Controller
@Slf4j
@RequestMapping("/api/internal/manager")
public class ManagerInternalApiController {

    @Autowired
    CrackHashMangerService crackHashService;
    @PatchMapping(value = "/hash/crack/request",  consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<OkResponseDTO> handleWorkerResponse(@RequestBody CrackHashWorkerResponse crackHashWorkerResponse) {
        log.info("Received worker response: {}", crackHashWorkerResponse);
        crackHashService.handleWorkerResponse(crackHashWorkerResponse);

        return new ResponseEntity<>(new OkResponseDTO(), HttpStatus.OK);
    }
}

