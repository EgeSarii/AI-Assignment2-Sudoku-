import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


class Normal_Comparator implements Comparator<Arc>
{

  @Override
  public int compare(Arc arc1, Arc arc2) {
    return 0;
  }
}

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
    int ct = 0;
    for (Field[] row : sudoku.getBoard())
    {
      for (Field field :row)
      {
        field.initializeArcs();
        arcs.addAll(field.getArcs());
      }
    }

    //Then add these arcs to the agenda.
    Queue<Arc> agenda = new PriorityQueue<>(new MRV_Comparator());//agenda of arcs
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
       /* for(Field f : arc.getLeftHandSide().getOtherNeighbours(arc.getRightHandSide()))
        {
          Arc newArc = new Arc(f, arc.getLeftHandSide());  
          if(!agenda.contains(newArc))
          {
            newArcs1.add(newArc);
          }
        */
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
        if(field.getDomainSize()!=1)
        {
          return false;
        }
      }
    }
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
