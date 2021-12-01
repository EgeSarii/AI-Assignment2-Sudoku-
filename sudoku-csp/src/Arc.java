public class Arc {

    private Field leftHandSide ;
    private Field rightHandSide;

    /**
     * Constructor for Arc class
     * @param leftHandSide left hand side of the arc
     * @param rightHandSide right hand side of the arc
     */
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
