package advancedClasses;

public class Pawn extends Figurine {

    /**
     * Constructs a Pawn with the specified color.
     *
     * @param color The color of the pawn ("w" for white, "b" for black)
     */
    public Pawn (String color){
        this.color=color;
    }


    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        String color = this.getColor();

        // Cannot move to an occupied square
        if (!board.isSquareEmpty(positionFirst, positionSecond)) {
            return false;
        }

        // Calculate the previous position based on color
        int prevPosFirst = calculatePreviousPosition(positionFirst, color);

        // Check if the previous position is valid
        if (prevPosFirst < 0 || prevPosFirst >= board.getBoard().length) {
            return false;
        }

        // Check if there's a pawn in the previous position that can move
        Figurine fig = board.getSquare(prevPosFirst, positionSecond);
        if (fig != null && isValidPawn(fig, color)) {
            move(board, prevPosFirst, positionSecond, positionFirst, positionSecond);
            return true;
        }

        // Check for initial two-square pawn move
        if (isInitPosTarget(positionFirst, positionSecond, color)) {
            int startingPos = calculatePreviousPosition(prevPosFirst, color);
            fig = board.getSquare(startingPos, positionSecond);

            if (fig != null && isValidPawn(fig, color)) {
                // Ensure the square between is also empty
                if (board.isSquareEmpty(prevPosFirst, positionSecond)) {
                    move(board, startingPos, positionSecond, positionFirst, positionSecond);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX==-1&&startingY==-1){
            return isLegalMove(positionFirst,positionSecond,board);
        }
        return false;
    }

    @Override
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {

        if(startingX!=-1){return false;}
        if(startingY==-1){return isLegalCapture(positionFirst,positionSecond,board);}

        String color= this.color;

        if (board.isSquareEmpty(positionFirst,positionSecond) ||
                board.getSquare(positionFirst,positionSecond).color.equals(color)){
            return false;
        }


        if (Math.abs(positionSecond-startingY)!=1){
            return false;
        }

        int positionFirstPrev=0;

        if (color.equals("w")){
            positionFirstPrev =positionFirst+1;
        } else if (color.equals("b")) {
            positionFirstPrev =positionFirst-1;
        } else {
            System.out.println("Invalid color");
            return false;
        }
        Figurine figurine= board.getSquare(positionFirstPrev, startingY);

        if (isValidPawn(figurine,color)){
            board.clearSquare(positionFirst,positionSecond);
            move(board,positionFirstPrev,startingY,positionFirst,positionSecond);
            return true;
        }
        return false;
    }

    /**
     * Checks if the figurine is a valid pawn of the specified color.
     *
     * @param fig The figurine to check
     * @param color The color to check for
     * @return True if the figurine is a pawn of the specified color
     */
    private boolean isValidPawn(Figurine fig, String color) {
        return fig instanceof Pawn && fig.getColor().equals(color);
    }

    /**
     * Checks if the target position is a valid initial two-square move target.
     *
     * @param x The rank (row) position
     * @param y The file (column) position
     * @param c The color of the pawn
     * @return True if the position is a valid initial two-square move target
     */
    private boolean isInitPosTarget(int x, int y ,String c){
        if(c.equals("w")){
            return (x == 4) && (y >= 0 && y < 8);
        }
        else if(c.equals("b")){
            return (x==3)&&(y>=0 && y<8);
        }
        return false;
    }

    /**
     * Calculate the previous position based on color
     * (used for pawn movement logic)
     * @param currentPos Current position
     * @param color Color of the piece
     * @return Previous rank position
     */
    public int calculatePreviousPosition(int currentPos, String color) {
        return color.equals("w") ? currentPos + 1 : currentPos - 1;
    }




}