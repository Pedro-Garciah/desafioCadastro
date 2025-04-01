package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
}
