package repository;

import domain.Pet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
}
