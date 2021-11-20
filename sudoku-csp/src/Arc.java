public class Arc {

    private Field leftHandSide ;
    private Field rightHandSide;

    public Arc(Field leftHandSide, Field rightHandSide)
    {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public void setLeftHandside(Field leftHandSide)
    {
        this.leftHandSide = leftHandSide;
    }

    public void setRightHandside(Field rightHandSide)
    {
        this.rightHandSide = rightHandSide;
    }

    public Field getLeftHandSide()
    {
        return this.leftHandSide;
    }
    
    public Field getRightHandSide()
    {
        return this.rightHandSide;
    }

    
}
