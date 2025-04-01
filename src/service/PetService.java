package service;

import repository.PetRepository;

public class PetService {

    public static void savePet() {
        for (String question : PetRepository.showForm()) {
            System.out.println(question);
        }
    }
}
