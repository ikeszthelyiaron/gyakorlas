package org.example;

import java.util.*;

public class Recursion {

    static int result = 0;
    static Set<int[]> pathFromOnePathResursion = new LinkedHashSet();
    static boolean[][] towersGrid;
    static int noOfMatricesWithFiveTowers = 0;
    protected static final int GRID_SIZE = 5;
    static Set<boolean[][]> myResultSetStatic;
    static Set<Integer[]> bookResultSetStatic;

    public static void main(String[] args) {

//        int[] coins = {25, 10, 5, 1};
//        int amount = 50;
//        int[][] cache = new int[amount + 1][coins.length];
//        System.out.println("count: " + calculateChange(amount, coins, 0));
//        System.out.println("count: " + calculateChangeMemoization(amount, coins, 0, cache));
//        System.out.println(josephus(15, 3));
//        System.out.println(hanoi(3, 'A', 'B', 'C'));
//        Set<int[]> path = robotGrid(5);
//        System.out.println("robotgrid path size: " + path.size());
//        path.forEach((e) -> System.out.println(e[0] + ", " + e[1]));
//        boolean[][] maze = generateMaze(6);
//        Set<int[]> path = new LinkedHashSet<>();
//        robotGriRecursiveOnePath(maze, path, maze.length, maze[0].length);
//        pathFromOnePathResursion.forEach((e) -> System.out.println(e[0] + ", " + e[1]));

//        System.out.println(colorSports());
//        int[][] grid = generateGrid(5);
//        printGrid(grid);
//        System.out.println(determineBiggestSpotSize(grid));
//        System.out.println("sizeOfSpot: " + determineSizeOfSpot(grid, 2, 4, 1));
//        buildTowersOneSolutionInit(5);
//        buildTowersAllSolutionsInit(5);
//        Set<Integer[]> solutionInBook = buildTowers(0, new Integer[GRID_SIZE], new LinkedHashSet<>());
//        bookResultSetStatic = solutionInBook;
//        System.out.println("solutionInBook size: " + solutionInBook.size());
//        findDiffBetweenSets(myResultSetStatic, bookResultSetStatic);
        int[]input = initializeArray();
        int[] inputCopy = null;
//        findMagicIndex(input, (input.length / 2) - 1);
        findMagicIndex(input, (input.length - 1));
//        printArrayRecursive(input, input.length % 2 == 0 ? (input.length / 2) - 1 : (input.length / 2));

    }

    static void printArrayRecursive(int[] currentArray, int target) {
        int halfIndex = currentArray.length % 2 == 0 ? (currentArray.length / 2) - 1 : (currentArray.length / 2);
        System.out.println("target: " + target);
        if(currentArray.length == 1) {
            System.out.println(currentArray[0]);
            return;
        }
        if(currentArray[halfIndex] == target) {
            System.out.println("found it: " + currentArray[halfIndex]);
        }
        int[] firstHalf = Arrays.copyOfRange(currentArray, 0, halfIndex + 1);
        int[] secondHalf = Arrays.copyOfRange(currentArray, halfIndex + 1, currentArray.length);
        printArrayRecursive(firstHalf, target);
        printArrayRecursive(secondHalf, target);

    }

    static boolean findMagicIndex(int[] currentArray, int target) {
        int halfIndex = currentArray.length % 2 == 0 ? (currentArray.length / 2) - 1 : (currentArray.length / 2);
        if (currentArray.length == 0 || (currentArray.length == 1 && currentArray[halfIndex] != target - 1)) {     //első fele tuti kell?
            return false;
        }
        int[] firstHalf = Arrays.copyOfRange(currentArray, 0, halfIndex + 1);
        int[] secondHalf = Arrays.copyOfRange(currentArray, halfIndex + 1, currentArray.length);
        int initialTargetAdjustment = currentArray.length % 2 == 0 ? (currentArray.length / 2) : (currentArray.length / 2 + 1);
        target -= initialTargetAdjustment;
        if(initialTargetAdjustment == 0) {
            target -= 1;
        }
        if (currentArray[halfIndex] == target) {
            System.out.printf("found it! input[%d] = %d", halfIndex, target);
            return true;
        } else {
            int targetAdjustment = 2 * (secondHalf.length % 2 == 0 ? (secondHalf.length / 2) : (secondHalf.length / 2 + 1)); //a szorzó a target -= sor miatt kell
                return
                        findMagicIndex(secondHalf, target + targetAdjustment) ||
                        findMagicIndex(firstHalf, target);
        }
    }

