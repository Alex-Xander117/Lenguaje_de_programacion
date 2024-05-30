import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * HLCO: High Level Concurrency Object
 */
/**
 * Clase que implementa métodos de sincronización de hilos:
 * a) bloques sincronizados
 * b) candados intrínsecos
 * c) variables volátiles
 */
public class GameResourceManagement {
    // Recurso compartido
    private volatile int coins;
    // (c) candado intrínseco:
    private final Lock coinsLock = new ReentrantLock();

    private Map<String, Player> players = new HashMap<>();
    private Map<String, Achievement> achievements = new HashMap<>();

    public GameResourceManagement(int initialCoins) {
        this.coins = initialCoins;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addPlayer(String name) {
        Player player = new Player(name);
        players.put(name, player);
    }

    public void addAchievement(String name, String description, int reward) {
        Achievement achievement = new Achievement(name, description, reward);
        achievements.put(name, achievement);
    }

    // (b) bloque sincronizado:
    public void collectCoins(Player player, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        coinsLock.lock();
        try {
            System.out.println("Player " + player.getName() + " is collecting " + amount + " coins");
            player.addCoins(amount);
            coins += amount;
            System.out.println("Player " + player.getName() + " now has " + player.getCoins() + " coins");
            checkAchievements(player);
        } finally {
            coinsLock.unlock();
        }
    }

    public void spendCoins(Player player, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        coinsLock.lock();
        try {
            if (player.getCoins() >= amount) {
                player.spendCoins(amount);
                coins -= amount;
                System.out.println("Player " + player.getName() + " now has " + player.getCoins() + " coins");
            } else {
                System.out.println("Player " + player.getName() + " cannot spend " + amount + " coins");
            }
        } finally {
            coinsLock.unlock();
        }
    }

    public int getPlayerCoins(Player player) {
        return player.getCoins();
    }

    private void checkAchievements(Player player) {
        for (Achievement achievement : achievements.values()) {
            if (player.getCoins() >= achievement.getReward()) {
                player.addCoins(achievement.getReward());
                System.out.println("Player " + player.getName() + " has earned the achievement: " + achievement.getName());
            }
        }
    }

    public static void main(String[] args) {
        GameResourceManagement game = new GameResourceManagement(0);
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        game.addAchievement("Beginner", "Collect 100 coins", 50);

        Player player1 = game.players.get("Player1");
        Player player2 = game.players.get("Player2");

        game.collectCoins(player1, 100);
        game.spendCoins(player2, 10);
        game.collectCoins(player1, 50);

        System.out.println("Player1 coins: " + game.getPlayerCoins(player1));
        System.out.println("Player2 coins: " + game.getPlayerCoins(player2));
    }
}

class Player {
    private String name;
    private int coins;

    public Player(String name) {
        this.name = name;
        this.coins = 0;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void spendCoins(int amount) {
        this.coins -= amount;
    }
}

class Achievement {
    private String name;
    private String description;
    private int reward;

    public Achievement(String name, String description, int reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReward() {
        return reward;
    }
}