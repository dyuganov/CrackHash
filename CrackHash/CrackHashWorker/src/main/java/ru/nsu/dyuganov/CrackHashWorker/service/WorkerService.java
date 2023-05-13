package ru.nsu.dyuganov.CrackHashWorker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;
import ru.nsu.dyuganov.CrackHashWorker.api.DTO.OkResponseDTO;
import ru.nsu.dyuganov.CrackHashWorker.model.HashCracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@EnableScheduling
public class WorkerService {

    @Value("${crackHashService.manager.ip}")
    private String managerIp;
    @Value("${crackHashService.manager.port}")
    private Integer managerPort;

    ExecutorService executors = Executors.newFixedThreadPool(5);

    @Autowired
    private RestTemplate restTemplate;

    public void processTask(CrackHashManagerRequest request) {
        executors.execute(() -> { crackCode(request); });
    }

    private void crackCode(CrackHashManagerRequest request){
        log.info("Started processing task: {}", request.getRequestId());

        List<String> answers = HashCracker.crack(
                request.getHash(),
                request.getMaxLength(),
                request.getAlphabet().getSymbols()
        );

        log.info("Finished processing task : {}", request.getRequestId());
        sendResponse(WorkerResponseBuilder.buildResponse(request.getRequestId(), request.getPartNumber(), answers));
    }

    private void sendResponse(CrackHashWorkerResponse response) {
        String url = String.format(
                "http://%s:%s/api/internal/manager/hash/crack/request",
                managerIp,
                managerPort
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<CrackHashWorkerResponse> entity = new HttpEntity<>(response, headers);

        restTemplate.patchForObject(
                url,
                entity,
                OkResponseDTO.class
        );
    }
}