    static int[] initializeArray() {
        //pr: uts elem, {1, 2, 3, 4, 5, 6, 7}; {1, 2, 3, 4, 5, 6}; & hosszabbak
        int[] result = {1, 2, 3, 4, 4};
        return result;
    }

    static void findDiffBetweenSets(Set<boolean[][]> myResultSet, Set<Integer[]> bookResultSet) {
        List<boolean[][]> converted = bookResultSet.stream()
                .map(a -> generateBooleanMatrixFromIntArray(a))
//                .filter(a -> !myResultSet.stream().anyMatch(m -> matrixEquals(m, a)))
                .toList();
        List<boolean[][]> diff = myResultSet.stream()
                .filter(m -> !converted.stream().anyMatch(a -> matrixEquals(a, m)))
                .toList();
        System.out.println("diff size: " + diff.size());
        System.out.println("solutions not included in my implementation:");
        for (int i = 0; i < diff.size(); i++) {
            printBooleanGrid(diff.get(i));
            System.out.println("-----------------------------------------------");
        }
    }

    static boolean[][] generateBooleanMatrixFromIntArray(Integer[] intArray) {
        boolean[][] result = new boolean[intArray.length][intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            result[i][intArray[i]] = true;
        }
        return result;
    }

    public static Set<Integer[]> buildTowers(int row, Integer[] columns, Set<Integer[]> solutions) {
        if (row == GRID_SIZE) {
            solutions.add(columns.clone());
        } else {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (canBuildBookImpl(columns, row, col)) {
                    // build this tower
                    columns[row] = col;
                    // go to the next row
                    buildTowers(row + 1, columns, solutions);
                }
            }
        }
        return solutions;
    }

    private static boolean canBuildBookImpl(Integer[] columns, int nextRow, int nextColumn) {
        for (int currentRow = 0; currentRow < nextRow; currentRow++) {
            int currentColumn = columns[currentRow];
            // cannot build on the same column
            if (currentColumn == nextColumn) {
                return false;
            }
            int columnsDistance
                    = Math.abs(currentColumn - nextColumn);
            int rowsDistance = nextRow - currentRow;
            // cannot build on the same diagonal
            if (columnsDistance == rowsDistance) {
                return false;
            }
        }
        return true;
    }

    static void buildTowersAllSolutionsInit(int gridSize) {
        Set<boolean[][]> resultSet = buildTowersAllSolutions(new HashSet<boolean[][]>(), new boolean[gridSize][gridSize], 0, 0);
        System.out.println("my result size: " + resultSet.size());
        myResultSetStatic = resultSet;

    }

    static boolean matrixHasFiveTowers(boolean[][] matrix) {
        int noOfTowers = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {
                    noOfTowers++;
                }
            }
        }
