package service;

import repository.PetRepository;

import java.util.Scanner;

public class PetService {
    static Scanner sc = new Scanner(System.in);

    public static void menu() {
        boolean showMenu = true;

        while (showMenu) {
            System.out.print(
                    """
                            1. Cadastrar um novo pet
                            2. Alterar os dados do pet cadastrado
                            3. Deletar um pet cadastrado
                            4. Listar todos os pets cadastrados
                            5. Listar pets por algum critério (idade, nome, raça)
                            6. Sair
                            >>\s""");

            // Lançar exceção customizada para inputs incorretos
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1 -> savePet();
                case 2 -> System.out.println("case 2");
                case 3 -> System.out.println("case 3");
                case 4 -> System.out.println("case 4");
                case 5 -> System.out.println("case 5");
                case 6 -> {
                    System.out.println("Encerrando programa...");
                    showMenu = false;
                }
            }
        }
    }

    public static void savePet() {
        for (String question : PetRepository.showForm()) {
            System.out.println(question);
        }
    }
}
