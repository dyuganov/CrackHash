package ru.nsu.dyuganov.CrackHashWorker.service;

import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.List;

public class WorkerResponseBuilder {

    public static CrackHashWorkerResponse buildResponse(String requestId, int partNumber, List<String> answers) {
        CrackHashWorkerResponse.Answers answer = new CrackHashWorkerResponse.Answers();
        answer.getWords().addAll(answers);
        CrackHashWorkerResponse response = new CrackHashWorkerResponse();
        response.setRequestId(requestId);
        response.setPartNumber(partNumber);
        response.setAnswers(answer);
        return response;
    }
}
