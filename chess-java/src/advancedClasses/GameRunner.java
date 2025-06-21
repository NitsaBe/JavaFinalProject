
package advancedClasses;
import advancedClasses.StringReader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Multi-threaded chess game validator
 */
public class GameRunner {
    private static final Logger LOGGER = Logger.getLogger(GameRunner.class.getName());
    private static final int DEFAULT_TIMEOUT_MINUTES = 10;

    private final String filePath;
    private final int numThreads;
    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);

    /**
     * Constructor with specified thread count
     *
     * @param filePath   Path to PGN file
     * @param numThreads Number of threads to use
     */
    public GameRunner(String filePath, int numThreads) {
        this.filePath = filePath;
        this.numThreads = numThreads;
    }

    /**
     * Constructor using available processors for thread count
     *
     * @param filePath Path to PGN file
     */

    public GameRunner(String filePath) {
        this(filePath, Runtime.getRuntime().availableProcessors());
    }




    /**
     * Start validating chess games
     */
    public void startGame() {
        try {

            // Read and parse PGN file
            String fileContent = StringReader.pgnFileToString(filePath);

            List<String> games = StringReader.separatedAllGamesIntoFullGamesList(fileContent);

            System.out.println("Starting validation of " + games.size() + " games using " + numThreads + " threads");

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            for (int i = 0; i < games.size(); i++) {
                final int gameIndex = i;
                final String gameText = games.get(i);

                executor.submit(() -> processGame(gameIndex, gameText));
            }

            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.err.println("Game processing was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            System.out.println("\n" + "    VALIDATION SUMMARY ");
            System.out.println("Total games processed: " + games.size());
            System.out.println("Successfully validated: " + successCount.get());
            System.out.println("Errors found: " + errorCount.get());

        } catch (IOException e) {
            System.err.println("Error reading PGN file: " + e.getMessage());
        }
    }

    /**
     * Process a single game
     *
     * @param gameIndex Index of the game
     * @param gameText  PGN text of the game
     */

    private void processGame(int gameIndex, String gameText) {
        try {
            // Parse game
            List<List<List<String>>> parsedGame = StringReader.parseGameNotation(gameText);

//            // Reset static flags
//            resetGameFlags();

            // Extract game metadata
            String event = getMetadataValue(parsedGame, "Event");
            String white = getMetadataValue(parsedGame, "White");
            String black = getMetadataValue(parsedGame, "Black");

            // Get moves
            List<List<String>> movePairs = parsedGame.get(1);

            // Create fresh board and validate moves
            Board board = new Board();
//            boolean isValid = GameValidator.validateGameMoves(movePairs, board);
            String isValidText = GameValidator.validateGameMoves(movePairs, board);


            // Update counters and log result
            if (isValidText.isEmpty()) {
                LOGGER.info("Game #" + gameIndex + " - VALID: " + event + " - " + white + " vs " + black);
                System.out.println("Game #" + gameIndex + " - VALID: " + event + " - " + white + " vs " + black);
                successCount.incrementAndGet();
            } else {
                LOGGER.warning("Game #" + gameIndex + " - INVALID: " + event + " - " + white + " vs " + black
                        +" :\n"+isValidText );
                System.out.println("Game #" + gameIndex + " - INVALID: " + event + " - " + white + " vs " + black
                        +" :\n"+isValidText );
                errorCount.incrementAndGet();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing game #" + gameIndex, e);
            System.err.println("Error processing game #" + gameIndex + ": " + e.getMessage());
            errorCount.incrementAndGet();
        }
    }

    /**
     * Reset game flags between validations
     */
//    private void resetGameFlags() {
//        King.hasMovedBlack = false;
//        King.hasMovedWhite = false;
//        Rook.hasMovedBlackY7 = false;
//        Rook.hasMovedBlackY0 = false;
//        Rook.hasMovedWhiteY0 = false;
//        Rook.hasMovedWhiteY7 = false;
//    }

    /**
     * Extract metadata value from parsed game
     * @param parsedGame Parsed game structure
     * @param key Metadata key to look for
     * @return Value for the key, or empty string if not found
     */
    private String getMetadataValue(List<List<List<String>>> parsedGame, String key) {
        for (List<String> metaItem : parsedGame.get(0)) {
            if (metaItem.get(0).equals(key)) {
                return metaItem.get(1);
            }
        }
        return "";
    }


    /**
     * Print summary of validation results
     * @param totalGames Total number of games processed
     */
    private void printSummary(int totalGames) {
        System.out.println("\n    VALIDATION SUMMARY ");
        System.out.println("Total games processed: " + totalGames);
        System.out.println("Successfully validated: " + successCount.get());
        System.out.println("Errors found: " + errorCount.get());

        LOGGER.info("Validation complete. Total: " + totalGames +
                ", Success: " + successCount.get() +
                ", Errors: " + errorCount.get());
    }



}