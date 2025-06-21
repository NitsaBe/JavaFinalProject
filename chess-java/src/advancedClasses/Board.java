
package advancedClasses;
public class Board {

//        [
//            [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites
//            ]

    /**
     * Represents a chess board with 8x8 squares.
     * Board coordinates are represented as follows:
     * [0][0] = a8 (top-left, black's side)
     * [7][7] = h1 (bottom-right, white's side)
     */


    private Figurine[][] board;
    private  boolean hasMovedWhite = false;    //KING
    private  boolean hasMovedBlack = false;    //KING

    private   boolean hasMovedWhiteY0 = false;
    private  boolean hasMovedWhiteY7 = false;
    private  boolean hasMovedBlackY0 = false;
    private  boolean hasMovedBlackY7 = false;

    public void setHasMovedBlackY7(boolean hasMovedBlackY7) {
        this.hasMovedBlackY7 = hasMovedBlackY7;
    }

    public void setHasMovedBlackY0(boolean hasMovedBlackY0) {
        this.hasMovedBlackY0 = hasMovedBlackY0;
    }

    public void setHasMovedWhiteY7(boolean hasMovedWhiteY7) {
        this.hasMovedWhiteY7 = hasMovedWhiteY7;
    }

    public void setHasMovedWhiteY0(boolean hasMovedWhiteY0) {
        this.hasMovedWhiteY0 = hasMovedWhiteY0;
    }

    public boolean isHasMovedWhiteY0() {
        return hasMovedWhiteY0;
    }

    public boolean isHasMovedWhiteY7() {
        return hasMovedWhiteY7;
    }

    public boolean isHasMovedBlackY0() {
        return hasMovedBlackY0;
    }

    public boolean isHasMovedBlackY7() {
        return hasMovedBlackY7;
    }

    public boolean isHasMovedWhite() {
        return hasMovedWhite;
    }

    public boolean isHasMovedBlack() {
        return hasMovedBlack;
    }

    public  void setHasMovedBlack(boolean hasMovedBlack) {
        this.hasMovedBlack = hasMovedBlack;
    }

    public  void setHasMovedWhite(boolean hasMovedWhite) {
        this.hasMovedWhite = hasMovedWhite;
    }

    public Figurine[][] getBoard() {
        return board;
    }

    /**
     * Get the piece at a specific position
     *
     * @param positionX X coordinate (0-7)
     * @param positionY Y coordinate (0-7)
     * @return Figurine at the position, or null if empty
     */
    public Figurine getSquare(int positionX, int positionY) {
        return this.getBoard()[positionX][positionY];
    }

    /**
     * Set a piece at a specific position
     *
     * @param positionX X coordinate (0-7)
     * @param positionY Y coordinate (0-7)
     * @param figurine  Piece to place, or null to clear
     */

    public void setSquare(int positionX, int positionY, Figurine figurine) {
        this.getBoard()[positionX][positionY] = figurine;
    }

    /**
     * Get a piece using algebraic notation
     *
     * @param positionX File (a-h)
     * @param positionY Rank (1-8)
     * @return Figurine at the position, or null if empty
     */
    public Figurine getSquare(String positionX, int positionY) {
        int posX = 0;
        int posY = 0;
        switch (positionX) {
            case "a": {
                break;
            }
            case "b": {
                posY = 1;
                break;
            }
            case "c": {
                posY = 2;
                break;
            }
            case "d": {
                posY = 3;
                break;
            }
            case "e": {
                posY = 4;
                break;
            }
            case "f": {
                posY = 5;
                break;
            }
            case "g": {
                posY = 6;
                break;
            }
            case "h": {
                posY = 7;
                break;
            }
        }
        posX = 8 - positionY;
        return getSquare(posX, posY);
    }

    /**
     * Check if a square is empty
     *
     * @param positionX X coordinate (0-7)
     * @param positionY Y coordinate (0-7)
     * @return true if square is empty, false otherwise
     */
    public boolean isSquareEmpty(int positionX, int positionY) {
        return this.getSquare(positionX, positionY) == null;
    }

    /**
     * Clear a square (set to null)
     *
     * @param positionX X coordinate (0-7)
     * @param positionY Y coordinate (0-7)
     */
    public void clearSquare(int positionX, int positionY) {
        this.getBoard()[positionX][positionY] = null;
    }


    /**
     * Constructor - initializes a board with pieces in starting positions
     */

    public Board() {

        board = new Figurine[8][8];
        // Set up pawns
        for (int x = 0; x < 8; x++) {
            board[1][x] = new Pawn("b"); // Black pawns
            board[6][x] = new Pawn("w"); // White pawns
        }

        // Set up queens
        board[7][3] = new Queen("w");
        board[0][3] = new Queen("b");

        // Set up kings
        board[7][4] = new King("w");
        board[0][4] = new King("b");

        // Set up rooks
        board[7][0] = new Rook("w");
        board[7][7] = new Rook("w");
        board[0][0] = new Rook("b");
        board[0][7] = new Rook("b");

        // Set up knights
        board[7][1] = new Knight("w");
        board[7][6] = new Knight("w");
        board[0][1] = new Knight("b");
        board[0][6] = new Knight("b");

        // Set up bishops
        board[0][2] = new Bishop("b");
        board[0][5] = new Bishop("b");
        board[7][2] = new Bishop("w");
        board[7][5] = new Bishop("w");

    }


    public void clearBoard(){
        for(int i = 0 ; i<this.getBoard().length ; i++){
            for(int p = 0 ; p<this.getBoard().length ; p++){
                this.clearSquare(i,p);
            }
        }
    }
}