package controller;

import entity.Candidate;
import repository.IRepository;
import repository.Repository;
import service.IService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Controller implements IController {
    private Scanner scanner;
    private IService service;
    private IRepository repository;
    private boolean isParticipantRegistered = false;  // Флаг для отслеживания регистрации

    public Controller(Scanner scanner, IService service, IRepository repository) {
        this.scanner = scanner;
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void startInteraction() {
        // Пример взаимодействия с пользователем
        while (true) {
            // Регистрация участника
            System.out.println("Введите ID участника для регистрации (или 'exit' для выхода):");
            String idInput = scanner.nextLine();
            if (idInput.equalsIgnoreCase("exit")) {
                System.out.println("Зарегистрированные участники: " + ((Repository) repository).getParticipants());
                System.out.println("Результаты выборов: " + ((Repository) repository).getResults());
                break;// Выход из программы
            }
            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: ID должен быть числом. Пожалуйста, попробуйте еще раз.");
                continue; // Повторяем ввод
            }
            System.out.println("Введите имя и фамилию участника:");
            String name = scanner.nextLine(); // Считываем имя и фамилию как одну строку
            // Проверка на существующего участника
            if (service.isParticipantRegistered(id)){
                System.out.println("Участник с таким ID уже зарегистрирован.");
                continue; // Возвращаемся к регистрации
            }
            // Регистрация нового участника
            if (service.registrationParticipant(id, name)) {
                isParticipantRegistered = true;  // Успешная регистрация
                System.out.println("Участник зарегистрирован!");
            } else {
                System.out.println("Ошибка регистрации участника. Пожалуйста, попробуйте снова.");
                isParticipantRegistered = false;  // Невозможность голосовать
                continue; // Возвращаемся к регистрации
            }
            // Процесс голосования
            if (isParticipantRegistered) {
                System.out.println("Введите имена кандидатов для голосования через пробел (максимум 2 кандидата):");
                String candidateInput = scanner.nextLine();
                String[] candidateNames = candidateInput.split(" "); // Разделяем по пробелам

                // Проверка на ограничение по количеству кандидатов и уникальность
                if (candidateNames.length > 2) {
                    System.out.println("Ошибка: Вы можете выбрать максимум 2 кандидата.");
                    continue; // Возвращаемся к процессу выбора
                }

                Set<Candidate> selectedCandidates = new HashSet<>(); // Используем HashSet для уникальности
                for (String candidateName : candidateNames) {
                    Candidate candidate = Candidate.fromString(candidateName);
                    if (candidate != null) {
                        selectedCandidates.add(candidate);
                    } else {
                        System.out.println("Неверное имя кандидата: " + candidateName);
                    }
                }

                // Проверка, выбраны ли два уникальных кандидата
                if (selectedCandidates.size() > 2) {
                    System.out.println("Ошибка: Выберите максимум 2 уникальных кандидата.");
                } else if (!selectedCandidates.isEmpty()) {
                    for (Candidate candidate : selectedCandidates) {
                        service.electionFromCandidates(candidate, 1);
                    }
                    System.out.println("Голос(а) за кандидатов " + selectedCandidates + " зарегистрирован(ы)!");
                } else {
                    System.out.println("Необходимо выбрать хотя бы одного кандидата для голосования.");
                }
            }
            // Отображение текущего состояния репозитория
            System.out.println("Зарегистрированные участники: " + ((Repository) repository).getParticipants());
            System.out.println("Результаты выборов: " + ((Repository) repository).getResults());

        }
    }
}
