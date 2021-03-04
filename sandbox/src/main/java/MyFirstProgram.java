public class MyFirstProgram{

    public static void main(String[] args){
        System.out.println ("Hello, world!");

        //Задание 2.
        Point p1 = new Point(12,10);
        Point p2 = new Point(15,-20);
        System.out.println("Run separate function " + distance(p1,p2));

        //Вычисление расстояния между точками при помощи метода в классе Point
        double result = p1.distancePointMethod(p2);
        System.out.println("Run method from class Point " + result);

    }


    //Функция вычисления расстояния между точками
    public static double distance (Point p1, Point p2){
        return Math.sqrt(Math.pow((p2.x - p1.x),2) + Math.pow((p2.y - p1.y),2));
    }


}