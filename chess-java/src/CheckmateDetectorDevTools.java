

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Component of the Chess game that detects check mates in the game.
 *
 * @author Jussi Lundstedt
 *
 */
public class CheckmateDetectorDevTools {
    private BoardDevTools b;
    private LinkedList<PieceDevTools> wPieces;
    private LinkedList<PieceDevTools> bPieces;
    private LinkedList<SquareDevTools> movableSquares;
    private final LinkedList<SquareDevTools> squares;
    private KingDevTools bk;
    private KingDevTools wk;
    private HashMap<SquareDevTools,List<PieceDevTools>> wMoves;
    private HashMap<SquareDevTools,List<PieceDevTools>> bMoves;

    /**
     * Constructs a new instance of CheckmateDetector on a given board. By
     * convention should be called when the board is in its initial state.
     *
     * @param b The board which the detector monitors
     * @param wPieces White pieces on the board.
     * @param bPieces Black pieces on the board.
     * @param wk Piece object representing the white king
     * @param bk Piece object representing the black king
     */
    public CheckmateDetectorDevTools(BoardDevTools b, LinkedList<PieceDevTools> wPieces,
                                     LinkedList<PieceDevTools> bPieces, KingDevTools wk, KingDevTools bk) {
        this.b = b;
        this.wPieces = wPieces;
        this.bPieces = bPieces;
        this.bk = bk;
        this.wk = wk;

        // Initialize other fields
        squares = new LinkedList<SquareDevTools>();
        movableSquares = new LinkedList<SquareDevTools>();
        wMoves = new HashMap<SquareDevTools,List<PieceDevTools>>();
        bMoves = new HashMap<SquareDevTools,List<PieceDevTools>>();

        SquareDevTools[][] brd = b.getSquareArray();

        // add all squares to squares list and as hashmap keys
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[y][x]);
                wMoves.put(brd[y][x], new LinkedList<PieceDevTools>());
                bMoves.put(brd[y][x], new LinkedList<PieceDevTools>());
            }
        }

        // update situation
        update();
    }

    /**
     * Updates the object with the current situation of the game.
     */
    public void update() {
        // Iterators through pieces
        Iterator<PieceDevTools> wIter = wPieces.iterator();
        Iterator<PieceDevTools> bIter = bPieces.iterator();

        // empty moves and movable squares at each update
        for (List<PieceDevTools> pieces : wMoves.values()) {
            pieces.removeAll(pieces);
        }

        for (List<PieceDevTools> pieces : bMoves.values()) {
            pieces.removeAll(pieces);
        }

        movableSquares.removeAll(movableSquares);

        // Add each move white and black can make to map
        while (wIter.hasNext()) {
            PieceDevTools p = wIter.next();

            if (!p.getClass().equals(KingDevTools.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }

                List<SquareDevTools> mvs = p.getLegalMoves(b);
                Iterator<SquareDevTools> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<PieceDevTools> pieces = wMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }

        while (bIter.hasNext()) {
            PieceDevTools p = bIter.next();

            if (!p.getClass().equals(KingDevTools.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }

                List<SquareDevTools> mvs = p.getLegalMoves(b);
                Iterator<SquareDevTools> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<PieceDevTools> pieces = bMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
    }

    /**
     * Checks if the black king is threatened
     * @return boolean representing whether the black king is in check.
     */
//    public boolean blackInCheck() {
//        update();
//        Square sq = bk.getPosition();
//        if (wMoves.get(sq).isEmpty()) {
//            movableSquares.addAll(squares);
//            return false;
//        } else return true;
//    }

    /**
     * Checks if the white king is threatened
     * @return boolean representing whether the white king is in check.
     */
//    public boolean whiteInCheck() {
//        update();
//        Square sq = wk.getPosition();
//        if (bMoves.get(sq).isEmpty()) {
//            movableSquares.addAll(squares);
//            return false;
//        } else return true;
//    }


//    ///////////
    public Boolean isInCheck(String color) {
        update();
        SquareDevTools sq =null;
        if (color.equals("w")){
            sq=wk.getPosition();
            if (bMoves.get(sq).isEmpty()) {
                movableSquares.addAll(squares);
                return false;
            }

        }
        else if (color.equals("b")){
            sq = bk.getPosition();
            if (wMoves.get(sq).isEmpty()) {
                movableSquares.addAll(squares);
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether black is in checkmate.
     * @return boolean representing if black player is checkmated.
     */
//    public boolean blackCheckMated() {
//        boolean checkmate = true;
//        // Check if black is in check
//        if (!this.isInCheck("b")) return false;
//
//        // If yes, check if king can evade
//        if (canEvade(wMoves, bk)) checkmate = false;
//
//        // If no, check if threat can be captured
//        List<Piece> threats = wMoves.get(bk.getPosition());
//        if (canCapture(bMoves, threats, bk)) checkmate = false;
//
//        // If no, check if threat can be blocked
//        if (canBlock(threats, bMoves, bk)) checkmate = false;
//
//        // If no possible ways of removing check, checkmate occurred
//        return checkmate;
//    }
//
//    /**
//     * Checks whether white is in checkmate.
//     * @return boolean representing if white player is checkmated.
//     */
//    public boolean whiteCheckMated() {
//        boolean checkmate = true;
//        // Check if white is in check
//        if (!this.isInCheck("w")) return false;
//
//        // If yes, check if king can evade
//        if (canEvade(bMoves, wk)) checkmate = false;
//
//        // If no, check if threat can be captured
//        List<Piece> threats = bMoves.get(wk.getPosition());
//        if (canCapture(wMoves, threats, wk)) checkmate = false;
//
//        // If no, check if threat can be blocked
//        if (canBlock(threats, wMoves, wk)) checkmate = false;
//
//        // If no possible ways of removing check, checkmate occurred
//        return checkmate;
//    }



    public Boolean colorCheckMated(String color){

        boolean checkmate = true;
        KingDevTools k =null;
        HashMap<SquareDevTools,List<PieceDevTools>>  opositeMoves=null;
        HashMap<SquareDevTools,List<PieceDevTools>>  colorMoves=null;

        if (color.equals("w")){
            if (!this.isInCheck("w")) return false;
             opositeMoves=bMoves;
             colorMoves=wMoves;
             k =wk;
        }
        else if (color.equals("b")){
            if (!this.isInCheck("b")) return false;
             opositeMoves=wMoves;
             colorMoves=bMoves;
             k = bk;
        }

        // If yes, check if king can evade
        if (canEvade(opositeMoves, k)) checkmate = false;

        // If no, check if threat can be captured
        List<PieceDevTools> threats = opositeMoves.get(k.getPosition());
        if (canCapture(colorMoves, threats, k)) checkmate = false;

        // If no, check if threat can be blocked
        if (canBlock(threats, colorMoves, wk)) checkmate = false;

        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }





    /*
     * Helper method to determine if the king can evade the check.
     * Gives a false positive if the king can capture the checking piece.
     */
    private boolean canEvade(Map<SquareDevTools,List<PieceDevTools>> tMoves, KingDevTools tKing) {
        boolean evade = false;
        List<SquareDevTools> kingsMoves = tKing.getLegalMoves(b);
        Iterator<SquareDevTools> iterator = kingsMoves.iterator();

        // If king is not threatened at some square, it can evade
        while (iterator.hasNext()) {
            SquareDevTools sq = iterator.next();
            if (!testMove(tKing, sq)) continue;
            if (tMoves.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
            }
        }

        return evade;
    }

    /*
     * Helper method to determine if the threatening piece can be captured.
     */
    private boolean canCapture(Map<SquareDevTools,List<PieceDevTools>> poss,
                               List<PieceDevTools> threats, KingDevTools k) {

        boolean capture = false;
        if (threats.size() == 1) {
            SquareDevTools sq = threats.get(0).getPosition();

            if (k.getLegalMoves(b).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(k, sq)) {
                    capture = true;
                }
            }

            List<PieceDevTools> caps = poss.get(sq);
            ConcurrentLinkedDeque<PieceDevTools> capturers = new ConcurrentLinkedDeque<PieceDevTools>();
            capturers.addAll(caps);

            if (!capturers.isEmpty()) {
                movableSquares.add(sq);
                for (PieceDevTools p : capturers) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }

        return capture;
    }

    /*
     * Helper method to determine if check can be blocked by a piece.
     */
    private boolean canBlock(List<PieceDevTools> threats,
                             Map <SquareDevTools,List<PieceDevTools>> blockMoves, KingDevTools k) {
        boolean blockable = false;

        if (threats.size() == 1) {
            SquareDevTools ts = threats.get(0).getPosition();
            SquareDevTools ks = k.getPosition();
            SquareDevTools[][] brdArray = b.getSquareArray();

            if (ks.getXNum() == ts.getXNum()) {
                int max = Math.max(ks.getYNum(), ts.getYNum());
                int min = Math.min(ks.getYNum(), ts.getYNum());

                for (int i = min + 1; i < max; i++) {
                    List<PieceDevTools> blks =
                            blockMoves.get(brdArray[i][ks.getXNum()]);
                    ConcurrentLinkedDeque<PieceDevTools> blockers =
                            new ConcurrentLinkedDeque<PieceDevTools>();
                    blockers.addAll(blks);

                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[i][ks.getXNum()]);

                        for (PieceDevTools p : blockers) {
                            if (testMove(p,brdArray[i][ks.getXNum()])) {
                                blockable = true;
                            }
                        }

                    }
                }
            }

            if (ks.getYNum() == ts.getYNum()) {
                int max = Math.max(ks.getXNum(), ts.getXNum());
                int min = Math.min(ks.getXNum(), ts.getXNum());

                for (int i = min + 1; i < max; i++) {
                    List<PieceDevTools> blks =
                            blockMoves.get(brdArray[ks.getYNum()][i]);
                    ConcurrentLinkedDeque<PieceDevTools> blockers =
                            new ConcurrentLinkedDeque<PieceDevTools>();
                    blockers.addAll(blks);

                    if (!blockers.isEmpty()) {

                        movableSquares.add(brdArray[ks.getYNum()][i]);

                        for (PieceDevTools p : blockers) {
                            if (testMove(p, brdArray[ks.getYNum()][i])) {
                                blockable = true;
                            }
                        }

                    }
                }
            }

            Class<? extends PieceDevTools> tC = threats.get(0).getClass();

            if (tC.equals(QueenDevTools.class) || tC.equals(BishopDevTools.class)) {
                int kX = ks.getXNum();
                int kY = ks.getYNum();
                int tX = ts.getXNum();
                int tY = ts.getYNum();

                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<PieceDevTools> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<PieceDevTools> blockers =
                                new ConcurrentLinkedDeque<PieceDevTools>();
                        blockers.addAll(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (PieceDevTools p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }

                if (kX > tX && tY > kY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY--;
                        List<PieceDevTools> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<PieceDevTools> blockers =
                                new ConcurrentLinkedDeque<PieceDevTools>();
                        blockers.addAll(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (PieceDevTools p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && kY > tY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY++;
                        List<PieceDevTools> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<PieceDevTools> blockers =
                                new ConcurrentLinkedDeque<PieceDevTools>();
                        blockers.addAll(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (PieceDevTools p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && tY > kY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY--;
                        List<PieceDevTools> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<PieceDevTools> blockers =
                                new ConcurrentLinkedDeque<PieceDevTools>();
                        blockers.addAll(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (PieceDevTools p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return blockable;
    }

    /**
     * Method to get a list of allowable squares that the player can move.
     * Defaults to all squares, but limits available squares if player is in
     * check.
     * @param b boolean representing whether it's white player's turn (if yes,
     * true)
     * @return List of squares that the player can move into.
     */
    public List<SquareDevTools> getAllowableSquares(boolean b) {
        movableSquares.removeAll(movableSquares);
        if (isInCheck("w")) {
            colorCheckMated("w");
        } else if (isInCheck("b")) {
            colorCheckMated("b");
        }
        return movableSquares;
    }

    /**
     * Tests a move a player is about to make to prevent making an illegal move
     * that puts the player in check.
     * @param p Piece moved
     * @param sq Square to which p is about to move
     * @return false if move would cause a check
     */
    public boolean testMove(PieceDevTools p, SquareDevTools sq) {
        PieceDevTools c = sq.getOccupyingPiece();

        boolean movetest = true;
        SquareDevTools init = p.getPosition();

        p.move(sq);
        update();

        if (p.getColor() == 0 && isInCheck("b")) movetest = false;
        else if (p.getColor() == 1 && isInCheck("w")) movetest = false;

        p.move(init);
        if (c != null) sq.put(c);

        update();

        movableSquares.addAll(squares);
        return movetest;
    }

}