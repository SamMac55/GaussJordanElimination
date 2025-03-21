import java.util.Scanner;
public class GaussJordan {
    private double[][] matrix;
    private int numrows;
    private int numcols;
    public GaussJordan(double[][] matrix){
        this.matrix = matrix;
        numrows = matrix.length;
        numcols = matrix[0].length;
    }
    public GaussJordan(Scanner kb){
        System.out.print("How many rows: ");
        numrows = kb.nextInt();
        System.out.print("How many columsn: ");
        numcols = kb.nextInt();
        matrix = new double[numrows][numcols];
        for(int i = 0; i< numrows; i++){
            System.out.println("Enter Row " + (i+1));
            for(int j = 0; j<numcols; j++){
                matrix[i][j] = kb.nextInt();

            }
        }

    }
    /*In gaus jordan eliminaton the algorithm starts with row 1 col 1
     * if row 1 col 1
     * three elementary row operations:
     * switching two rows
     * scaling by non zero
     * scaling a row by nonzero and adding to another row
     * 
     */

    public double[][] GaussJordanElimination(){
        double[][] rref = Gauss();
        System.out.println("Starting backward elimination");
        for(int i = 0; i<rref.length; i++){
            for(int j = 0; j<rref[0].length; j++){
                System.out.print(rref[i][j] + " ");
            }   
            System.out.println();
        }
        int currRow = rref.length-1;
        while(allZeroRow(rref,currRow)){
            --currRow;
        }
        int currCol = rref[0].length-1;
        
        while(currRow>=0 && currCol>= 0){
            for(int i = rref[0].length-1; i>=0; i--){
                if(rref[currRow][i]==1){
                    currCol = i;
                    break;
                }
            }
            System.out.println("reverse scaling and adding");
            for(int i = currRow-1; i>=0; i--){
                System.out.println("scaling row and adding row " + (currRow+1)  + " to row " +(i+1));
                rref = scaleandAdd(rref,currRow, i, (-1.0*rref[i][currCol]));
            }
            System.out.println(toString());
            currRow--;
            currCol = rref[0].length-1;
        }

        return rref;
    }
    //only forward elimination
    public double[][] Gauss(){
        int nextRow = 1;
        int currRow = 0;
        int currCol = 0;
        while(currRow< matrix.length && currCol< matrix[0].length){
            //start with left corner, if it is zero, swap with other row to be nonzero
            if(matrix[currRow][currCol]==0){//first case if its 
                int swapRow = currRow+1;
                while(swapRow<matrix.length && matrix[swapRow][currCol]==0){
                    swapRow++;
                }
                if(swapRow>=matrix.length){
                    currCol++;
                    currRow=0;
                    continue;
                }else{
                    System.out.println("Swapping rows: " + (currRow+1) + " and " + (swapRow+1));
                    matrix =switchRows(matrix,currRow,swapRow);
                }
            }
            //divide current row by its value to make the cursor entry one
            matrix = scaleRow(matrix,currRow, (1.0/matrix[currRow][currCol]));
            System.out.println("scaling row: " + currRow+1);
            //eliminate all other entries in cursor column, then move to the next row and col
            for(int i = currRow+1; i<matrix.length; i++){
                System.out.println("scaling row and adding row " + (currRow+1)  + " to row " + (i+1));
                matrix = scaleandAdd(matrix,currRow, i, (-1.0*matrix[i][currCol]));
            }
            currCol++;
            currRow = nextRow;
            nextRow++;
            System.out.println(toString());
        }

        return matrix;
    }
    //helper methods
    private double[][] switchRows(double[][] m, int row1, int row2){// row 1 and row 2 start counting at 0
        //simple switch
        for(int j = 0; j<m[0].length; j++){
            double temp = m[row1][j];
            m[row1][j] = m[row2][j];
            m[row2][j] = temp;
        }
        return m;
        
    }
    //go through columns and scale
    private double[][] scaleRow(double [][] m, int row, double scalar){
        for(int i = 0; i<m[0].length;i++){
            m[row][i] *= scalar;
        }
        return m;
    }
    //go through columns and add the scale row multiplied by the sclar to row we want to change
    private double[][] scaleandAdd(double[][] m, int scaleRow, int changeRow, double scalar){
        for(int i = 0; i<m[0].length; i++){
            m[changeRow][i] += m[scaleRow][i] * scalar;
        }
        return m;
    }
    //print out our matrix
    public String toString(){
        
        String returnVal = "\nCurrent Matrix\n---------------\n";
        for(int i = 0; i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length; ++j){
                if(j == 0){
                    returnVal+="|";
                }
                returnVal+=matrix[i][j] + " ";
            }   
            returnVal += "|\n";
        }
        return returnVal;
    }
    //helper for gaussJordanElimination to skip the all zero rows
    private boolean allZeroRow(double[][] m, int row){
        for(int i = 0; i< m[0].length; i++){
            if(m[row][i]!=0 || m[row][i]!=-0){
                return false;
            }
        }
        return true;
    }
    
}
