package pt.ulusofona.lp2.thenightofthelivingdeisi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TestGameManager {


//    @Test
//    public void testDeveLerComSucessoUmFicheiro5X5() {
//        File file = new File("./test-files/5x5.txt");
//        Assertions.assertTrue(file.exists());
//        GameManager gameManager = new GameManager();
//        boolean carregou = gameManager.loadGame(file);
//        Assertions.assertTrue(carregou);
//    }

    GameManager gameManager = new GameManager();

    @Test
    public void testDeveLerComSucessoUmFicheiroComCaoZombie() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_v2.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);
        Assertions.assertTrue(true);
    }
    @Test
    public void testDeveLerComSucessoUmFicheiro7X7() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);
        Assertions.assertTrue(true);
    }



    @Test
    public void testDeveLancarExcessaoAoLerCaoZombie() throws FileNotFoundException {
        File file = new File("./test-files/cao_zombie.txt");
        Assertions.assertTrue(file.exists());

        GameManager gameManager = new GameManager();

        // Verifica se a exceção InvalidFileException é lançada ao carregar o ficheiro
        Assertions.assertThrows(InvalidFileException.class, () -> gameManager.loadGame(file));
    }

    @Test
    public void testDeveLancarExcessaoAoLerVampiroHumano() throws FileNotFoundException {
        File file = new File("./test-files/vampiro_humano.txt");
        Assertions.assertTrue(file.exists());

        GameManager gameManager = new GameManager();

        // Verifica se a exceção InvalidFileException é lançada ao carregar o ficheiro
        Assertions.assertThrows(InvalidFileException.class, () -> gameManager.loadGame(file));
    }

//
//    @Test
//    public void testDeveMoverUmHumanoComSucesso() {
//        File file = new File("./test-files/5x5.txt");
//        Assertions.assertTrue(file.exists());
//        GameManager gameManager = new GameManager();
//        boolean carregou = gameManager.loadGame(file);
//        Assertions.assertTrue(carregou);
//        Assertions.assertTrue(gameManager.move(0,0, 1,0));
//    }
//
//    @Test
//    public void testDeveDevePermitirOHumanoEquipar_Se() {
//        File file = new File("./test-files/5x5.txt");
//        Assertions.assertTrue(file.exists());
//        GameManager gameManager = new GameManager();
//        boolean carregou = gameManager.loadGame(file);
//        Assertions.assertTrue(carregou);
//        Assertions.assertTrue(gameManager.move(0,0, 1,0));
//        Assertions.assertEquals(true, gameManager.hasEquipment(1, 0));
//    }
}
