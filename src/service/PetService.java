package service;

import domain.Enums.Sex;
import domain.Enums.Type;
import domain.Pet;
import repository.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetService {
    static final String EMPTY_INFO = "NAO INFORMADO";
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
        List<String> answers = new ArrayList<>();

        for (String question : PetRepository.showForm()) {
            System.out.println(question);

            if (question.startsWith("4 -")) {
                answers.add(validateAddress());
                continue;
            }

            answers.add(sc.nextLine().trim());
        }

        Pet pet = new Pet();

        pet.setName(validateName(answers.get(0)));
        pet.setType(validatePetType(answers.get(1)));
        pet.setSex(validatePetSex(answers.get(2)));
        pet.setAddress(answers.get(3));
        pet.setAge(validateAge(answers.get(4)));
        pet.setWeight(validateWeight(answers.get(5)));
        pet.setRace(validateRace(answers.get(6)));

        PetRepository.savePet(pet, answers);
    }

    public static String validateAddress() {
        System.out.println("Qual o número da casa?");
        int number = Integer.parseInt(sc.nextLine());

        System.out.println("Qual a cidade?");
        String city = sc.nextLine();

        System.out.println("Qual a rua?");
        String street = sc.nextLine();

        return street + ", " + number + ", " + city;
    }

    public static String validateName(String text) {
        if (text.isEmpty())
            return EMPTY_INFO;

        if (text.matches("([a-zA-Z]+ +)+[a-zA-Z]+"))
            return text;
        else throw new IllegalArgumentException("Digite um nome e sobrenome válidos.");
    }

    public static Type validatePetType(String text) {
        if (text.trim().equalsIgnoreCase("gato"))
            return Type.GATO;
        if (text.trim().equalsIgnoreCase("cachorro"))
            return Type.CACHORRO;
        else
            throw new IllegalArgumentException("Selecione um tipo válido (Cachorro ou Gato)");
    }

    public static Sex validatePetSex(String text) {
        if (text.trim().equalsIgnoreCase("macho"))
            return Sex.MALE;
        if (text.trim().equalsIgnoreCase("femea")) {
            return Sex.FEMALE;
        } else
            throw new IllegalArgumentException("Selecione um sexo válido (Macho ou Femea)");
    }

    public static String validateWeight(String text) {
        if (text.isEmpty())
            return EMPTY_INFO;

        if (text.contains(","))
            text = text.replace(",", ".");

        double weight = Double.parseDouble(text);

        if (weight > 60 || weight < 0.5)
            throw new IllegalArgumentException("Digite um peso válido (entre 0,5kg e 60kg");

        return String.valueOf(weight);
    }

    public static String validateAge(String text) {
        if (text.isEmpty())
            return EMPTY_INFO;

        int age = Integer.parseInt(text);
        if (age > 20)
            throw new IllegalArgumentException("Digite uma idade válida(menor que 20)");

//        if (age<1)
//            age = 0.5 //
        return String.valueOf(age);
    }

    public static String validateRace(String text) {
        if (text.isEmpty())
            return EMPTY_INFO;

        if (text.matches("([a-zA-Z]+ +)*[a-zA-Z]+"))
            return text;

        throw new IllegalArgumentException("A raça não pode conter caracteres especiais.");
    }
}
