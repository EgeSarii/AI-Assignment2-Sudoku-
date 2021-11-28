  private static List<Field> sameSubSquare(int i, int j, Field[][] grid)
  { 
	List<Field> inSameSubSquare = new ArrayList<>();
    if ((i+1)% 3 ==1 ) // row 1,4,7
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(grid[i+1][j+1], grid[i+1][j+2], grid[i+2][j+1], grid[i+2][j+2]);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(grid[i+1][j-1], grid[i+2][j-1], grid[i+1][j+1], grid[i+2][j+1]);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(grid[i+1][j-1], grid[i+2][j-1], grid[i+2][j-1], grid[i+2][j-2]);
      }
    }
    else if ((i+1)%3 == 2) // row 2,5,8
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(grid[i-1][j+1], grid[i-1][j+2], grid[i+1][j+1], grid[i+1][j+2]);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(grid[i+1][j-1], grid[i+1][j+1], grid[i-1][j+1], grid[i-1][j-1]);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(grid[i-1][j-1], grid[i-1][j-2], grid[i+1][j-1], grid[i+1][j-2]);
      }
    }
    else // row 3,6,9
    {
      if((j+1) % 3 ==1) //column 1,4, 7
      {
       inSameSubSquare.addAll(grid[i-1][j+1], grid[i-1][j+2], grid[i-2][j+1], grid[i-2][j+2]);
      }
      else if ((j+1) % 3 ==2) //column 2,5,8
      {
       inSameSubSquare.addAll(grid[i-1][j-1], grid[i-1][j+1], grid[i-2][j+1], grid[i-2][j-1]);
      }
      else //(j+1)%3 ==0   column 3,6,9
      {
       inSameSubSquare.addAll(grid[i-1][j-1], grid[i-1][j-2], grid[i-2][j-1], grid[i-2][j-2]);
      }
    }
    return inSameSubSquare;
  }
