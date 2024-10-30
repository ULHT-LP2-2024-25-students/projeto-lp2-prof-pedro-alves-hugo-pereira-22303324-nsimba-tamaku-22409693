package pt.ulusofona.lp2.thenightofthelivingdeisi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


public class TestGameManager {


    @Test
    public void deveLerComSucessoUmFicheiro5X5() {
        File file = new File("./test-files/5x5.txt");
        Assertions.assertTrue(file.exists());
        GameManager gameManager = new GameManager();
        boolean carregou = gameManager.loadGame(file);
        Assertions.assertTrue(carregou);
    }

    @Test
    public void deveLerSemSucessoUmFicheiro5X5() {
        File file = new File("./test-files/5x5_com_erro.txt");
        Assertions.assertTrue(file.exists());
        GameManager gameManager = new GameManager();
        boolean carregou = gameManager.loadGame(file);
        Assertions.assertFalse(carregou);
    }

    @Test
    public void deveDeveMoverUmHumanoComSucesso() {
        File file = new File("./test-files/5x5.txt");
        Assertions.assertTrue(file.exists());
        GameManager gameManager = new GameManager();
        boolean carregou = gameManager.loadGame(file);
        Assertions.assertTrue(carregou);
        Assertions.assertTrue(gameManager.move(0,0, 0,1));
    }
}