//        System.out.println("noOfTowers: " + noOfTowers);
        return noOfTowers == 5;
    }

    static Set<boolean[][]> buildTowersAllSolutions(Set<boolean[][]> result, boolean[][] currentBuilds, int row, int column) {
        int gridSize = currentBuilds.length;

        if (row == gridSize) {
            boolean[][] clone = cloneBooleanMatrix(currentBuilds);
            result.add(clone);
            return result;
        }

        for (int i = 0; i < gridSize; i++) {
            if (canBuild(currentBuilds, row, i)) {
                currentBuilds[row][i] = true;
                result = buildTowersAllSolutions(result, currentBuilds, row + 1, 0);
                currentBuilds[row][i] = false;
                //?: a valid megoldások megtalálása után miért és hogyan üti ki a true értéket a resultSetben?
            }
        }

        return result;
    }

    private static boolean[][] cloneBooleanMatrix(boolean[][] currentBuilds) {
        int gridSize = currentBuilds[0].length;
        boolean[][] result = new boolean[gridSize][gridSize];
        for (int i = 0; i < currentBuilds.length; i++) {
            boolean[] row = new boolean[gridSize];
            for (int j = 0; j < currentBuilds[i].length; j++) {
                row[j] = currentBuilds[i][j];
            }
            result[i] = row;
        }
        return result;
    }

    static boolean matrixEquals(boolean[][] one, boolean[][] other) {
        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < one[0].length; j++) {
                if (!one[i][j] == other[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean invalidCurrentBuilds(boolean[][] currentBuilds) {
        for (int i = 0; i < currentBuilds.length; i++) {
            int noOfTrues = 0;
            for (int j = 0; j < currentBuilds[0].length; j++) {
                if (currentBuilds[i][j]) {
                    noOfTrues++;
                }
            }
            if (noOfTrues > 1) {
                return true;
            }
        }
        return false;
    }

    static void buildTowersOneSolutionInit(int gridSize) {
        towersGrid = new boolean[gridSize][gridSize];
        buildTowersOneSolution(towersGrid, 0, 0);
        printBooleanGrid(towersGrid);
    }

    static boolean buildTowersOneSolution(boolean[][] currentBuilds, int row, int column) {

        int gridSize = currentBuilds.length;

        //base case
        if (row >= gridSize) {
            //mi van ha nincs valid megold?
            towersGrid = currentBuilds;
            return true;
        }

        for (int i = column; i < gridSize; i++) {
            if (canBuild(currentBuilds, row, i)) {
                currentBuilds[row][i] = true;
                if (buildTowersOneSolution(currentBuilds, row + 1, 0)) {
                    return true;
                }
                currentBuilds[row][i] = false;      //backtrack
                return false;
            }
        }
        return false;
    }

    static boolean canBuild(boolean[][] currentBuilds, int row, int column) {
        for (int i = 0; i < currentBuilds[row].length; i++) {
            if (currentBuilds[row][i]) {
                return false;
            }
        }
        for (int i = 0; i < row; i++) {
            if (currentBuilds[i][column]) {      //ha az oszlop már foglalt
                return false;
            }
            for (int j = 0; j < currentBuilds[0].length; j++) {
                if (currentBuilds[i][j]) {
                    int rowDistance = row - i;
                    int columnDistance = Math.abs(column - j);
                    if (rowDistance == columnDistance) {
                        return false;
                    }
                }
            }
        }


        return true;
    }

    static void printBooleanGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] == true ? "I" : ".");
                if (j == grid[0].length - 1) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    static int determineBiggestSpotSize(int[][] grid) {
        int maxSize = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int sizeOfSpotHere = determineSizeOfSpot(grid, j, i, 1);
                if (sizeOfSpotHere > maxSize) {
                    maxSize = sizeOfSpotHere;
                }
            }
        }
        return maxSize;
    }

    static int[][] generateGrid(int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (j % 3 == 0) {
                    result[i][j] = 3;
                } else {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }

    static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
                if (j == grid[0].length - 1) {
                    System.out.println();
                }
            }
        }
    }

    static int determineSizeOfSpot(int[][] grid, int currentRow, int currentColumn, int size) {
        int currentColor = grid[currentRow][currentColumn];
        if (grid[currentRow][currentColumn] < 0) {
            return 0;
        }

        grid[currentRow][currentColumn] = -grid[currentRow][currentColumn];
        //lefele
        if (currentRow < grid.length - 1 && grid[currentRow + 1][currentColumn] == currentColor) {
            size = determineSizeOfSpot(grid, currentRow + 1, currentColumn, size + 1);
        }

        //felfele
        if (currentRow > 0 && grid[currentRow - 1][currentColumn] == currentColor) {
            size = determineSizeOfSpot(grid, currentRow - 1, currentColumn, size + 1);
        }
        //balra
        if (currentColumn > 0 && grid[currentRow][currentColumn - 1] == currentColor) {
            size = determineSizeOfSpot(grid, currentRow, currentColumn - 1, size + 1);
        }
        //jobbra
        if (currentColumn < grid[0].length - 1 && grid[currentRow][currentColumn + 1] == currentColor) {
            size = determineSizeOfSpot(grid, currentRow, currentColumn + 1, size + 1);
        }
        return size;
    }

    static boolean robotGridRecursiveOnePath(boolean[][] maze, Set<int[]> path, int m, int n) {
        //base case
        if (m == 1 && n == 1) {
            path.add(new int[]{m, n});
            pathFromOnePathResursion = path;
            return true;
        }

        int currentColumn = maze[0].length - m;
        int currentRow = maze.length - n;
        int nextColumn = currentColumn + 1;
        int nextRow = currentRow + 1;

        //recurs1: lefele
        if (currentRow < maze[0].length - 1 && !maze[currentColumn][nextRow]) {
            path.add(new int[]{m, n});
            if (robotGridRecursiveOnePath(maze, path, m, n - 1)) {
                return true;
            }
            path.remove(new int[]{m, n});
        }

        //recurs2: jobbra
        if (currentColumn < maze.length - 1 && !maze[nextColumn][currentRow]) {
            path.add(new int[]{m, n});
            if (robotGridRecursiveOnePath(maze, path, m - 1, n)) {
                return true;
            }
            path.remove(new int[]{m, n});
        }

        return false;
    }

    static Set<int[]> robotGridRecursiveAllPaths(boolean[][] maze, Set<int[]> path, int m, int n) {
        //m: oszlop, n: sor --> maze[m][n]
        if (m == 1 && n == 1) {
            return path;
        }
        int currentColumn = maze[0].length - m;
        int currentRow = maze.length - n;
        int nextColumn = currentColumn + 1;
        int nextRow = currentRow + 1;
        if (currentRow < maze[0].length - 1 && !maze[currentColumn][nextRow]) { //ha lehet 1et lefele men
            path.add(new int[]{m, n - 1});
            robotGridRecursiveAllPaths(maze, path, m, n - 1);
        }
        if (currentColumn < maze.length - 1 && !maze[nextColumn][currentRow]) {  //ha lehet jobbra menni
            path.add(new int[]{m - 1, n});
            robotGridRecursiveAllPaths(maze, path, m - 1, n);
        }
        return path;
    }

    static Set<int[]> robotGrid(int sizeOfGrid) {
        boolean[][] maze = generateMaze(sizeOfGrid);
        Set<int[]> path = new LinkedHashSet<>();
        int m = maze[0].length;
        int n = maze.length;
        return robotGridRecursiveAllPaths(maze, path, m, n);
    }

    static boolean[][] generateMaze(int sizeOfGrid) {
        boolean[][] result = new boolean[sizeOfGrid][sizeOfGrid];
        result[0][2] = true;
        result[2][0] = true;
        result[3][0] = true;
        result[3][1] = true;
        result[3][2] = true;
        result[3][3] = true;
        return result;
    }

    static int hanoi(int noOfDisks, char origin, char target, char intermediate) {

        if (noOfDisks == 1) {
            System.out.printf("moving disk 1 from %c to %c\n", origin, target);
            result++;
            return result;
        }

        hanoi(noOfDisks - 1, origin, intermediate, target);

        System.out.printf("moving disk %d from %c to %c\n", noOfDisks, origin, target);
        result++;

        hanoi(noOfDisks - 1, intermediate, target, origin);

        return result;
    }

    static int josephus(int noOFPeople, int k) {
        if (noOFPeople == 1) {
            return 1;
        }
        return (josephus(noOFPeople - 1, k) + k - 1) % noOFPeople + 1;
    }

    static int calculateChangeMemoization(int amount, int[] coins, int position, int[][] cache) {
        int count = 0;
        if (position >= coins.length - 1) {
            return 1;
        }

        int coin = coins[position];
        if (cache[amount][position] > 0) {
            return cache[amount][position];
        }
        for (int i = 0; coin * i <= amount; i++) {
            int remaining = amount - coin * i;
            cache[amount][position] = calculateChange(remaining, coins, position + 1);
            count += cache[amount][position];
        }
        return count;
    }

    static int calculateChange(int amount, int[] coins, int position) {
        int count = 0;
        if (position >= coins.length - 1) {
            return 1;
        }

        int coin = coins[position];
        for (int i = 0; coin * i <= amount; i++) {
            int remaining = amount - coin * i;
            count += calculateChange(remaining, coins, position + 1);
        }
        return count;
    }


}
