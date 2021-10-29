import java.util.ArrayList;
import java.util.List;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    // TODO: implement AC-3

    // For every field initialize its arcs (if it is a constant value, i.e. known then its arc list is empty)
    //Then add these arcs to the queue.
    List<Arc> arcs = new ArrayList<>();
    for (Field[] row : sudoku.getBoard())
    {
      for (Field field :row)
      {
        field.initializeArcs();
        arcs.addAll(field.getArcs());
      }
    }
    /*System.out.println(arcs.size());
    Arc arc = arcs.get(2);
    System.out.println(arc.getRightHandSide().getValue());
    revise(arc);
    System.out.println(arc.getLeftHandSide().getDomain());
    System.out.println(arc.getRightHandSide().getDomain());
    */
    System.out.println(arcs.isEmpty());
    //while queue is not empty
    while(!! arcs.isEmpty())
    {
      //take the first element first arc
      Arc arc = arcs.get(0);
      Integer arcLeftDomainSize = arc.getLeftHandSide().getDomainSize();

      //revise the arc \\removeFromDomain 
      revise(arc);
      // if the new size of domain is zero return false 
      if(arc.getLeftHandSide().getDomainSize() == 0)
      {
        return false;
      }
      
      // if the domain size has changed
      if(arcLeftDomainSize - arc.getLeftHandSide().getDomainSize() !=0 )
      {
        //for each right hand side of the arc add the queue if it is not on the queue
        //find occurences?
      }

    }
    
    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function
    return true;
  }

  public void revise(Arc arc)
  {
    Field leftHand = arc.getLeftHandSide();
    Field rightHand = arc.getRightHandSide();

    for (int valueLeft : (leftHand.getDomain())) //for all values in the domain of left hand side
    {
      for(int valueRight : rightHand.getDomain()) //there exists a value meets the arc in the domain of right hand side
      {
        if (valueLeft != valueRight)
        {
          break; // if there exists a value then break the inner loop
        }
        // if there is left no value that meets the arc in the domain of right hand side
        // which means the value is the last element of the domain and it does not meet the arc
        else if (rightHand.getDomain().indexOf(valueRight) == rightHand.getDomainSize()-1)
        {
          leftHand.removeFromDomain(valueLeft); //remove that value from the domain of left hand side.
        }
      }
    }
  }
}
