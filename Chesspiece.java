public interface Chesspiece {
    public ChessPosition getPosition(); // returns position
    public ChessColor getColor(); // returns color
    public String getName(); // returns Name
    public void setPosition(ChessPosition pos); // sets position
    public boolean getCheck(); // returns if a piece is checking the King
    public void setCheck(boolean val); // returns the value of a position's checking
    public boolean isValidMove(ChessPosition curPos, ChessPosition endPos); // Checks theoretical validity of a move
    public ChessPosition[] getPath(ChessPosition curPos, ChessPosition endPos); // returns path between the start and ending position
}
