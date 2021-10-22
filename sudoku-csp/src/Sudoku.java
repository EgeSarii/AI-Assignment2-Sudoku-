import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        addNeighboursToField(grid[i][j], i, j, grid);
      }
    }
  }

  /**
   * Adds a list of neighbours to one field, i.e., arcs to be satisfied
   * @param field a field
   * @param neighbour neighbour to add to the field
   */
  private static void addNeighboursToField(Field field,int i, int j, Field[][] grid) {
    if ((i+1)% 3 ==1 ) // row 1,4,7
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
        List<Field> newNeighbours = Arrays.asList(grid[i+1][j+1],grid[i+1][j+2],grid[i+2][j+1],grid[i+2][j+2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
        List<Field> newNeighbours = Arrays.asList(grid[i+1][j-1],grid[i+2][j-1],grid[i+1][j+1],grid[i+2][j+1]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
        List<Field> newNeighbours = Arrays.asList(grid[i+1][j-1],grid[i+2][j-1],grid[i+2][j-1],grid[i+2][j-2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
    }
    else if ((i+1)%3 == 2) // row 2,5,8
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
        List<Field> newNeighbours = Arrays.asList(grid[i-1][j+1],grid[i-1][j+2],grid[i+1][j+1],grid[i+1][j+2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
        List<Field> newNeighbours = Arrays.asList(grid[i+1][j-1],grid[i+1][j+1],grid[i-1][j+1],grid[i-1][j-1]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
        List<Field> newNeighbours = Arrays.asList(grid[i-1][j-1],grid[i-1][j-2],grid[i+1][j-1],grid[i+1][j-2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
    }
    else // row 3,6,9
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
        List<Field> newNeighbours = Arrays.asList(grid[i-1][j+1],grid[i-1][j+2],grid[i-2][j+1],grid[i-2][j+2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
        List<Field> newNeighbours = Arrays.asList(grid[i-1][j-1],grid[i-1][j+1],grid[i-2][j+1],grid[i-2][j-1]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
        List<Field> newNeighbours = Arrays.asList(grid[i-1][j-1],grid[i-1][j-2],grid[i-2][j-1],grid[i-2][j-2]);
        sameRowColumn (newNeighbours, i, j, grid);
        field.setNeighbours(newNeighbours);
      }
    }
  }

  private static void sameRowColumn (List<Field> newNeighbours, int i, int j, Field[][] grid )
  {
    for (int k =0; k<9 ; k++ ) //fields in the same row and same column
    {
      if(k!= j)
      {
        Field neighbourInRow = grid[i][k];
        newNeighbours.add(neighbourInRow);
      }
      if(k!=i)
      {
        Field neighbourInColumn = grid[k][j];
        newNeighbours.add(neighbourInColumn);
      }
    }
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
