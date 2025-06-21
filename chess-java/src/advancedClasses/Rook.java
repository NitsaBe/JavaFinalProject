
package advancedClasses;
public class Rook extends Figurine {
    /**
     * Constructs a Rook with the specified color.
     *
     * @param color The color of the rook ("w" for white, "b" for black)
     */
    public Rook(String color) {
        this.color = color;
    }

    // Direction constants
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    // Track if each rook has moved (for castling rules)


    /**
     * Helper method to check if a move is legal in a specific direction.
     *
     * @param positionFirst Target position x
     * @param positionSecond Target position y
     * @param board The chess board
     * @param color The color of the rook
     * @param direction Direction to check
     * @param checkPosX Current check position x
     * @param checkPosY Current check position y
     * @param lastCheckX Last check position x (for disambiguation)
     * @param lastCheckY Last check position y (for disambiguation)
     * @return True if the move is legal
     */
    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board, String color,
                                      int direction, int checkPosX, int checkPosY, int lastCheckX, int lastCheckY) {
        // Check if position is outside board bounds
        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
            return false;
        }

        Figurine figure = board.getSquare(checkPosX, checkPosY);

        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            if (figure instanceof Rook) {
                if (figure.color.equals(color)) {
                    if ((lastCheckX==-1&&lastCheckY==-1)||(lastCheckX == checkPosX && lastCheckY == -1) ||
                            (lastCheckX == -1 && lastCheckY == checkPosY)) {
                        move(board, checkPosX, checkPosY, positionFirst, positionSecond);
                        updateRookMovedStatus(checkPosX, checkPosY , board);
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }

        return switch (direction) {
            case 0 -> // Up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX-1, checkPosY, lastCheckX, lastCheckY);
            case 1 -> // Right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX, checkPosY+1, lastCheckX, lastCheckY);
            case 2 -> // Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX+1, checkPosY, lastCheckX, lastCheckY);
            case 3 -> // Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX, checkPosY-1, lastCheckX, lastCheckY);
            default -> {
                System.out.println("Error: Invalid rook move direction");
                yield false;
            }
        };
    }
    /**
     * Update the status of which rook has moved.
     *
     * @param x The x-coordinate of the moved rook
     * @param y The y-coordinate of the moved rook
     */
    private void updateRookMovedStatus(int x, int y, Board board) {
        if (x == 0 && y == 0) {
            board.setHasMovedBlackY0(true);
        } else if (x == 0 && y == 7) {
            board.setHasMovedBlackY7(true);
        } else if (x == 7 && y == 0) {
            board.setHasMovedWhiteY0(true);
        } else if (x == 7 && y == 7) {
            board.setHasMovedWhiteY7(true);
        }
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        // Check if position is outside board bounds
        if (positionFirst > 7 || positionFirst < 0 || positionSecond > 7 || positionSecond < 0) {
            return false;
        }
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            // Try all 4 directions
            return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, -1, -1);
        } else {
            System.out.println("Error: Target square is not empty");
            return false;
        }
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalMove(positionFirst, positionSecond, board);
        }

        // Handle disambiguated moves
        if (startingY == -1) {
            if (startingX > positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, startingX, startingY);
            } else if (startingX < positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, startingX, startingY);
            }else{
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, startingX, startingY)||
                        isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, startingX, startingY);
            }
        } else {
            if (startingY > positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, startingX, startingY);
            } else if (startingY < positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, startingX, startingY);
            }
            else { return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, startingX, startingY)||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, startingX, startingY);}
        }

//        System.out.println("Error: Invalid rook move parameters");
//        return false;
    }


    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalCapture(positionFirst, positionSecond, board);
        }

        // Cannot capture empty square or same color
        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            return false;
        }

        // Remove piece temporarily to check if move is valid
        Figurine captured = board.getSquare(positionFirst, positionSecond);
        board.setSquare(positionFirst, positionSecond, null);

        boolean isValid = isLegalMove(startingX, startingY, positionFirst, positionSecond, board);

        // Restore piece if move is invalid
        if (!isValid) {
            board.setSquare(positionFirst, positionSecond, captured);
        }

        return isValid;
    }

    /**
     * Check if a specific rook has moved.
     *
     * @param color The color of the rook
     * @param position The position (0 for queenside, 7 for kingside)
     * @return True if the rook has moved
     */
//    public static boolean hasRookMoved(String color, int position) {
//        if (color.equals("w")) {
//            return position == 0 ? hasMovedWhiteY0 : hasMovedWhiteY7;
//        } else {
//            return position == 0 ? hasMovedBlackY0 : hasMovedBlackY7;
//        }
//    }
}