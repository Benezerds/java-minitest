public class FirstTask {

    /*
     * I did'nt directly modify the array because the array supposedly to be "FIXED"
     * Thus, I take the liberty to take my assumption that this array might have
     * other uses
     * That is why I approach this problem by making sure not modifying the array at
     * all.
     */
    public static void main(String[] args) throws Exception {
        int[] array = new int[100];

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        // Printing the array
        for (int i : array) {
            // If TRUE = It will be SKIPPED
            if (numbersFilter(i)) {
                continue;
            } else {
                System.out.print(fooBarCheck(i) + ", ");
            }
        }
    }

    // Functions to filter the prime numbers (TRUE IF PRIME NUMBER)
    public static boolean numbersFilter(int number) {
        return (number <= 1) ? false : (number == 2) ? true : (number % 2 == 0) ? false : checkDivition(number);
    }

    /*
     * Helper method to check wheter the number can be divided, up-to the square
     * root of the number itself
     */
    public static boolean checkDivition(int number) {
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * FooBar that takes number as the parameter and then returns String accordingly
     */
    public static String fooBarCheck(int number) {
        if (number % 3 == 0 && number % 5 == 0) {
            return "FooBar";
        } else if (number % 3 == 0) {
            return "Foo";
        } else if (number % 5 == 0) {
            return "Bar";
        } else {
            return String.valueOf(number);
        }
    }

}
