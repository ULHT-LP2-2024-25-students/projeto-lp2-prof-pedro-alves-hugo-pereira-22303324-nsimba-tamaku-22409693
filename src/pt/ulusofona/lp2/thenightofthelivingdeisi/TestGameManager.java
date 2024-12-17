package pt.ulusofona.lp2.thenightofthelivingdeisi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestGameManager {

    GameManager gameManager = new GameManager();

    // --- Testes de Carregamento de Ficheiro ---
    @Test
    public void testDeveLerComSucessoUmFicheiro7X7() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);
        Assertions.assertTrue(true);
    }

    @Test
    public void testDeveLerComSucessoUmFicheiro7X7EGetSquareInfoDasPecas() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        Assertions.assertEquals("E:-1", gameManager.getSquareInfo(6, 3));
        Assertions.assertEquals("E:-2", gameManager.getSquareInfo(2, 0));
        Assertions.assertEquals("E:-3", gameManager.getSquareInfo(2, 1));
        Assertions.assertEquals("E:-4", gameManager.getSquareInfo(4, 4));
        Assertions.assertEquals("Z:1", gameManager.getSquareInfo(3, 3));
        Assertions.assertEquals("Z:2", gameManager.getSquareInfo(5, 3));
        Assertions.assertEquals("Z:4", gameManager.getSquareInfo(0, 1));
        Assertions.assertEquals("H:6", gameManager.getSquareInfo(3, 4));
        Assertions.assertEquals("SH", gameManager.getSquareInfo(6, 0));
        Assertions.assertEquals("SH", gameManager.getSquareInfo(0, 6));


        String[] arr = gameManager.getCreatureInfo(6);

        Assertions.assertArrayEquals(arr, new String [] {"6","Criança","Humano","Karate Kid","3","4", null});
    }

    @Test
    public void testDeveLancarExcessaoAoLerCaoZombie() throws IOException {
        File file = new File("./test-files/cao_zombie.txt");
        Assertions.assertTrue(file.exists());

        Assertions.assertThrows(InvalidFileException.class, () -> gameManager.loadGame(file));
    }

    @Test
    public void testDeveLancarExcessaoAoLerVampiroHumano() throws IOException {
        File file = new File("./test-files/vampiro_humano.txt");
        Assertions.assertTrue(file.exists());

        Assertions.assertThrows(InvalidFileException.class, () -> gameManager.loadGame(file));
    }

    // --- Testes de Movimento ---
    @Test
    public void testZombieMoveToEmptySquare() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        boolean moveSuccessful = gameManager.move(3, 3, 2, 3); // Zombie 1 se move
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("Z:1", gameManager.getSquareInfo(2, 3));
        Assertions.assertEquals(gameManager.getSquareInfo(3, 3), "");
        Assertions.assertEquals(1, gameManager.getGameSession().getTurnCounter());
    }

    @Test
    public void testHumanMoveToEmptySquare() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        gameManager.move(3, 3, 2, 3); // Zombie move primeiro

        boolean moveSuccessful = gameManager.move(3, 4, 3, 3); // Humano 6 se move
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("H:6", gameManager.getSquareInfo(3, 3));
        Assertions.assertEquals(gameManager.getSquareInfo(3, 4), "");
        Assertions.assertEquals(2, gameManager.getGameSession().getTurnCounter());
    }

    @Test
    public void testMoveToOccupiedSquareFails() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        boolean moveSuccessful = gameManager.move(5, 3, 3, 3); // Tentativa de mover um zombie para posição ocupada por um zombie
        Assertions.assertFalse(moveSuccessful);
        Assertions.assertEquals("Z:2", gameManager.getSquareInfo(5, 3));
        Assertions.assertEquals("Z:1", gameManager.getSquareInfo(3, 3));
    }

    @Test
    public void testTurnOrderBetweenZombiesAndHumans() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        gameManager.move(3, 3, 2, 3); // Zombie 1 move
        Assertions.assertEquals(1, gameManager.getGameSession().getTurnCounter());

        gameManager.move(3, 4, 3, 5); // Humano 6 move
        Assertions.assertEquals(2, gameManager.getGameSession().getTurnCounter());

        gameManager.move(5, 3, 5, 4); // Zombie 2 move
        Assertions.assertEquals(3, gameManager.getGameSession().getTurnCounter());
    }

    // --- Teste de Comportamento Extra ---
    @Test
    public void testDeveVerificarSquareInfoAposMovimentos() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Zombie 1 move de (3,3) para (3,4)
        gameManager.move(3, 3, 3, 4);
        Assertions.assertEquals("Z:6", gameManager.getSquareInfo(3, 4));
        Assertions.assertEquals(gameManager.getSquareInfo(3, 3), "Z:1");

        // Humano 6 move de (3,4) para (3,5)
        gameManager.move(6, 5, 5, 5);
        Assertions.assertEquals("H:9", gameManager.getSquareInfo(5, 5));
        Assertions.assertEquals(gameManager.getSquareInfo(6, 5), "");
    }

    @Test
    public void testZombieDestroiEquipamento() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Zombie 1 move para uma posição com equipamento (Lixívia) e destrói o equipamento
        gameManager.move(5, 3, 4, 4); // Zombie 1 move para pegar o equipamento
        Assertions.assertEquals(gameManager.getSquareInfo(5, 3), ""); // Humano já não está
        Assertions.assertEquals(gameManager.getSquareInfo(4, 4), "Z:2");
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(2), "2 | Adulto | Zombie | Walker | -1 @ (4, 4)");
    }

    @Test
    public void testHumanoAtacaZombie() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_human_first_to_play.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Humano 6 move para a posição do Zombie 1 e ataca
        boolean moveSuccessful = gameManager.move(4, 3, 2, 1); // Humano move para atacar o zombie
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("H:7", gameManager.getSquareInfo(2, 1));
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (2, 1) | -3 | Pistola Walther PPK @ (2, 1) | 3 balas");
        gameManager.move(1, 1, 1, 2); // Zombie move-se
        gameManager.move(2, 1, 1, 2); // humano ataca
        Assertions.assertEquals("H:7", gameManager.getSquareInfo(1, 2)); // Zombie atacado
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (1, 2) | -3 | Pistola Walther PPK @ (1, 2) | 2 balas");
    }


    @Test
    public void testZombieAtacaHumano() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Zombie 1 move para a posição do Humano 6 e ataca
        boolean moveSuccessful = gameManager.move(5, 3, 4, 3); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("Z:2", gameManager.getSquareInfo(5, 3));
        Assertions.assertEquals("Z:7", gameManager.getSquareInfo(4, 3)); // Humano atacado

        Assertions.assertEquals(gameManager.getCreatureInfoAsString(2), "2 | Adulto | Zombie | Walker | -0 @ (5, 3)");
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Zombie (Transformado) | Freddie M. | -0 @ (4, 3)");
    }


    @Test
    public void testZombieAtacaHumanoComEquipamento() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_human_first_to_play.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Humano 6 move para a posição do Zombie 1 e ataca
        boolean moveSuccessful = gameManager.move(4, 3, 2, 1); // Humano move para atacar o zombie
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("H:7", gameManager.getSquareInfo(2, 1));
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (2, 1) | -3 | Pistola Walther PPK @ (2, 1) | 3 balas");
        gameManager.move(1, 1, 1, 2); // Zombie move-se
        gameManager.move(2, 1, 1, 2); // humano ataca
        Assertions.assertEquals("H:7", gameManager.getSquareInfo(1, 2)); // Zombie atacado
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (1, 2) | -3 | Pistola Walther PPK @ (1, 2) | 2 balas");
        Assertions.assertEquals("Z:4", gameManager.getSquareInfo(0, 1));
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(4), "4 | Vampiro | Crawler | -0 @ (0, 1)");
        gameManager.move(0, 1, 1, 2); // Vampiro tenta atacar
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (1, 2) | -3 | Pistola Walther PPK @ (1, 2) | 1 balas");
    }

    @Test
    public void testHumanoTrocaDeEquipamento() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_mais_equipamentos.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Zombie 1 move para a posição do Humano 6 e ataca
        boolean moveSuccessful = gameManager.move(5, 5, 4, 4); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        gameManager.move(1, 1, 1, 0); // Zombie move-se
        gameManager.move(4, 3, 3, 2); // humano move-se
        gameManager.move(1, 0, 1, 1); // Zombie move-se
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(8), "8 | Idoso | Humano | James Bond | +1 @ (4, 4) | -4 | Lixívia @ (4, 4) | 1.0 litros");
        moveSuccessful = gameManager.move(4, 4, 3, 5); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(8), "8 | Idoso | Humano | James Bond | +2 @ (3, 5) | -5 | Escudo de madeira @ (3, 5)");
        Assertions.assertEquals(gameManager.getSquareInfo(4,4), "E:-4");
    }


    @Test
    public void testSafeHeaven() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_human_close_sh.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);
        ArrayList<Integer> a =  new ArrayList<>();
        a.add(9);

        // Zombie 1 move para a posição do Humano 6 e ataca
        boolean moveSuccessful = gameManager.move(6, 1, 6, 0); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("", gameManager.getSquareInfo(6, 1));
        Assertions.assertEquals(a, gameManager.getIdsInSafeHaven()); // Humano atacado

        gameManager.getCreditsPanel();

    }


    @Test
    public void GameIsOver1() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_poucos_zombies.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);


        // Zombie 1 move para a posição do Humano 6 e ataca
        boolean moveSuccessful = gameManager.move(4, 3, 2, 1); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        moveSuccessful = gameManager.move(1, 1, 0, 1); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        gameManager.move(2, 1, 0, 1); // Zombie 1 move para atacar o humano

        Assertions.assertTrue(gameManager.gameIsOver());
    }

    @Test
    public void testHumanoCarregaOEquipamento() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_human_first_to_play.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Humano 6 move para a posição do Zombie 1 e ataca
        boolean moveSuccessful = gameManager.move(4, 3, 2, 1); // Humano move para atacar o zombie
        Assertions.assertTrue(moveSuccessful);
        Assertions.assertEquals("H:7", gameManager.getSquareInfo(2, 1));
        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (2, 1) | -3 | Pistola Walther PPK @ (2, 1) | 3 balas");
        gameManager.move(1, 1, 1, 2); // Zombie move-se
        gameManager.move(2, 1, 3, 1); // humano move

        Assertions.assertEquals(gameManager.getCreatureInfoAsString(7), "7 | Adulto | Humano | Freddie M. | +1 @ (3, 1) | -3 | Pistola Walther PPK @ (3, 1) | 3 balas");
    }

    @Test
    public void testIdosoNaoCarregEquipamento() throws InvalidFileException, IOException {
        File file = new File("./test-files/7x7_mais_equipamentos.txt");
        Assertions.assertTrue(file.exists());
        gameManager.loadGame(file);

        // Zombie 1 move para a posição do Humano 6 e ataca
        boolean moveSuccessful = gameManager.move(5, 5, 4, 4); // Zombie 1 move para atacar o humano
        Assertions.assertTrue(moveSuccessful);
        gameManager.move(1, 1, 1, 0); // Zombie move-se
        gameManager.move(4, 3, 3, 2); // humano move-se
        gameManager.move(1, 0, 1, 1); // Zombie move-se
        moveSuccessful = gameManager.move(4, 4, 5, 5);
        Assertions.assertTrue(moveSuccessful);

        gameManager.getSurvivors();
    }


}
