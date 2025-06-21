package advancedClasses;

public class King  extends Figurine {
    /**
     * Constructs a King with the specified color.
     *
     * @param color The color of the king ("w" for white, "b" for black)
     */
    public King(String color) {
        this.color = color;
    }
    // Track if each king has moved (for castling rules)

    // Possible king moves in all 8 directions
    private static final int[][] KING_MOVES = {
            {-1, 0}, {-1, +1}, {0, +1}, {+1, +1},
            {+1, 0}, {+1, -1}, {0, -1}, {-1, -1}
    };

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (!board.isSquareEmpty(positionFirst, positionSecond)) {
            return false;
        }

        String color = this.getColor();

        // Check all possible king moves
        for (int[] move : KING_MOVES) {
            int checkX = positionFirst + move[0];
            int checkY = positionSecond + move[1];

            // Validate board boundaries
            if (checkX < 0 || checkX >= 8 || checkY < 0 || checkY >= 8) {
                continue;
            }

            Figurine figurine = board.getSquare(checkX, checkY);

            // Skip if no piece or different color piece
            if (figurine == null || !figurine.getColor().equals(color)) {
                continue;
            }

            // If there's a king of the same color at this position
            if (figurine instanceof King && figurine.getColor().equals(color)) {
                move(board, checkX, checkY, positionFirst, positionSecond);

                // Update has moved status
                if (color.equals("w")) {
                    board.setHasMovedWhite(true);
                } else if (color.equals("b")) {
                    board.setHasMovedBlack(true);
                } else {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if(startingX==-1&&startingY==-1){
            return isLegalMove(positionFirst,positionSecond,board);
        }
        return false;
    }


    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if(startingX==-1&&startingY==-1){
            return isLegalCapture(positionFirst,positionSecond,board);
        }
        return false;
    }
    /**
     * Check if a king of the specified color has moved.
     *
     * @param color The color to check ("w" for white, "b" for black)
     * @return True if the king has moved, false otherwise
     */
//    public static boolean hasKingMoved(String color) {
//        return color.equals("w") ? hasMovedWhite : hasMovedBlack;
//    }
}