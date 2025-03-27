import java.util.ArrayList;
import java.util.Set;

// https://users.aalto.fi/~tjunttil/2020-DP-AUT/notes-sat/solving.html
// https://sat.inesc-id.pt/~ines/publications/aimath06.pdf
import io.github.charl11e.sat.*;

public class Convertor {

    // UNSAT Result if grid is unsolvable
    public static final int[][] UNSAT_RESULT = new int[0][0];


    public static ArrayList<ArrayList<Integer>> convertToCNF (int[][] values) {
        ArrayList<ArrayList<Integer>> clause_set = new ArrayList<>();
        encodeRules(clause_set);
        encodeValues(clause_set, values);
        return clause_set;
    }

    public static int[][] decodeCNF(Set<Integer> assignment) {
        // Check that assignment is not empty, if it is return empty as UNSAT
        if (assignment.isEmpty()) {
            return UNSAT_RESULT;
        }

        int[][] grid = new int[9][9];
            for (int literal : assignment) {
                if (literal > 0) {
                    int[] values = decodeLiteral(literal);
                    int row = values[0];
                    int col = values[1];
                    int value = values[2];
                    grid[row][col] = value;
                }
            }
        return grid;
    }

    private static void encodeRules(ArrayList<ArrayList<Integer>> clause_set) {
        ArrayList<Integer> clause;
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
    }

    private static void encodeValues(ArrayList<ArrayList<Integer>> clause_set, int[][] values) {
        ArrayList<Integer> clause;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int val = values[row][col];
                if (val != 0) {
                    clause = new ArrayList<>();
                    clause.add(getLiteral(row, col, val));
                    clause_set.add(clause);
                }
            }
        }
    }


    // Helper function to get value of each literal at each row, column and value
    private static int getLiteral(int row, int col, int val) {
        return row * 81 + col * 9 + val;
    }

    private static int[] decodeLiteral(int literal) {
        int adjusted = literal - 1;
        int row = adjusted / 81;
        int remainder = adjusted % 81;
        int col = remainder / 9;
        int value = remainder % 9 + 1;
        return new int[]{row, col, value};
    }

    public static int[][] solve(int[][] values) {
        ArrayList<ArrayList<Integer>> cnf = convertToCNF(values);
        SATResult result = DPLL.solve(cnf);
        return decodeCNF(result.getAssignment());
    }
}
