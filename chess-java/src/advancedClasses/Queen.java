
package advancedClasses;
public class Queen extends Figurine {
    /**
     * Constructs a Queen with the specified color.
     *
     * @param color The color of the queen ("w" for white, "b" for black)
     */

    public Queen(String color) {
        this.color = color;
    }
    /**
     * Helper method to check if a move is legal in a specific direction.
     *
     * @param positionFirst Target position x
     * @param positionSecond Target position y
     * @param board The chess board
     * @param color The color of the queen
     * @param checkPosX Current check position x
     * @param checkPosY Current check position y
     * @param direction Direction to check
     * @return True if the move is legal
     */
    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board, String color,
                                      int checkPosX, int checkPosY, int direction) {
        // Check board boundaries
        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
//            System.out.println("Error: Queen move out of board bounds");
            return false;
        }

        Figurine figure = board.getSquare(checkPosX, checkPosY);

        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            if (figure instanceof Queen) {
                if (figure.color.equals(color)) {
                    move(board, checkPosX, checkPosY, positionFirst, positionSecond);
                    return true;
                }
            }
//            System.out.println("Error: Queen path blocked by opponent piece");
            return false;
        }

        return switch (direction) {
            case 0 -> // Up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX, checkPosY - 1, direction);
            case 1 -> // Up-Right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY - 1, direction);
            case 2 -> // Right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY, direction);
            case 3 -> // Right-Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY + 1, direction);
            case 4 -> // Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX, checkPosY + 1, direction);
            case 5 -> // Down-Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY + 1, direction);
            case 6 -> // Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY, direction);
            case 7 -> // Left-Up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY - 1, direction);
            default -> {
                System.out.println("Error: Invalid queen move direction");
                yield false;
            }
        };
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            for (int direction = 0; direction < 8; direction++) {
                if (isLegalMoveHelper(positionFirst, positionSecond, board, this.color,
                        positionFirst, positionSecond, direction)) {
                    return true;
                }
            }
        } else {
            System.out.println("Error: Queen target square is not empty");
        }
        return false;
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX==-1&&startingY==-1){
            return isLegalMove(positionFirst,positionSecond,board);
        }
        System.out.println("Error: Queen disambiguated moves not implemented");
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            System.out.println("Error: Invalid queen capture - target empty or same color");
            return false;
        }
        // Temporarily remove captured piece for move validation
        Figurine captured = board.getSquare(positionFirst, positionSecond);
        board.setSquare(positionFirst, positionSecond, null);
        boolean isValid = isLegalMove(startingX, startingY, positionFirst, positionSecond, board);
        // Restore captured piece if move was invalid
        if (!isValid) {
            board.setSquare(positionFirst, positionSecond, captured);
        }
        return isValid;
    }
}