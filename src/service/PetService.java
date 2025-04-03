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
                case 2 -> modifyPet();
                case 3 -> deletePet();
                case 4 -> showAllPets();
                case 5 -> {
                    List<String> results = searchPet();
                    System.out.println("=================");
                    for (String result : results) {
                        System.out.print(result);
                    }
                    System.out.println("=================");
                }
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

    public static List<String> searchPet() {
        String date = "";

        System.out.println("Qual o tipo de animal? (Gato/Cachorro)");
        String type = sc.nextLine();

        System.out.println("""
                Critérios:
                1 - Nome ou sobrenome
                2 - Sexo
                3 - Endereço
                4 - Idade
                5 - Peso
                6 - Raça""");

        System.out.println("Qual o primeiro critério que deseja utilizar na busca?");
        int option1 = Integer.parseInt(sc.nextLine());

        System.out.println("Qual o segundo critério que deseja utilizar na busca? (Deixe em branco caso não queira utilizar)");
        String option2String = sc.nextLine();

        System.out.println("Deseja buscar por Data de Cadastro?(S/N)");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            System.out.println("Digite o numero do mes");
            String month = sc.nextLine();
            if (Integer.parseInt(month) < 10) month = "0" + month;

            System.out.println("Digite o ano");
            String year = sc.nextLine();

            date = year + month;
        }
        searchSwitch(option1);
        String search1 = sc.nextLine().trim();

        if (!option2String.isEmpty()) {
            int option2 = Integer.parseInt(option2String);
            searchSwitch(option2);
            String search2 = sc.nextLine().trim();

            return PetRepository.searchPet(date, option1, option2, search1, search2, type);
        }

        return PetRepository.searchPet(date, option1, search1, type);
    }

    private static void searchSwitch(int option) {
        switch (option) {
            case 1 -> System.out.print("Digite o nome do pet desejado: ");
            case 2 -> System.out.print("Digite o sexo do pet desejado: ");
            case 3 -> System.out.print("Digite o endereço do pet desejado: ");
            case 4 -> System.out.print("Digite a idade do pet desejado: ");
            case 5 -> System.out.print("Digite o peso do pet desejado: ");
            case 6 -> System.out.print("Digite a raça do pet desejado: ");
        }
    }

    private static void showAllPets(){
        List<String> allResults = PetRepository.showAllPets();

        System.out.println("=================");
        for (String result : allResults) {
            System.out.print(result);
        }
        System.out.println("=================");
    }

    private static void modifyPet() {
        System.out.println("------------\nAlterando Pet:");
        List<String> results = searchPet();

        System.out.println("=================");
        for (String result : results) {
            System.out.print(result);
        }
        System.out.println("=================");

        System.out.println("Qual Pet deseja modificar?(Digite o numero da lista de respostas)");
        int petId = Integer.parseInt(sc.nextLine()) - 1;

        String petToModify = results.get(petId);

        PetRepository.modifyPet(petToModify);
    }

    public static int selectCharacteristic() {
        System.out.println("""
                Qual característica deseja alterar?
                1 - Nome
                2 - Endereço
                3 - Idade
                4 - Peso
                5 - Raça""");
        int op = Integer.parseInt(sc.nextLine());
        infoSwitch(op);
        op--;
        if (op >= 2) op += 2;
        return op;
    }

    private static void infoSwitch(int option) {
        switch (option) {
            case 1 -> System.out.print("Digite o novo nome do pet desejado: ");
            case 2 -> System.out.print("Digite o novo endereço do pet desejado: ");
            case 3 -> System.out.print("Digite a nova idade do pet desejado: ");
            case 4 -> System.out.print("Digite o novo peso do pet desejado: ");
            case 5 -> System.out.print("Digite a nova raça do pet desejado: ");
        }
    }

    private static void deletePet() {
        System.out.println("------------\nDeletando Pet:");
        List<String> results = searchPet();
        int petId;


        while (true) {
            System.out.println("=================");
            for (String result : results) {
                System.out.print(result);
            }
            System.out.println("=================");

            System.out.println("Qual Pet deseja deletar?(Digite o numero da lista de respostas)");
            petId = Integer.parseInt(sc.nextLine()) - 1;

            if (petId > 0 && petId < results.size() - 1)
                break;
        }

        System.out.println("Realmente deseja deletar este Pet?(S/N)");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("s")) {
            String petToDelete = results.get(petId);
            PetRepository.deletePet(petToDelete);
            System.out.println("Pet deletado com sucesso.");
        }
    }
}
