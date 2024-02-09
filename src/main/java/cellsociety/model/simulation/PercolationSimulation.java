package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.Cell;
import cellsociety.model.core.CellShape;
import cellsociety.model.core.HexagonShape;
import cellsociety.model.core.PercolationCell;
import cellsociety.model.core.RectangleShape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import cellsociety.model.simulation.Records.PercolationRecord;
import java.util.Map;

/**
 * This cellular automata simulation represents the CS201 Percolation Assignment
 * <p>
 * author @noah loewy
 */

public class PercolationSimulation extends Simulation {

  public static final int OPEN = 0;
  public static final int PERCOLATED = 1;
  public static final int BLOCKED = 2;

  private final int percolatedNeighbors;


  /**
   * Initializes a PercolationSimulation object
   *
   * @param row,                the number of rows in the 2-dimensional grid
   * @param col,                the number of columns in the 2-dimensional grid
   * @param hoodType,           the definition of neighbors
   * @param stateList,          a list of the integer representation of each cells state, by rows,
   *                            then cols
   */
  public PercolationSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      PercolationRecord r) {
    super(hoodType,  r.gridType());
    this.percolatedNeighbors = r.percolatedNeighbors();
    createCellsAndGrid(row, col, stateList, r.cellShape(), hoodType);
  }

  public void createCellsAndGrid(int row, int col, List<Integer> stateList,
      String cellShape, Neighborhood hoodType){
    List<Cell> cellList = cellMaker(row, col,stateList,hoodType, cellShape);
    initializeMyGrid(row, col, cellList);
    for(Cell cell : cellList) {
      cell.initializeNeighbors(hoodType, myGrid);
    }
  }
  public List<Cell> cellMaker(int row, int col, List<Integer> stateList, Neighborhood hoodType,
      String cellShape){
    List<Cell> cellList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      CellShape shape = switch (cellShape) {
        case "square" -> new RectangleShape();
        case "hexagon" -> new HexagonShape();
        default -> throw new InvalidValueException("Cell Shape Does Not Exist");
      };
      Map<String, Integer> params = new HashMap<>();
      params.put("percolatedNeighbors", percolatedNeighbors);
      cellList.add(new PercolationCell(stateList.get(i),i/col,i%col,shape, params));
    }
    return cellList;
  }

  /**
   * Transition function for Percolation. All cells remain in their state, unless the cell is open,
   * in which the cell is passed into the helper function handleOpenCell for transitioning
   */
  @Override
  public void transitionFunction() {
    Iterator<Cell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      Cell currentCell = gridIterator.next();
      currentCell.transition();
    }
  }
}
