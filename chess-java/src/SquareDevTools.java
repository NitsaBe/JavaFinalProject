

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class SquareDevTools extends JComponent {
    private BoardDevTools b;
    
    private final int color;
    private PieceDevTools occupyingPiece;
    private boolean dispPiece;
    
    private int xNum;
    private int yNum;
    
    public SquareDevTools(BoardDevTools b, int c, int xNum, int yNum) {
        
        this.b = b;
        this.color = c;
        this.dispPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        
        
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public int getColor() {
        return this.color;
    }
    
    public PieceDevTools getOccupyingPiece() {
        return occupyingPiece;
    }
    
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    
    public int getXNum() {
        return this.xNum;
    }
    
    public int getYNum() {
        return this.yNum;
    }
    
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
    
    public void put(PieceDevTools p) {
        this.occupyingPiece = p;
        p.setPosition(this);
    }
    
    public PieceDevTools removePiece() {
        PieceDevTools p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }
    
    public void capture(PieceDevTools p) {
        PieceDevTools k = getOccupyingPiece();
        if (k.getColor() == 0) b.Bpieces.remove(k);
        if (k.getColor() == 1) b.Wpieces.remove(k);
        this.occupyingPiece = p;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.color == 1) {
            g.setColor(new Color(221,192,127));
        } else {
            g.setColor(new Color(101,67,33));
        }
        
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g);
        }
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
    
}
