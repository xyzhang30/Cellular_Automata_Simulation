package cellsociety.model.simulation;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import cellsociety.model.core.WarpedGrid;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
 * transitionFunction() based on the rules of the simulation
 *
 * @author Noah Loewy
 */

public abstract class Simulation<T extends Cell> {

  protected Neighborhood myNeighborhood;
  protected Grid<T> myGrid;

  private Function<Integer, T> myCellInitializer;
  private int myCols;

  private String myGridType;
  private String cellShape;

  public Simulation() {
  }

  /**
   * Constructs a basic Simulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state, by rows, then cols
   * @param gridType          type of grid used in simulation
   */

  public Simulation(int row, int col, Neighborhood hoodType, List<Integer> stateList, String gridType, String cellShape,
      Function<Integer, T> cellInitializer) {

    myCellInitializer = cellInitializer;
    myNeighborhood = hoodType;
    myCols = col;
    myGridType = gridType;
    this.cellShape = cellShape;
    initializeMyGrid(row, col, stateList);
  }

  public void initializeMyGrid(int row, int col, List<Integer> stateList) {
    List<T> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      cellList.add(myCellInitializer.apply(i));
    }
    switch(myGridType) {
      case "Normal": {
        myGrid = new Grid<>(row, col, cellList);
        break;
      }
      case "Warped": {
        myGrid = new WarpedGrid<>(row, col, cellList);
        break;
      }
    }
  }

  /**
   * This abstract function will assign each cell a new value for their myNextState, based on the
   * current grid configuration and the rules of the simulation
   */
  public abstract void transitionFunction();

  /**
   * Iterates through all available cells and updates the current state based on the results of the
   * transition function
   */
  public void processUpdate() {
    printForDebugging();
    Iterator<T> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      T cell = iterator.next();
      cell.updateStates();
    }
  }


  /**
   * Given a list of cells, and an integer representing a state, determines the number of cells in
   * the list that are currently at that state
   *
   * @param neighbors, a list of cells, representing the neighbors of a central cell
   * @param state,     an integer, representing the state to check for
   * @return count, representing the number of cells in neighbors where myCurrentState == state
   */
  public int countNeighborsInState(List<T> neighbors, int state) {
    int count = 0;
    for (T c : neighbors) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  public Neighborhood getNeighborhood(){
    return myNeighborhood;
  }

  public Grid<T> getGrid(){
    return myGrid;
  }
  /**
   * Retrieves the Grid's object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator<T> getIterator() {
    return myGrid.iterator();
  }

  private void printForDebugging() {
    Iterator<T> iterator2 = myGrid.iterator();
    int count = 0;
    while (iterator2.hasNext()) {
      if (count % myCols == 0) {
        System.out.println();
      }
      T c = iterator2.next();
      count++;
      System.out.print(c.getCurrentState() + " ");

    }

  }
}
