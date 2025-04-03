package repository;

import domain.Pet;
import service.PetService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetRepository {

    public static List<String> showForm() {
        Path form = Paths.get("formulario.txt");

        List<String> questions;

        try {
            questions = Files.readAllLines(form);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    public static void savePet(Pet pet, List<String> answers) {

        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateTime = ldt.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));
        String formattedPetName = pet.getName().toUpperCase().trim().replaceAll(" +", "");

        Path path = Paths.get("petsCadastrados/" + formattedDateTime + "-" + formattedPetName + ".txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            if (Files.notExists(path))
                Files.createFile(path);

            int contador = 1;

            for (String answer : answers) {
                writer.write(contador + " - " + answer);
                writer.newLine();
                contador++;
            }
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> showAllPets() {
        Path path = Paths.get("petsCadastrados/");
        List<String> results = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path path1 : stream) {
                List<String> data = Files.readAllLines(path1);
                int count = 1;
                results.add(String.format("%d. %s - %s - %s - %s - %s - %s - %s\n",
                        count, data.get(0).substring(4), data.get(1).substring(4),
                        data.get(2).substring(4), data.get(3).substring(4),
                        data.get(4).substring(4), data.get(5).substring(4),
                        data.get(6).substring(4)));
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    public static List<String> searchPet(String date, int option, String search, String type) {
        Path path = Paths.get("petsCadastrados/");
        List<String> searchResults = new ArrayList<>();


        if (option >= 2) option++; //Pulando o Campo Tipo

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            int count = 1;
            for (Path path1 : stream) {
                if (path1.getFileName().toString().startsWith(date)) {
                    List<String> data = Files.readAllLines(path1);

                    if (data.get(option - 1).toLowerCase().contains(search.toLowerCase()) &&
                            data.get(1).toLowerCase().contains(type.toLowerCase())) {

                        searchResults.add(String.format("%d. %s - %s - %s - %s - %s - %s - %s\n",
                                count, data.get(0).substring(4), data.get(1).substring(4),
                                data.get(2).substring(4), data.get(3).substring(4),
                                data.get(4).substring(4), data.get(5).substring(4),
                                data.get(6).substring(4)));

                        count++;
                    }
                }
            }
            return searchResults;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> searchPet(String date, int option1, int option2, String search1, String search2, String type) {
        Path path = Paths.get("petsCadastrados/");
        List<String> searchResults = new ArrayList<>();

        if (option1 >= 2) option1++; //Pulando o Campo Tipo
        if (option2 >= 2) option2++;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            int count = 1;
            for (Path path1 : stream) {
                if (path1.getFileName().toString().startsWith(date)) {
                    List<String> data = Files.readAllLines(path1);

                    if (data.get(option1 - 1).toLowerCase().contains(search1.toLowerCase()) &&
                            data.get(1).toLowerCase().contains(type.toLowerCase()) &&
                            data.get(option2 - 1).toLowerCase().contains(search2.toLowerCase())) {

                        searchResults.add(String.format("%d. %s - %s - %s - %s - %s - %s - %s\n",
                                count, data.get(0).substring(4), data.get(1).substring(4),
                                data.get(2).substring(4), data.get(3).substring(4),
                                data.get(4).substring(4), data.get(5).substring(4),
                                data.get(6).substring(4)));

                        count++;
                    }
                }
            }
            return searchResults;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modifyPet(String petToModify) {
        Path path = Paths.get("petsCadastrados/");
        Scanner sc = new Scanner(System.in);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path path1 : stream) {

                List<String> data = Files.readAllLines(path1);

                if (isPetEqual(petToModify, data)) {
                    int op = PetService.selectCharacteristic();
                    String newData = sc.nextLine().trim();

                    data.set(op, ++op + " - " + newData);

                    String oldFileDate = path1.getFileName().toString().substring(0, 13);
                    String formattedPetName = data.getFirst().substring(4).toUpperCase().trim().replaceAll(" +", "");

                    Path newFile = Paths.get("petsCadastrados/" + oldFileDate + "-" + formattedPetName + ".txt");

                    if (Files.notExists(newFile)) {
                        Files.createFile(newFile);
                        Files.delete(path1);
                    }

                    BufferedWriter writer = Files.newBufferedWriter(newFile);

                    int contador = 1;
                    for (String dataLine : data) {
                        writer.write(dataLine);
                        writer.newLine();
                        contador++;
                    }
                    writer.close();

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPetEqual(String petToModify, List<String> data) {
        petToModify = petToModify.substring(3);

        String petTxt = String.format("%s - %s - %s - %s - %s - %s - %s\n", data.get(0).substring(4), data.get(1).substring(4),
                data.get(2).substring(4), data.get(3).substring(4),
                data.get(4).substring(4), data.get(5).substring(4),
                data.get(6).substring(4));

        return petToModify.trim().equalsIgnoreCase(petTxt.trim());
    }
}
