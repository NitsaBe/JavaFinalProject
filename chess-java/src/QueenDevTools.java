

import java.util.LinkedList;
import java.util.List;

public class QueenDevTools extends PieceDevTools {

    public QueenDevTools(int color, SquareDevTools initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<SquareDevTools> getLegalMoves(BoardDevTools b) {
        LinkedList<SquareDevTools> legalMoves = new LinkedList<SquareDevTools>();
        SquareDevTools[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        int[] occups = getLinearOccupations(board, x, y);
        
        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }
        
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }
        
        List<SquareDevTools> bMoves = getDiagonalOccupations(board, x, y);
        
        legalMoves.addAll(bMoves);
        
        return legalMoves;
    }
    
}
