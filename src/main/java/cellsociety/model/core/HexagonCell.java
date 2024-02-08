package cellsociety.model.core;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

public class HexagonCell extends Cell{
  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param row           is the x-coordinate of the cell on the 2-dimensional grid
   * @param col            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public HexagonCell(int initialState, int row, int col) {
    super(initialState, row, col);
    double offset = .5;
    double currOffset;
    if(row%2==1){
      currOffset=.5;
    }
    else{
      currOffset = 0.0;
    }
    List<Point> myVertices = getVertices();
    myVertices.add(new Point(row+.25,col+currOffset, offset));
    myVertices.add(new Point(row+1,col+currOffset, offset));
    myVertices.add(new Point(row+1.25,col+.5+currOffset, offset));
    myVertices.add(new Point(row+1,col+1+currOffset, offset));
    myVertices.add(new Point(row+.25,col+1+currOffset, offset));
    myVertices.add(new Point(row+1,col+.5+currOffset, offset));
  }
}
