

import java.util.LinkedList;
import java.util.List;

public class KingDevTools extends PieceDevTools {

    public KingDevTools(int color, SquareDevTools initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<SquareDevTools> getLegalMoves(BoardDevTools b) {
LinkedList<SquareDevTools> legalMoves = new LinkedList<SquareDevTools>();
        
        SquareDevTools[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied() || 
                                board[y + k][x + i].getOccupyingPiece().getColor() 
                                != this.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
