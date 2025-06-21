
package advancedClasses;

public class Bishop extends Figurine {

    public Bishop(String color) {
        this.color = color;
    }
    /**
     * Helper method to check if a bishop can move diagonally in a given direction
     *
     * @param targetX Target X position
     * @param targetY Target Y position
     * @param board Current board state
     * @param color Bishop's color
     * @param direction Direction of diagonal movement (0-3)
     * @param checkPosX Current X position being checked
     * @param checkPosY Current Y position being checked
     * @param lastCheckX Last X position checked (for disambiguated moves)
     * @param lastCheckY Last Y position checked (for disambiguated moves)
     * @return true if move is legal, false otherwise
     */
    private boolean isLegalMoveHelper(int targetX, int targetY, Board board, String color,
                                      int direction, int checkPosX, int checkPosY, int lastCheckX, int lastCheckY) {
        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
            return false;
        }

        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            Figurine figure = board.getSquare(checkPosX, checkPosY);

            // If the piece is a bishop of our color
            if (figure instanceof Bishop && figure.color.equals(color)) {
                // If we're checking with a disambiguation constraint
                if ((lastCheckX==-1&&lastCheckY==-1)||(lastCheckX == checkPosX && lastCheckY == -1) ||
                        (lastCheckX == -1 && lastCheckY == checkPosY)) {

                    move(board, checkPosX, checkPosY, targetX, targetY);
                    return true;
                }
                return false;

            }
            return false; // Path blocked by another piece
        }

        // Continue searching in the specified direction
        return switch (direction) {
            case 0 -> // Up-Left
                    isLegalMoveHelper(targetX, targetY, board, color, direction,
                            checkPosX - 1, checkPosY + 1, lastCheckX, lastCheckY);
            case 1 -> // Up-Right
                    isLegalMoveHelper(targetX, targetY, board, color, direction,
                            checkPosX + 1, checkPosY + 1, lastCheckX, lastCheckY);
            case 2 -> // Down-Right
                    isLegalMoveHelper(targetX, targetY, board, color, direction,
                            checkPosX + 1, checkPosY - 1, lastCheckX, lastCheckY);
            case 3 -> // Down-Left
                    isLegalMoveHelper(targetX, targetY, board, color, direction,
                            checkPosX - 1, checkPosY - 1, lastCheckX, lastCheckY);
            default -> false;
        };
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Boardboard) {
        if (positionFirst > 7 || positionFirst < 0 || positionSecond > 7 || positionSecond < 0) {
            return false;
        }
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst - 1, positionSecond + 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst + 1, positionSecond + 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst + 1, positionSecond - 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst - 1, positionSecond - 1, -1, -1);
        } else {
            System.out.println("Error: Target square is not empty");
            return false;
        }
    }


    @Override
    public boolean isLegalMove(int startingX, int startingY, int targetX, int targetY, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalMove(targetX, targetY, board);
        } else if (startingY == -1) {
            // File disambiguation (startingX specifies the rank)
            if (startingX > targetX) {
                return isLegalMoveHelper(targetX, targetY, board, this.color, 1, targetX, targetY, startingX, startingY) ||
                        isLegalMoveHelper(targetX, targetY, board, this.color, 2, targetX, targetY, startingX, startingY);
            } else if (startingX < targetX) {
                return isLegalMoveHelper(targetX, targetY, board, this.color, 0, targetX, targetY, startingX, startingY) ||
                        isLegalMoveHelper(targetX, targetY, board, this.color, 3, targetX, targetY, startingX, startingY);
            }
        } else {
            // Rank disambiguation (startingY specifies the file)
            if (startingY > targetY) {
                return isLegalMoveHelper(targetX, targetY, board, this.color, 0, targetX, targetY, startingX, startingY) ||
                        isLegalMoveHelper(targetX, targetY, board, this.color, 1, targetX, targetY, startingX, startingY);
            } else if (startingY < targetY) {
                return isLegalMoveHelper(targetX, targetY, board, this.color, 2, targetX, targetY, startingX, startingY) ||
                        isLegalMoveHelper(targetX, targetY, board, this.color, 3, targetX, targetY, startingX, startingY);
            }
        }
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {

        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            System.out.println("Error: Invalid bishop capture - target empty or same color");
            return false;
        }
        if (startingX == -1 && startingY == -1) {
            return isLegalCapture(positionFirst, positionSecond, board);
        }
        board.setSquare(positionFirst, positionSecond, null);
        return isLegalMove(startingX, startingY, positionFirst, positionSecond, board);
    }
}
