import edu.*;
public class Board {
    private final short [] board;
    private final short length;
    
    public Board(int[][] blocks)    {
	// construct a board from an N-by-N array of blocks
	board = new short[blocks.length * blocks.length];
	int curr = 0;
	for(int[] row: blocks){
	    for(int i: row){
		board[curr] = (short) i;
	    }
	}
	length = (short)blocks.length;
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
	// board dimension N
	return (int) length;
    }
    public int hamming(){
	// number of blocks out of place	
	int hamming = 0;
	int correctVal = 1;
	for(int i = 0; i < length * length - 1; i++){
	    if(board[i] == 0) continue;
	    if(board[i] != correctVal) hamming++;
	    correctVal++;
	}
	return hamming;
    }
    public int manhattan(){
	// sum of Manhattan distances between blocks and goal
	int manhattan = 0;
	int correctRow = 0, correctCol = 0;
	int currRow, currCol;
	for(int i = 0; i < length * length; i++){
	    if(board[i] == 0) continue;
	    currRow =  (i/length);
	    currCol =  (i%length);
	    correctRow = board[i] / length;
	    correctCol = board[i] % length;
	    manhattan += Math.abs(correctCol - currCol) + Math.abs(correctRow - currRow);
	}
	return manhattan;
    }
    public boolean isGoal(){
	// is this board the goal board?
	return hamming() == 0;
    }
    public Board twin() {
	// a board that is obtained by exchanging any pair of blocks
	int[] singleArr = new int[length * length];
	int currIndex = 0;
	for(int i:singleArr){
	    i = board[currIndex];
	}
	
	//swap one pair
	for(int i = 0; i < singleArr.length; i++){
	    if(singleArr[i] != 0 && singleArr[i + 1] !=0){
		int temp = singleArr[i];
		singleArr[i] = singleArr[i + 1];
		singleArr[i + 1] = temp;
		break;
	    }
	}
	
	//create board input value
	int[][] input = new int[length][length];
	currIndex = 0;
	for(int[] row: input){
	    for(int i: row){
		i = singleArr[currIndex];
		currIndex++;
	    }
	}	
	Board twin = new Board(input);
	return twin;
    }
    public boolean equals(Object y){
	// does this board equal y?
	if(y == null) return false;
	if(y == this) return true;
	if(y.getClass() != this.getClass()) return false;
	
	Board that = (Board) y;
	if(that.board != this.board
		|| that.length != this.length) return false;
	return true;
    }
    public Iterable<Board> neighbors(){
	// all neighboring boards
    }
    public String toString() {
	// string representation of this board (in the output format specified below)
    }

    public static void main(String[] args){
	// unit tests (not graded)
    }
}