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
    List<Arc> arcs =new ArrayList<>();// arc list of the game
    for (Field[] row : sudoku.getBoard())
    {
      for (Field field :row)
      {
        field.initializeArcs();
        arcs.addAll(field.getArcs());
      }
    }

    //Then add these arcs to the agenda.

    List<Arc> agenda = new ArrayList<>();//agenda of arcs
    agenda.addAll(arcs);
    
    
    //while agenda is not empty
    int ct =0;
    while(!agenda.isEmpty())
    {
      //take the first element first arc
      Arc arc = agenda.get(0);
      agenda.remove(0);
      int arcLeftDomainSize = arc.getLeftHandSide().getDomainSize();
      //revise the arc  
      revise(arc);
      // if the new size of domain is zero return false 
      if(arc.getLeftHandSide().getDomainSize() == 0)
      {
        return false;
      }
      
      // if the domain size has changed
      if(arcLeftDomainSize != arc.getLeftHandSide().getDomainSize() )
      {
        //for all arcs contain the left hand side as the right hand side add to the list
        agenda.addAll(findRightHandSides(arc.getLeftHandSide(),arcs , agenda));
        ct++; 
       
      }
        
    }
    System.out.println(ct);
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
    List<Integer> toRemove = new ArrayList<>();
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
        else if (rightHand.getDomain().indexOf(valueRight)== rightHand.getDomainSize()-1)
        
        {
          toRemove.add(valueLeft); //remove that value from the domain of left hand side.
        }
      }
    }
    for(Integer i : toRemove)
    {
      leftHand.removeFromDomain(i);
    }
  }

  /**
   * It returns the arcs with the right hand side is equal to the input
   * @param leftHandSide the field in the left hand side
   * @param arcList  the list of arcs to be looked at
   * @return a list of arcs where the right hand side is equal to leftHandSide
   */
  public List<Arc> findRightHandSides(Field leftHandSide, List<Arc> arcList, List<Arc> agenda)
  {
    List<Arc> arcsFound = new ArrayList<>();

    for(Arc arc : arcList)    //for every arc in the arc list
    {
      if(leftHandSide == arc.getRightHandSide())  // if the lefthandside is same as the arc's right hand side
      {
          if(!agenda.contains(arc)) // and if that arc is not in the agenda
          {
            arcsFound.add(arc); //add that arc to the agenda
          }
      }
    }

    return arcsFound;

  }
}
