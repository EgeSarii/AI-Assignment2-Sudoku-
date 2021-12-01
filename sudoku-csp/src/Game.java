import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Comparator class for Normal(default) comparator for AC-3
 * It does change nothing.
 */ 
class Normal_Comparator implements Comparator<Arc>
{

  @Override
  public int compare(Arc arc1, Arc arc2) {
    return 0;
  }
}
/**
 * Comparator class for MRV Heuristics comparator for AC-3
 * It sorts the arcs in ascending order where the arcs with less domain size
 * of right hand side are prioritized.
 */ 
class MRV_Comparator implements Comparator<Arc>
{

  @Override
  public int compare(Arc arc1, Arc arc2) {
    if(arc1.getRightHandSide().getDomainSize() < arc2.getRightHandSide().getDomainSize())
    {
      return -1;
    }
    else if (arc1.getRightHandSide().getDomainSize() > arc2.getRightHandSide().getDomainSize())
    {
      return 1;
    }
    else
    {
      return 0;
    }
  }
}
/**
 * Comparator class for PFA Heuristics comparator for AC-3
 * It prioritize the arcs wiht right hand side has domain size of 1.
 * If they are not 1, it changes nothing.
 */ 
class PFA_Comparator implements Comparator<Arc>
{

  @Override
  public int compare(Arc arc1, Arc arc2) {
    if(arc1.getRightHandSide().getDomainSize()==1 && arc2.getRightHandSide().getDomainSize()!=1)
    {
      return -1;
    }
    else if (arc1.getRightHandSide().getDomainSize()!=1 && arc2.getRightHandSide().getDomainSize()==1)
    {
      return 1;
    }
    else
    {
      return 0;
    }
  }
}

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
    // For every field initialize its arcs (if it is a constant value, i.e. known then its arc list is empty)
    List<Arc> arcs =new ArrayList<>();// arc list of the game
    int ct = 0; //counter for complexity measurement
    for (Field[] row : sudoku.getBoard())
    {
      for (Field field :row)
      {
        field.initializeArcs();
        arcs.addAll(field.getArcs());
      }
    }

    //Then add these arcs to the agenda.
    Queue<Arc> agenda = new PriorityQueue<>(new PFA_Comparator());//agenda of arcs
    agenda.addAll(arcs);

    
    //while agenda is not empty
    while(!agenda.isEmpty())
    {
      ct++;
      //take the first element first arc
      Arc arc = agenda.poll();
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
        for(Arc newArc : arcs)    //for every arc in the arc list
        {
          if(arc.getLeftHandSide() == newArc.getRightHandSide() && (!agenda.contains(newArc)))// if the lefthandside is same as the arc's right hand side
          {                                             // and if that arc is not in the agenda
            agenda.add(newArc); //add that arc to the agenda
          }
        }
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
    for (Field[] row : sudoku.getBoard())
    {
      for (Field field :row)
      {
        if(field.getDomainSize()!=1) //if the domain size of one field is not 1
        {
          return false; // then the solution is not valid
        }
      }
    }
    return true;//if the domain sizes of every field is 1, then the solution is valid
  }
  /**
   * Implementation of Revise Operation
   * @param arc arc to be revised
   */
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
          toRemove.add(valueLeft); //add to the list of values to be removed
        }
      }
    }
    for(Integer i : toRemove)
    {
      leftHand.removeFromDomain(i);//remove that value from the domain of left hand side.
    }
  }
}
