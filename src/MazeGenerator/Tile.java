package MazeGenerator;

import Vector.Vector2;

import java.awt.*;

public class Tile {
    Vector2 pos;
    final int size;

    boolean northWall = true;
    boolean southWall = true;
    boolean eastWall = true;
    boolean westWall = true;

    public Tile (int x, int y, int s) {
        pos = new Vector2(x, y);
        size = s;
    }

    public void draw (Graphics2D g) {
        // NorthWall
        if (northWall) { g.drawLine((int)pos.x * size, (int)pos.y * size, (int)(pos.x+1) * size, (int)pos.y * size); }

        // SouthWall
        if (southWall) { g.drawLine((int)pos.x * size, (int)(pos.y+1) * size, (int)(pos.x+1) * size, (int)(pos.y+1) * size); }

        // EastWall
        if (eastWall) { g.drawLine((int)(pos.x + 1) * size, (int)pos.y * size, (int)(pos.x+1) * size, (int)(pos.y+1) * size); }

        // WestWall
        if (westWall) { g.drawLine((int)pos.x * size, (int)pos.y * size, (int)pos.x * size, (int)(pos.y+1) * size); }
    }

    public boolean touched () {
        return !northWall || !southWall || !eastWall || !westWall;
    }
}
