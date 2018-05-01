package org.vandeseer.pdfbox.easytable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Row {

    private Table table;
    private final List<Cell> cells;
    private Color borderColor;

    private Row(final List<Cell> cells) {
        super();
        this.cells = cells;
        for (final Cell cell : cells) {
            cell.setRow(this);
        }
    }

    public Table getTable() {
        return table;
    }

    void setTable(final Table table) {
        this.table = table;
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    float getHeightWithoutFontHeight() {
        Cell highestCell = null;
		for (Cell cell : cells) {
			if (highestCell == null || cell.getHeightWithoutFontSize() > highestCell.getHeightWithoutFontSize()) {
				highestCell = cell;
			}
		}
		if (highestCell == null) {
			throw new IllegalStateException();
		}
		return highestCell.getHeightWithoutFontSize();
    }

    public Color getBorderColor() {
        return borderColor != null ? borderColor : getTable().getBorderColor();
    }

    private void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public static class RowBuilder {
        private final List<Cell> cells = new LinkedList<Cell>();
        private Color backgroundColor = null;
        private Color borderColor = null;

        public RowBuilder add(final Cell cell) {
            cells.add(cell);
            return this;
        }

        public RowBuilder setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public RowBuilder setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        public Row build() {
			for (Cell cell : cells) {
                if (!cell.hasBackgroundColor()) {
					if (backgroundColor != null) {
						cell.setBackgroundColor(backgroundColor);
					}
                }
            }
            Row row = new Row(cells);
			if (borderColor != null) {
				row.setBorderColor(borderColor);
			}
            return row;
        }
    }

}
