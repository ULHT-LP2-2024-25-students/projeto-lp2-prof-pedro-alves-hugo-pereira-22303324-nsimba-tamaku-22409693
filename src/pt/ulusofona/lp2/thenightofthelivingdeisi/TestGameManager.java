package pt.ulusofona.lp2.thenightofthelivingdeisi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


public class TestGameManager {


    @Test
    public void deveLerComSucessoUmFicheiro5X5() {
        File file = new File("./test-files/5x6.txt");
        Assertions.assertTrue(file.exists());
        GameManager gameManager = new GameManager();
        gameManager.loadGame(file);



    }
}
