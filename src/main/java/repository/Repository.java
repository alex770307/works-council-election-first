package repository;

import entity.Candidate;
import entity.Participant;
import entity.ResultOfElection;

import java.util.HashMap;
import java.util.Map;

public class Repository implements IRepository{

    private Map<Integer, Participant> participants;
    private Map<String, ResultOfElection> results;

    public Repository() {
        participants = new HashMap<>();
        results = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "participants=" + participants +
                ", results=" + results +
                '}';
    }

    @Override
    public void registrationParticipant(Participant participant) {
        if (participants.containsKey(participant.getId())) {
            System.out.println("Участник с таким ID уже зарегистрирован.");
            return; // Просто выходим из метода
        }
        participants.put(participant.getId(), participant);
    }

    @Override
    public Participant findById(int id) {
        return participants.get(id);
    }

    @Override
    public ResultOfElection findResultByCandidate(Candidate candidate) {
        return results.get(candidate.getId());
    }

    @Override
    public void addResultOfElection(ResultOfElection result) {
        results.put(result.getCandidateName().getId(),result);
    }

    public Map<Integer, Participant> getParticipants() {
        return participants;
    }

    public Map<String, ResultOfElection> getResults() {
        return results;
    }
}
