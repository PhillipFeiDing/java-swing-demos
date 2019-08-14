public class Triangle
{
    private Point pA,pB,pC;

    public Triangle(Point pA, Point pB, Point pC)
    {
        this.pA = pA;
        this.pB = pB;
        this.pC = pC;
    }

    public void printSides()
    {
        System.out.printf("Side a  : %10.2f\nSide b  : %10.2f\nSide c  : %10.2f\n"
                , pB.distance(pC), pA.distance(pC), pA.distance(pB));
    }

    public void printAngles()
    {
        System.out.printf("Angle A : %10.2f Deg\nAngle B : %10.2f Deg\nAngle C : %10.2f Deg\n"
                , angleBetween(a(), b(), c())
                , angleBetween(b(), a(), c())
                , angleBetween(c(), a(), b()));
    }

    public double angleBetween(double oppSide, double side1, double side2)
    {
        double angle = Math.acos((oppSide * oppSide - side1 * side1 - side2 * side2)
                / (2 * side1 * side2)) *180 / Math.PI;
        if (side1 * side1 + side2 * side2 < oppSide * oppSide) {return Math.max(angle, 180 - angle);}
        else {return Math.min(angle, 180 - angle);}
    }

    public double a() {return pB.distance(pC);}
    public double b() {return pA.distance(pC);}
    public double c() {return pA.distance(pB);}
    public Point pA() {return pA;}
    public Point pB() {return pB;}
    public Point pC() {return pC;}
    public double getPerimeter(){return a() + b() + c();}
    public double s() {return getPerimeter() / 2;}
    public double getArea() { return Math.sqrt(s() * (s() - a()) * (s() - b()) * (s() - c()));}
}