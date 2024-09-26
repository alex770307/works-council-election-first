package service;

public class CheckParticipantStrength implements ICheckParticipantStrength{
    private static final String ID_REGEX = "^\\d{4}$";

    @Override
    public boolean isIdValid(int id) {
        // Преобразуем int в String
       return String.valueOf(id).matches(ID_REGEX);
    }

//
//    private static final String ID_REGEX = "^\\d{4}$";
//    private static final String NAME_REGEX = "^[A-Z][a-z]+ [A-Z][a-z]+$";
//
//    @Override
//    public boolean isIdValid(int id) {
//        // Преобразуем int в String
//        return String.valueOf(id).matches(ID_REGEX);
//    }
//
//    @Override
//    public boolean isNameValid(String name) {
//        return name.matches(NAME_REGEX);
//
//    }
}
