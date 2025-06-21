

import java.util.List;
public class BishopDevTools extends PieceDevTools {

    public BishopDevTools(int color, SquareDevTools initSq, String img_file) {
        super(color, initSq, img_file);
    }
    
    @Override
    public List<SquareDevTools> getLegalMoves(BoardDevTools b) {
        SquareDevTools[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        return getDiagonalOccupations(board, x, y);
    }
}
