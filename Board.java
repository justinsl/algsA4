import java.util.Arrays;

import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.Queue;

public class Board {
	private final short [] board;
	private final int length;

	public Board(int[][] blocks)    {
		// construct a board from an N-by-N array of blocks
		board = new short[blocks.length * blocks.length];
		int curr = 0;
		length = blocks.length;
		for(int[] row: blocks){
			for(int i = 0; i < length; i++){
				board[curr++] = (short) row[i];
			}
		}
		
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
		for(int i = 0; i < length * length; i++){
			if(board[i] == 0){
				correctVal++;
				continue;
			}
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
			currRow =  getRow(i);
			currCol =  getCol(i);
			correctRow = (board[i] - 1)/ length;
			
			correctCol = (board[i] % length + length - 1) % length;
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
		for(int i = 0; i < length * length; i++){
			singleArr[i] = board[currIndex++];
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
			for(int i = 0; i < length; i++){
				row[i] = singleArr[currIndex++];
				
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
		if(Arrays.equals(this.board, that.board)) return true;
		return false;
	}
	public Iterable<Board> neighbors(){
		// all neighboring boards
		Queue<Board> neighbours = new Queue<Board>();
		int zeroRow, zeroCol;
		int index = 0;
		while(board[index] != 0){
			index++;
		}
		
		zeroRow = getRow(index);
		zeroCol = getCol(index);
		
		if(zeroCol > 0){
			//add left neighbour
			short[] tempBoard = new short[length * length];
			System.arraycopy(board, 0, tempBoard, 0, length * length);
			tempBoard[index] = tempBoard[index - 1];
			tempBoard[index - 1] = 0;
			Board leftNeighbour = new Board(doubleIntArray(tempBoard));
			neighbours.enqueue(leftNeighbour);
		}
		if(zeroCol < length - 1){
			//add right neighbour
			short[] tempBoard = new short[length * length];
			System.arraycopy(board, 0, tempBoard, 0, length * length);
			tempBoard[index] = tempBoard[index + 1];
			tempBoard[index + 1] = 0;
			Board rightNeighbour = new Board(doubleIntArray(tempBoard));
			neighbours.enqueue(rightNeighbour);
		}
		if(zeroRow > 0){
			//add top neighbour
			short[] tempBoard = new short[length * length];
			System.arraycopy(board, 0, tempBoard, 0, length * length);
			tempBoard[index] = tempBoard[getIndex(zeroRow - 1,zeroCol)];
			tempBoard[getIndex(zeroRow - 1,zeroCol)] = 0;
			Board topNeighbour = new Board(doubleIntArray(tempBoard));
			neighbours.enqueue(topNeighbour);
		}
		if(zeroRow < length - 1){
			//add btm neighbour
			short[] tempBoard = new short[length * length];
			System.arraycopy(board, 0, tempBoard, 0, length * length);
			tempBoard[index] = tempBoard[getIndex(zeroRow + 1,zeroCol)];
			tempBoard[getIndex(zeroRow + 1,zeroCol)] = 0;
			Board btmNeighbour = new Board(doubleIntArray(tempBoard));
			neighbours.enqueue(btmNeighbour);
		}
		
		return neighbours;
	}
	
	private int[][] doubleIntArray(short[] board){
		int length = (int) Math.sqrt(board.length);
		int[][] intArray = new int[length][length];
		int currIndex = 0;
		for(int[] row: intArray){
			for(int i = 0; i < length; i++){
				row[i] = board[currIndex++];
			}
		}
		return intArray;
		
	}
	private int getIndex(int row, int col){
		return row * length + col;
	}
	
	private int getRow(int index){
		return index / length;
	}
	
	private int getCol(int index){
		return index % length;
	}

	public String toString() {
		// string representation of this board (in the output format specified below)
		String s = new String();
		s += String.valueOf(length);
		s += '\n';
		for(int r = 0; r < length; r++){
			for(int c = 0; c < length; c++){
				s += board[getIndex(r,c)];
				s += " ";
			}
			s += '\n';
		}
		return s;
	}

	public static void main(String[] args){
		// unit tests (not graded)
		In in = new In(args[0]);
		int[] values = in.readAllInts();
		int length = values[0];
		int[][] intArray = new int[length][length];
		int currIndex = 1;
		for(int[] row:intArray){
			for(int i = 0; i < length; i++){
				row[i] = values[currIndex];
				currIndex++;
			}
		}
		Board test = new Board(intArray);
		System.out.println(test);
//		System.out.println(test.manhattan());
//		System.out.println(test.hamming());
		
		for(Board b: test.neighbors()){
			System.out.println(b);
		}
	}
}