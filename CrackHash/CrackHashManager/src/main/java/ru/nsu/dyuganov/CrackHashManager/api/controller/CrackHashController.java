package ru.nsu.dyuganov.CrackHashManager.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.WorkerRequestDTO;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.RequestIdDTO;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.RequestStatusDTO;
import ru.nsu.dyuganov.CrackHashManager.service.CrackHashMangerService;

@Slf4j
@Controller
@RequestMapping("/api/hash")
public class CrackHashController {

    @Autowired
    CrackHashMangerService crackHashService;

    @PostMapping("/crack")
    public ResponseEntity<RequestIdDTO> crackHash(@RequestBody WorkerRequestDTO request) {
        log.info("Received request to crack hash: {}", request);
        return new ResponseEntity<>(
                new RequestIdDTO(crackHashService.crackHash(request.getHash(), request.getMaxLength())), HttpStatus.OK);
    }

    @GetMapping("/status/{requestId}")
    public ResponseEntity<RequestStatusDTO> getStatus(@PathVariable String requestId) {
        log.info("Received request to get status of request: {}", requestId);
        return new ResponseEntity<>(crackHashService.getStatus(requestId), HttpStatus.OK);
    }
}

