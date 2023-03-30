package ru.nsu.dyuganov.CrackHashManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import ru.nsu.dyuganov.CrackHashManager.model.RequestStatus;

import java.security.Timestamp;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@EnableScheduling
public class CrackHashService {

    private final Map<String, RequestStatus> requests = new ConcurrentHashMap<>();



}
