

import java.util.LinkedList;
import java.util.List;

public class KnightDevTools extends PieceDevTools {

    public KnightDevTools(int color, SquareDevTools initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<SquareDevTools> getLegalMoves(BoardDevTools b) {
        LinkedList<SquareDevTools> legalMoves = new LinkedList<SquareDevTools>();
        SquareDevTools[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
