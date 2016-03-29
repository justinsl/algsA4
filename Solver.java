import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
	private MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>();
	private Stack<Board> solution = new Stack<Board>();
	private boolean solvable = false;	
	
    public Solver(Board initial){
	// find a solution to the initial board (using the A* algorithm)
    	SearchNode start = new SearchNode(initial, null, 0);
    	pq.insert(start);
    	
    	SearchNode start2 = new SearchNode(initial.twin(), null, 0);
    	pq2.insert(start2);
    	
    	SearchNode currNode = start;
    	SearchNode currNode2 = start2;
    	
    	while(!currNode.board.isGoal() && !currNode2.board.isGoal()){
    		currNode = pq.delMin();
    		currNode2 = pq2.delMin();
    		
    		//pq1 loop
    		for(Board neighbour: currNode.board.neighbors()){
        		if(currNode.moves == 0 || !neighbour.equals(currNode.previous.board) ){
        			SearchNode toInsert = new SearchNode(neighbour, currNode, currNode.moves + 1);
        			pq.insert(toInsert);
        		}
        	}
    		for(Board neighbour2: currNode2.board.neighbors()){
        		if(currNode2.moves == 0 || !neighbour2.equals(currNode2.previous.board)){
        			SearchNode toInsert2 = new SearchNode(neighbour2, currNode2, currNode2.moves + 1);
        			pq2.insert(toInsert2);
        		}
        	}
    	}
    	
    	SearchNode route;
    	//currNode is goal node
    	if(currNode.board.isGoal()){
    		route = currNode;
    		solvable = true;
    		
    		while(route.board != initial && route.board != start2.board){
        		solution.push(route.board);
        		route = route.previous;
        	}
    		solution.push(initial);
    	}
    	
    	
    	
    }
    public boolean isSolvable(){
	// is the initial board solvable?
    	return solvable;
    }
    public int moves(){
	// min number of moves to solve initial board; -1 if unsolvable
    	
    	if(isSolvable())return solution.size() - 1;
    	else return -1;
    }
    public Iterable<Board> solution(){
	// sequence of boards in a shortest solution; null if unsolvable
    	if(isSolvable())return solution;
    	else return null;
    }
    
    private class SearchNode implements Comparable<SearchNode>{
		private final Board board;
		private final SearchNode previous;
		private final int moves;
		
		private SearchNode(Board b, SearchNode p, int m){
			board = b;
			previous = p;
			moves = m;
		}
		
		@Override
		public int compareTo(SearchNode that) {
			return this.moves + this.board.manhattan() - that.moves - that.board.manhattan();
		}
	}
    
    public static void main(String[] args){
	// solve a slider puzzle (given below)
    	// create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
