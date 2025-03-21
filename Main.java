import java.util.Scanner;
public class Main{

    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        double[][] matrix;
        //perform operations
        GaussJordan m = new GaussJordan(kb);
        matrix = m.GaussJordanElimination();
        
        

    }
}