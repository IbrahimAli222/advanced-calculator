import java.util.Scanner;
public class MatrixProgram {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an operation:");
        System.out.println("1. Addition");
        System.out.println("2. Multiplication");
        System.out.println("3. Solve variables using row operations");
        System.out.println("4. compute the determinant for a matrix");
        System.out.println("5. compute the inverse of a square matrix");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        if (choice == 1) {
            try {
                // Input dimensions
                System.out.print("Enter rows of Matrix 1: ");
                int r1 = input.nextInt();

                System.out.print("Enter columns of Matrix 1: ");
                int c1 = input.nextInt();

                System.out.print("Enter rows of Matrix 2: ");
                int r2 = input.nextInt();

                System.out.print("Enter columns of Matrix 2: ");
                int c2 = input.nextInt();

                input.nextLine(); // clear buffer

                // Validate sizes
                if (r1 <= 0 || c1 <= 0 || r2 <= 0 || c2 <= 0) {
                    System.out.println("Error: Matrix dimensions must be positive.");
                    return;
                }

                if (r1 != r2 || c1 != c2) {
                    System.out.println("Error: Matrices must have the same dimensions for addition.");
                    return;
                }

                int[][] matrix1 = new int[r1][c1];
                int[][] matrix2 = new int[r2][c2];
                int[][] result = new int[r1][c1];

                // Input Matrix 1
                System.out.println("\nEnter Matrix 1 (row by row, space-separated):");
                for (int i = 0; i < r1; i++) {
                    System.out.print("Row " + (i + 1) + ": ");
                    String[] values = input.nextLine().split(" ");

                    if (values.length != c1) {
                        System.out.println("Error: You must enter exactly " + c1 + " numbers.");
                        return;
                    }

                    for (int j = 0; j < c1; j++) {
                        matrix1[i][j] = Integer.parseInt(values[j]);
                    }
                }

                // Input Matrix 2
                System.out.println("\nEnter Matrix 2 (row by row, space-separated):");
                for (int i = 0; i < r2; i++) {
                    System.out.print("Row " + (i + 1) + ": ");
                    String[] values = input.nextLine().split(" ");

                    if (values.length != c2) {
                        System.out.println("Error: You must enter exactly " + c2 + " numbers.");
                        return;
                    }

                    for (int j = 0; j < c2; j++) {
                        matrix2[i][j] = Integer.parseInt(values[j]);
                    }
                }

                // Addition
                for (int i = 0; i < r1; i++) {
                    for (int j = 0; j < c1; j++) {
                        result[i][j] = matrix1[i][j] + matrix2[i][j];
                    }
                }

                // Output
                System.out.println("\nResultant Matrix:");
                for (int i = 0; i < r1; i++) {
                    for (int j = 0; j < c1; j++) {
                        System.out.print(result[i][j] + "\t");
                    }
                    System.out.println();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter valid integers only.");
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }

            input.close();
        }
         else if (choice == 2) {
            try {
                // Input dimensions
                System.out.print("Enter rows of Matrix 1: ");
                int r1 = input.nextInt();

                System.out.print("Enter columns of Matrix 1: ");
                int c1 = input.nextInt();

                System.out.print("Enter rows of Matrix 2: ");
                int r2 = input.nextInt();

                System.out.print("Enter columns of Matrix 2: ");
                int c2 = input.nextInt();

                input.nextLine(); // clear buffer

                // Validate sizes
                if (r1 <= 0 || c1 <= 0 || r2 <= 0 || c2 <= 0) {
                    System.out.println("Error: Matrix dimensions must be positive.");
                    return;
                }

                if (c1 != r2) {
                    System.out.println("Error: Cannot multiply matrices.");
                    return;
                }

                int[][] matrix1 = new int[r1][c1];
                int[][] matrix2 = new int[r2][c2];
                int[][] result = new int[r1][c2];

                // Input Matrix 1
                System.out.println("\nEnter Matrix 1 (row by row, space-separated):");
                for (int i = 0; i < r1; i++) {
                    System.out.print("Row " + (i + 1) + ": ");
                    String[] values = input.nextLine().split(" ");

                    if (values.length != c1) {
                        System.out.println("Error: You must enter exactly " + c1 + " numbers.");
                        return;
                    }

                    for (int j = 0; j < c1; j++) {
                        matrix1[i][j] = Integer.parseInt(values[j]);
                    }
                }

                // Input Matrix 2
                System.out.println("\nEnter Matrix 2 (row by row, space-separated):");
                for (int i = 0; i < r2; i++) {
                    System.out.print("Row " + (i + 1) + ": ");
                    String[] values = input.nextLine().split(" ");

                    if (values.length != c2) {
                        System.out.println("Error: You must enter exactly " + c2 + " numbers.");
                        return;
                    }

                    for (int j = 0; j < c2; j++) {
                        matrix2[i][j] = Integer.parseInt(values[j]);
                    }
                }

                // Multiplication
                for (int i = 0; i < r1; i++) {
                    for (int j = 0; j < c2; j++) {
                        for (int k = 0; k < c1; k++) {
                            result[i][j] += matrix1[i][k] * matrix2[k][j];
                        }
                    }
                }

                // Output
                System.out.println("\nResultant Matrix:");
                for (int i = 0; i < r1; i++) {
                    for (int j = 0; j < c2; j++) {
                        System.out.print(result[i][j] + "\t");
                    }
                    System.out.println();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter valid integers only.");
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }

            input.close();
        } else if (choice == 3) {
            System.out.print("Enter number of equations: ");
            int equations = input.nextInt();

            System.out.print("Enter number of variables: ");
            int variables = input.nextInt();

            if (equations <= 0 || variables <= 0) {
                System.out.println("Invalid size.");
                return;
            }

            double[][] matrix = new double[equations][variables + 1];

            System.out.println("Enter the augmented matrix values:");
            for (int i = 0; i < equations; i++) {
                for (int j = 0; j < variables + 1; j++) {
                    if (j == variables) {
                        System.out.print("Enter constant of equation " + (i + 1) + ": ");
                    } else {
                        System.out.print("Enter coefficient of variable " + (j + 1) + " in equation " + (i + 1) + ": ");
                    }
                    matrix[i][j] = input.nextDouble();
                }
            }

            for (int i = 0; i < equations; i++) {
                double pivot = matrix[i][i];

                if (pivot == 0) {
                    System.out.println("Cannot solve using this simple method because pivot is zero.");
                    return;
                }

                for (int j = 0; j < variables + 1; j++) {
                    matrix[i][j] = matrix[i][j] / pivot;
                }

                for (int k = 0; k < equations; k++) {
                    if (k != i) {
                        double factor = matrix[k][i];
                        for (int j = 0; j < variables + 1; j++) {
                            matrix[k][j] = matrix[k][j] - factor * matrix[i][j];
                        }
                    }
                }
            }

            System.out.println("Solution:");
            for (int i = 0; i < variables; i++) {
                System.out.println("Variable " + (i + 1) + " = " + matrix[i][variables]);
            }
        } else if (choice == 4) {
            try {
                System.out.print("Enter the size of the square matrix (n): ");
                int n = input.nextInt();
                if (n <= 0) {
                    System.out.println("Matrix size must be positive.");
                    return;
                }

                double[][] matrix = new double[n][n];
                System.out.println("Enter the matrix elements row by row:");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrix[i][j] = input.nextDouble();
                    }
                }
                System.out.println("\nOriginal Matrix:");
                printMatrix(matrix);

                System.out.println();
                System.out.println("determinant: ");
                System.out.printf("%10.4f ", determinant(matrix, n));

            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } finally {
                input.close();
            }

        } else if (choice == 5) {
            try {
                System.out.print("Enter the size of the square matrix (n): ");
                int n = input.nextInt();
                if (n <= 0) {
                    System.out.println("Matrix size must be positive.");
                    return;
                }

                double[][] matrix = new double[n][n];
                System.out.println("Enter the matrix elements row by row:");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrix[i][j] = input.nextDouble();
                    }
                }

