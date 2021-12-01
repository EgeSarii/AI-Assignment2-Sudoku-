import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
  private Field[][] board;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
		for(int i=0;i<9;i++){
      if(i == 3 || i == 6) {
		  	output += "╠═══════╬═══════╬═══════╣\n";
		  }
      output += "║ ";
		  for(int j=0;j<9;j++){
		   	if(j == 3 || j == 6) {
          output += "║ ";
		   	}
         output += board[i][j] + " ";
		  }
		  
      output += "║\n";
	  }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }


  /**
	 * Reads sudoku from file
	 * @param filename
	 * @return 2d int array of the sudoku
	 */
	public static Field[][] readsudoku(String filename) {
		assert filename != null && filename != "" : "Invalid filename";
		String line = "";
		Field[][] grid = new Field[9][9];
		try {
		FileInputStream inputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(inputStream);
        for(int i = 0; i < 9; i++) {
        	if(scanner.hasNext()) {
        		line = scanner.nextLine();
        		for(int j = 0; j < 9; j++) {
              int numValue = Character.getNumericValue(line.charAt(j));
              if(numValue == 0) {
                grid[i][j] = new Field();
              } else if (numValue != -1) {
                grid[i][j] = new Field(numValue);
        			}
        		}
        	}
        }
        scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("error opening file: "+filename);
		}
    addNeighbours(grid);
		return grid;
	}

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
    for (int i =0; i< 9; i++)
    {
      for(int j=0; j<9 ; j++)
      {
        List<Field> neighbours = new ArrayList<>();
        neighbours.addAll(sameSubSquare(i, j, grid));
        neighbours.addAll(sameRowColumn(i, j, grid));

        grid[i][j].setNeighbours(neighbours);

      }
    }
  }

  /**
   * Implementation of algorithm for finding all non-overlapping neighbours of 
   * a field in the same sub-square
   * @param i row index of field
   * @param j column index of field
   * @param grid the sudoku table
   * @return a list of all non-overlapping neighbours
   */
  private static List<Field> sameSubSquare(int i, int j, Field[][] grid)
  { 
	List<Field> inSameSubSquare = new ArrayList<>();
    if ((i+1)% 3 ==1 ) // row 1,4,7
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i+1][j+1], grid[i+1][j+2], grid[i+2][j+1], grid[i+2][j+2]));
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i+1][j-1], grid[i+2][j-1], grid[i+1][j+1], grid[i+2][j+1]));
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i+1][j-1], grid[i+2][j-1], grid[i+2][j-1], grid[i+2][j-2]));
      }
    }
    else if ((i+1)%3 == 2) // row 2,5,8
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i-1][j+1], grid[i-1][j+2], grid[i+1][j+1], grid[i+1][j+2]));
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i+1][j-1], grid[i+1][j+1], grid[i-1][j+1], grid[i-1][j-1]));
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i-1][j-1], grid[i-1][j-2], grid[i+1][j-1], grid[i+1][j-2]));
      }
    }
    else // row 3,6,9
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i-1][j+1], grid[i-1][j+2], grid[i-2][j+1], grid[i-2][j+2]));
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i-1][j-1], grid[i-1][j+1], grid[i-2][j+1], grid[i-2][j-1]));
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(Arrays.asList(grid[i-1][j-1], grid[i-1][j-2], grid[i-2][j-1], grid[i-2][j-2]));
      }
    }
    return inSameSubSquare;
  }
  /**
   * Implementation of algorithm for finding all neighbours of 
   * a field in the same row and same column
   * @param i row index of field
   * @param j column index of field
   * @param grid the sudoku table
   * @return a list of all neighbours in the same row-column
   */
  private static List<Field> sameRowColumn ( int i, int j, Field[][] grid )
  {

    List<Field> inSameRowColumn = new ArrayList<>();
    for (int k =0; k<9 ; k++ ) //fields in the same row and same column
    {
      if(k!= j)
      {
        inSameRowColumn.add(grid[i][k]);
      }
      if(k!=i)
      {
        inSameRowColumn.add(grid[k][j]);
      }
    }
    return inSameRowColumn;
  }

  /**
	 * Generates fileformat output
	 */
	public String toFileString(){
    String output = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output += board[i][j].getValue();
      }
      output += "\n";
    }
    return output;
	}

  public Field[][] getBoard(){
    return board;
  }
}
