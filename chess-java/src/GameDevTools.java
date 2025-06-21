public class GameDevTools implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new StartMenuDevTools());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GameDevTools());
    }
}
