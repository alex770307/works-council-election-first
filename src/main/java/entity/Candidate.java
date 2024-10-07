package entity;

public enum Candidate {
    Iwan("1"),
    Peter("2"),
    Sidor("3");
    private final String id;

    Candidate(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Candidate fromString(String id) {
        for (Candidate candidate : Candidate.values()) {
            if (candidate.getId().equals(id)) {
                return candidate;
            }
        }
        return null;  // Возвращаем null, если кандидат не найден

    }
}