                System.out.println("\nOriginal Matrix:");
                printMatrix(matrix);

                double[][] inverse = invert(matrix);

                System.out.println("\nInverse Matrix:");
                printMatrix(inverse);

            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } finally {
                input.close();
            }


        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static double determinant(double[][] matrix, int n){
        if(n == 1){
            return matrix[0][0];
        } else if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else{
            double det = 0;

            for (int col = 0; col < n; col++) {

                // Create submatrix (minor)
                double[][] sub = new double[n - 1][n - 1];

                for (int i = 1; i < n; i++) {
                    int subCol = 0;

                    for (int j = 0; j < n; j++) {
                        if (j == col) continue;

                        sub[i - 1][subCol] = matrix[i][j];
                        subCol++;
                    }
                }
                // Sign (+ - + - ...)
                double sign;
                if (col % 2 == 0) {
                    sign = 1;
                } else {
                    sign = -1;
                }

                det += sign * matrix[0][col] * determinant(sub, n - 1);
            }

            return det;
        }


    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%10.4f ", val);
            }
            System.out.println();
        }
    }

    public static double[][] invert(double[][] matrix) {
        int n = matrix.length;
        double[][] augmented = new double[n][2 * n];

        // Create augmented matrix [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + n] = 1; // Identity matrix
        }

        // Perform Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Find pivot
            double pivot = augmented[i][i];
            if (pivot == 0) {
                throw new ArithmeticException("Matrix is singular and cannot be inverted.");
            }

            // Normalize pivot row
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }

        // Extract inverse matrix from augmented matrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverse[i], 0, n);
        }

        return inverse;
    }
}
