public class Arc {

    private Field leftHandSide ;
    private Field rightHandSide;
    private Boolean constraint;

    public Arc(Field leftHandSide, Field rightHandSide)
    {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.constraint = (leftHandSide != rightHandSide);
    }
}
