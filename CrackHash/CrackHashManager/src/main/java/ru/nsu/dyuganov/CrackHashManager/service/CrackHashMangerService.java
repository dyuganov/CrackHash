package ru.nsu.dyuganov.CrackHashManager.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.OkResponseDTO;
import ru.nsu.dyuganov.CrackHashManager.api.DTO.RequestStatusDTO;
import ru.nsu.dyuganov.CrackHashManager.model.RequestStatus;
import ru.nsu.dyuganov.CrackHashManager.model.RequestStatusMapper;
import ru.nsu.dyuganov.CrackHashManager.model.Status;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
@EnableScheduling
public class CrackHashMangerService {
    private final Map<String, RequestStatus> requests = new ConcurrentHashMap<>();
    private final RequestStatusMapper requestStatusMapper = RequestStatusMapper.INSTANCE;

    private final ArrayDeque<Pair<String, Timestamp>> pendingRequests = new ArrayDeque<>();
    private final CrackHashManagerRequest.Alphabet alphabet = new CrackHashManagerRequest.Alphabet();

    @Value("${crackHashService.worker.ip}")
    private String workerIp;
    @Value("${crackHashService.worker.port}")
    private Integer workerPort;
    @Value("${crackHashService.manager.expireTimeMinutes}")
    private Integer expireTimeMinutes;
    @Value("${crackHashService.alphabet}")
    private String alphabetString;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        List.of(alphabetString.split("")).forEach(alphabet.getSymbols()::add);
    }

    public String crackHash(String hash, int maxLength) {
        var id = UUID.randomUUID().toString().substring(0, 7);
        requests.put(id, new RequestStatus());
        var managerRequest = createManagerRequest(hash, maxLength, id);
        try {
            log.info("Sending request to worker: {}", managerRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            restTemplate.exchange(
                    String.format(
                            "http://%s:%s/internal/api/worker/hash/crack/task",
                            workerIp,
                            workerPort
                    ),
                    HttpMethod.POST,
                    new HttpEntity<>(managerRequest, headers),
                    OkResponseDTO.class
            );

        } catch (Exception e) {
            log.error("Error while sending request to worker", e);
            e.getStackTrace();
            return null;
        }
        pendingRequests.add(Pair.of(id, new Timestamp(System.currentTimeMillis())));
        return id;
    }

    public RequestStatusDTO getStatus(String requestId) {
        return requestStatusMapper.toRequestStatusDTO(requests.get(requestId));
    }

    public void handleWorkerResponse(CrackHashWorkerResponse workerResponse) {
        log.info("Received response from worker");
        RequestStatus requestStatus = requests.get(workerResponse.getRequestId());
        if (requestStatus.getStatus() == Status.IN_PROGRESS) {
            if (workerResponse.getAnswers() != null) {
                requestStatus.getData().addAll(workerResponse.getAnswers().getWords());
                log.info("Response answer: {}", workerResponse.getAnswers().getWords());
                requestStatus.setStatus(Status.READY);
            }
        }
    }

    @Scheduled(fixedDelay = 60 * 1000)
    private void expireRequests() {
        pendingRequests.removeIf(pair -> {
            if (System.currentTimeMillis() - pair.getSecond().getTime() > expireTimeMinutes * 60 * 1000) {
                requests.computeIfPresent(pair.getFirst(), (s, requestStatus) -> {
                    if (requestStatus.getStatus().equals(Status.IN_PROGRESS)) {
                        requestStatus.setStatus(Status.ERROR);
                    }
                    return requestStatus;
                });
                return true;
            }
            return false;
        });
    }

    private CrackHashManagerRequest createManagerRequest(String hash,
                                                         int maxLength,
                                                         String id) {
        CrackHashManagerRequest crackHashManagerRequest = new CrackHashManagerRequest();
        crackHashManagerRequest.setHash(hash);
        crackHashManagerRequest.setMaxLength(maxLength);
        crackHashManagerRequest.setRequestId(id);
        crackHashManagerRequest.setPartNumber(1);
        crackHashManagerRequest.setPartCount(1);
        crackHashManagerRequest.setAlphabet(alphabet);
        return  crackHashManagerRequest;
    }
}
