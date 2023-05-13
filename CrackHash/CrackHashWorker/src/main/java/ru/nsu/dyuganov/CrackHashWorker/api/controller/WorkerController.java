package ru.nsu.dyuganov.CrackHashWorker.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.dyuganov.CrackHashWorker.api.DTO.OkResponseDTO;
import ru.nsu.dyuganov.CrackHashWorker.service.WorkerService;

@Slf4j
@Controller
@RequestMapping("/internal/api/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @PostMapping(value = "/hash/crack/task", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OkResponseDTO> getTask(@RequestBody CrackHashManagerRequest request) {
        log.info("Received task: {}", request);
        workerService.processTask(request);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO());
    }
}

