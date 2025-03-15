import java.util.ArrayList;

public class Convertor {
    public static ArrayList<ArrayList<Integer>> convertToCNF (int[][] values) {
        ArrayList<ArrayList<Integer>> clause_set = encodeRules();

        return clause_set;
    }

    private static ArrayList<ArrayList<Integer>> encodeRules() {
        ArrayList<Integer> clause;
        ArrayList<ArrayList<Integer>> clause_set = new ArrayList<>();
        // Each cell needs value from 1-9
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                clause = new ArrayList<>();
                for (int val = 1; val < 10; val++) {
                    clause.add(getLiteral(row, col, val));
                }
                clause_set.add(clause);
            }
        }

        // Each cell has at most one value
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (int val1 = 1; val1 < 10; val1++) {
                    for (int val2 = val1+1; val2 < 10; val2++) {
                        clause = new ArrayList<>();
                        clause.add(-getLiteral(row, col, val1));
                        clause.add(-getLiteral(row, col, val2));
                        clause_set.add(clause);
                    }
                }
            }
        }

        // Each row contains all numbers
        for (int row = 0; row < 9; row++) {
            for (int val = 1; val < 10; val++) {
                clause = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    clause.add(getLiteral(row, col, val));
                }
                clause_set.add(clause);
            }
        }

        // Each column contains all numbers
        for (int col = 0; col < 9; col++) {
            for (int val = 1; val < 10; val++) {
                clause = new ArrayList<>();
                for (int row = 0; row < 9; row++) {
                    clause.add(getLiteral(row, col, val));
                }
                clause_set.add(clause);
            }
        }

        // Each 3x3 grid contains all numbers
        for (int gridRow = 0; gridRow < 3; gridRow++) {
            for (int gridCol = 0; gridCol < 3; gridCol++) {
                for (int val = 1; val < 10; val++) {
                    clause = new ArrayList<>();
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 3; col++) {
                            int rowIndex = gridRow * 3 + row;
                            int colIndex = gridCol * 3 + col;
                            clause.add(getLiteral(rowIndex, colIndex, val));
                        }
                    }
                    clause_set.add(clause);
                }
            }
        }
        return clause_set;
    }

    // Helper function to get value of each literal at each row, column and value
    private static int getLiteral(int row, int col, int val) {
        return (row-1) * (9 * 9) + (col - 1) * 9 + val;
    }
}
