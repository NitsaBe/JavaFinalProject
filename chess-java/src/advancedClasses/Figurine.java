package advancedClasses;

public abstract class Figurine {


    /** Color of the piece ("w" for white, "b" for black) */
    String color;


    /**
     * Get the color of this piece
     * @return "w" for white, "b" for black
     */
    public String getColor() {
        return color;
    }

    /**
     * Move a piece on the board
     * @param board The chess board
     * @param fromFirst Source X coordinate
     * @param fromSecond Source Y coordinate
     * @param toFirst Target X coordinate
     * @param toSecond Target Y coordinate
     */
    public static void move(Board board, int fromFirst, int fromSecond, int toFirst, int toSecond) {
        if(board.getSquare(fromFirst,fromSecond)==null){return;}
        Figurine figurine=board.getSquare(fromFirst,fromSecond);
        board.clearSquare(fromFirst, fromSecond);
        board.setSquare(toFirst, toSecond, figurine);
    }
    /**
     * Check if a move to the target position is legal
     * @param positionFirst Target X coordinate
     * @param positionSecond Target Y coordinate
     * @param board Current board state
     * @return true if move is legal, false otherwise
     */
    public abstract boolean isLegalMove(int positionFirst, int positionSecond, Board board);
    /**
     * Check if a move from a specific starting position to target position is legal
     * Used for disambiguated moves in chess notation
     * @param startingX Starting X coordinate (-1 if not specified)
     * @param startingY Starting Y coordinate (-1 if not specified)
     * @param positionFirst Target X coordinate
     * @param positionSecond Target Y coordinate
     * @param board Current board state
     * @return true if move is legal, false otherwise
     */

    public abstract boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board);

    /**
     * Check if a capture at the target position is legal
     * @param positionFirst Target X coordinate
     * @param positionSecond Target Y coordinate
     * @param board Current board state
     * @return true if capture is legal, false otherwise
     */
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {

        if (board.isSquareEmpty(positionFirst,positionSecond)||
                board.getSquare(positionFirst,positionSecond).color.equals(color)){
            return false;
        }

        else {
            board.setSquare(positionFirst,positionSecond,null);
            return isLegalMove(positionFirst, positionSecond, board);
        }

    }

    /**
     * Check if a capture from a specific starting position to target position is legal
     * @param startingX Starting X coordinate (-1 if not specified)
     * @param startingY Starting Y coordinate (-1 if not specified)
     * @param positionFirst Target X coordinate
     * @param positionSecond Target Y coordinate
     * @param board Current board state
     * @return true if capture is legal, false otherwise
     */

    public abstract boolean isLegalCapture(int startingX, int startingY,int positionFirst, int positionSecond, Board board);
}