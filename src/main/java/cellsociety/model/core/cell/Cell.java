package cellsociety.model.core.cell;

import cellsociety.Point;
import cellsociety.model.core.grid.Grid;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.List;

/**
 * The Cell is the atomic unit of our simulation. Each individual cell represents a location on a
 * grid, and has some sort of state.
 *
 * @author Noah Loewy
 */

public abstract class Cell<T extends Cell<T>> {

  public static final int PLACEHOLDER = -1;
  private int myCurrentState;

  private int myNextState;
  private final Point myLocation;
  private final List<Point> myVertices;
  private List<T> myNeighbors;

  /**
   * Constructs a Generic Cell object. Note that Cell is an abstract class. This is because all
   * simulations use a specific kind of Cell, but they all share common methods.
   * @param initialState  the integer representation of the cell's current state
   * @param row           the row the cell is positioned at as represented on a 2D grid
   * @param col           the column the cell is positioned at as represented on a 2D grid
   * @param shapeType     the shape of a cell, as represented on a 2D coordinate grid
   */
  public Cell(int initialState, int row, int col, CellShape shapeType) {
    myCurrentState = initialState;
    myNextState = PLACEHOLDER;
    myLocation = new Point(row, col);
    myVertices = shapeType.getVertices(row, col);
  }

  /**
   * Constructs a Generic Cell object. Note that Cell is an abstract class. This is because all
   * simulations use a specific kind of Cell, but they all share common methods.
   * @param hoodType  the integer representation of the cell's current state
   * @param grid      the row the cell is positioned at as represented on a 2D coordinate grid
   */
  public void initializeNeighbors(Neighborhood hoodType, Grid grid) {
    myNeighbors = hoodType.getNeighbors(grid, this);
  }

  /**
   * This function updates the state of the cell after a given timestep.
   */
  public abstract void transition();

  /**
   * @return the integer representation of the cell's current state (prior to update)
   */
  public int getCurrentState() {
    return myCurrentState;
  }

  /**
   * @return the integer representation of the cell's next state (following update)
   */
  public int getNextState() {
    return myNextState;
  }

  /**
   * Updates the myNextState instance variable
   * @param nextState the new value of myNextState
   */
  public void setNextState(int nextState) {
    myNextState = nextState;
  }

  /**
   * Updates the state instance variables after a timeset, by using a temporary placeholder for the
   * next state, and setting the current state equal to the old "next state"
   */
  public void updateStates() {
    myCurrentState = myNextState;
    myNextState = PLACEHOLDER;
  }

  /**
   * Given an integer representing a target state, determines the number of neighboring cells that
   * have a current state matching the target state.
   *
   * @param state  an integer, representing the state to check for
   * @return the number of neighboring cells where myCurrentState == state
   */
  public int countNeighborsInState(int state) {
    int count = 0;
    for (T c : getNeighbors()) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  /**
   * Retrieves myNeighbors instance variable
   *
   * @return list of neighboring cells, using the neighborhood definition provided during Cell
   * initialization in the Grid class
   */
  public List<T> getNeighbors() {
    return myNeighbors;
  }

  /**
   * Retrieves myLocation instance variable
   *
   * @return the current x,y position of the cell object on the 2-dimensional grid
   */
  public Point getLocation() {
    return myLocation;
  }

  /**
   * Retrieves myVertices instance variable
   *
   * @return a list of point objects, representing the vertices of the cell's graphical
   * representation on a 2D plane, under the assumption that each cell is alotted 1 square unit of
   * space.
   */
  public List<Point> getVertices() {
    return myVertices;
  }

  /**
   * Retrieves the centroid of the Cell's graphical representation, based on the Center of Mass of
   * its vertices
   *
   * @return a point object representing the Cell's center of mass when displayed graphically on a
   * 2D plane
   */
  public Point getCentroid() {
    double rowSum = 0;
    double colSum = 0;
    for (Point p : myVertices) {
      rowSum += p.getRow();
      colSum += p.getCol();
    }
    return new Point(rowSum, colSum);
  }

  /**
   * Updates myNeighborhood instance variable
   *
   * @param neighborhood a list of generic Cells representing the new neighbors of this cell.
   */
  public void setNeighborhood(List<T> neighborhood) {
    myNeighbors = neighborhood;
  }
}
