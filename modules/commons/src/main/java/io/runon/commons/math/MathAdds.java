package io.runon.commons.math;

public class MathAdds {
    public static double logB(double x, double base) {
        return Math.log(x) / Math.log(base);
    }

    public static long positiveIntegerSum(long positiveInteger){

        return positiveInteger*(positiveInteger+1)/2;
    }



    public static double weightGap(double a, double b){


        if( a > b){
            return b/a;
        }else{
            return a/b;
        }

    }
}
