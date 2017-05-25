package Rubiks_Cube;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by new on 09.02.17.
 */
public class Cube {
    private Side[] sides;
    public Cube() {
        sides = new Side[6];
        sides[0] = new Side(Sides.TOP, new Sides[] {Sides.BACKWARD, Sides.RIGHT, Sides.FORWARD, Sides.LEFT}, Sides.BOTTOM);
        sides[1] = new Side(Sides.FORWARD, new Sides[] {Sides.TOP, Sides.RIGHT, Sides.BOTTOM, Sides.LEFT}, Sides.BACKWARD);
        sides[2] = new Side(Sides.RIGHT, new Sides[] {Sides.TOP, Sides.BACKWARD, Sides.BOTTOM, Sides.FORWARD}, Sides.LEFT);
        sides[3] = new Side(Sides.BACKWARD, new Sides[] {Sides.TOP, Sides.LEFT, Sides.BOTTOM, Sides.RIGHT}, Sides.FORWARD);
        sides[4] = new Side(Sides.LEFT, new Sides[] {Sides.TOP, Sides.FORWARD, Sides.BOTTOM, Sides.BACKWARD}, Sides.RIGHT);
        sides[5] = new Side(Sides.BOTTOM, new Sides[] {Sides.FORWARD, Sides.RIGHT, Sides.BACKWARD, Sides.LEFT}, Sides.TOP);
    }

    public Side getSide(Sides side) {
        return sides[side.getIndex()];
    }

    public void shuffle(int difficulty) {
        Random random = new Random();
        for (int i = 0; i <= difficulty; i++) {
            switch (random.nextInt(2)) {
                case 0:
                    turnMiddleLine(random.nextInt(6));
                case 1:
                    sides[random.nextInt(6)].flip(Direction.values()[random.nextInt(2)]);
                    break;
            }
        }
    }

    public void turnCubeVertical(Direction direction) {
        if (direction == Direction.CLOCKWISE)
            sides[1].flipCubeAround();
        else
            sides[3].flipCubeAround();
    }
    public void turnCubeHorizontal (Direction direction) {
        if (direction == Direction.CLOCKWISE)
            sides[0].flipCubeAround();
        else
            sides[5].flipCubeAround();
    }
    public void turnCubeForward () {
        sides[4].flipCubeAround();
    }
    public void turnCubeBackward () {
        sides[2].flipCubeAround();
    }
    public void turnMiddleLine(int index) {
        if (index < 0 && index > 5) throw new IndexOutOfBoundsException("Index should be within 0..5");
        if (index == 0 || index == 5) {
            sides[index].flipMiddleLine(false);
        } else {
            sides[index].flipMiddleLine(true);
        }
    }


    public static void main(String[] args) {
        Cube p = new Cube();
        //p.currentPosition(true);
        //System.out.println(p.toString());
       //p.getSide(Sides.BACKWARD).flipCubeAround();
        //System.out.println(p.toString());
        //p.shuffle(150);
        System.out.println(p.currentPosition(Sides.TOP, true, 2));
        //System.out.println(p.getSide(Sides.TOP));
    }

    public class Side {
        private Sides current;
        public Sides[] bound = new Sides[4];
        public Sides opposite;
        private PreSet toSet = null;
        private Colors [][] sideToSet = null;
        private int currentNeighbour = 0;
        public Colors [][] colors = new Colors[3][3]; // 3x3 цветных кубика на стороне
        //связанные стороны bound должны задаваться в порядке по часовой стрелке, смотря из центра куба
        //например: для стороны (1) порядок будет верхняя прававя нижняя левая
        public Side (Sides current, Sides[] bound, Sides opposite) {
            if (bound.length != 4) throw new IllegalArgumentException();
            this.current = current;
            this.opposite = opposite;
            this.bound = bound;
            Colors c =  Colors.values()[current.ordinal()];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    colors[i][j] = c;
                }
            }
        }
        public Colors[] getLine(int index) {
            if (index < -1 && index > 3) throw new IndexOutOfBoundsException("Index should be within 0..2");
            return colors[index].clone();
        }
        public Colors[] getRow(int index) {
            if (index < -1 && index > 3) throw new IndexOutOfBoundsException("Index should be within 0..2");
            return new Colors[] {colors[0][index], colors[1][index], colors[2][index]};
        }
        private void setLine(Colors[] line, int index) {
            if (line.length != 3) throw new IllegalArgumentException("There are only 3 points in line");
            if (index < -1 && index > 3) throw new IndexOutOfBoundsException("Index should be within 0..2");
            colors[index] = line.clone();
        }
        private void setRow(Colors[] row, int index) {
            if (row.length != 3) throw new IllegalArgumentException("There are only 3 points in row");
            if (index < -1 && index > 3) throw new IndexOutOfBoundsException("Index should be within 0..2");
            colors[0][index] = row[0];
            colors[1][index] = row[1];
            colors[2][index] = row[2];
        }
        //переходит нв следующую соседнюю сторону в списке и возвращает ее
        private Side next() {
            return getSide(bound[(++currentNeighbour) % 4]);
        }
        //только возвращает следующую соседнюю сторону в списке, но не переходит на нее
        private Side lookNext() {
            return getSide(bound[(currentNeighbour + 1) % 4]);
        }
        //переходит на предующую соседнюю сторону в списке и возвращает ее
        private Side previous() {
            if (currentNeighbour == 0)
                currentNeighbour = 4;
            return getSide(bound[(--currentNeighbour) % 4]);
        }
        //только возвращает предыдудщую соседнюю сторону в списке
        private Side lookPrevious() {
            if (currentNeighbour == 0)
                currentNeighbour = 4;
            return getSide(bound[(currentNeighbour - 1) % 4]);
        }
        private void applyChanges() {
            if (toSet == null) throw new IllegalStateException();
            if (toSet.isLine) {
                setLine(toSet.colors, toSet.index);
            } else {
                setRow(toSet.colors, toSet.index);
            }
            toSet = null;
        }
        private void setChanges(boolean isLine, int index, Colors[] colors) {
            toSet = new PreSet(isLine, index, colors);
        }
        private void applySide() {
            if (sideToSet == null) throw new IllegalStateException();
            colors = sideToSet.clone();
            sideToSet = null;
        }
        private void setSide(Colors [][] side) {
            if (side.length != 3 || side[0].length != 3) throw new IllegalArgumentException();
            sideToSet = side.clone();
        }
        public void transpose(Direction direction) {
            Colors[][] newSide = new Colors[3][3];
            if (direction == Direction.CONTRACLOCKWISE) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newSide[i][j] = colors[2 - j][i];
                    }
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newSide[i][j] = colors[j][2-i];
                    }
                }
            }
            colors = newSide;
        }
        //поворот стороны
        public void flip(Direction direction) {
            if (direction == Direction.CLOCKWISE) {
                next().setChanges(false, 0, lookPrevious().getLine(2));
                next().setChanges(true, 0, lookPrevious().getRow(0));
                next().setChanges(false, 2, lookPrevious().getLine(0));
                next().setChanges(true, 2, lookPrevious().getRow(2));
            } else {
                previous().setChanges(false, 2, lookNext().getLine(2));
                previous().setChanges(true, 0, lookNext().getRow(2));
                previous().setChanges(false, 0, lookNext().getLine(0));
                previous().setChanges(true, 2, lookNext().getRow(0));
            }
            this.transpose(direction);
            for(Sides side: bound) {
                getSide(side).applyChanges();
            }
        }
        //поворот только по часовой стрелке относительно выбранной стороны
        public void flipMiddleLine(boolean isVertical) {
            if (isVertical) {
                next().setChanges(false, 1, lookPrevious().getLine(1));
                next().setChanges(true, 1, lookPrevious().getRow(1));
                next().setChanges(false, 1, lookPrevious().getLine(1));
                next().setChanges(true, 1, lookPrevious().getRow(1));
            } else {
                for (int i = 0; i < 4; i++) {
                    previous().setChanges(true, 1, lookNext().getLine(1));
                }
            }
            for(Sides side: bound) {
                getSide(side).applyChanges();
            }
        }
        //только по часовой стрелке
        public void flipCubeAround() {
            for (int i = 0; i < 4; i++)
                next().setSide(lookPrevious().colors);
            for(Sides side: bound) {
                getSide(side).applySide();
            }
            this.transpose(Direction.CLOCKWISE);
            getSide(opposite).transpose(Direction.CONTRACLOCKWISE);
        }
        private final class PreSet {
            public boolean isLine;
            public int index;
            public Colors [] colors;
            public PreSet(boolean isLine, int index, Colors[] colors) {
                this.index = index;
                this.isLine = isLine;
                this.colors = colors.clone();
            }
        }
        @Override
        public boolean equals(Object other) {
           if (other.getClass() != this.getClass()) return false;
           Side side = (Side) other;
           return (Arrays.deepEquals(this.colors, side.colors) && Arrays.deepEquals(this.bound, side.bound)
                   && opposite.equals(side.opposite));
        }
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    str.append(colors[i][j]).append(" ");
                }
                str.append("\r\n");
            }
            return str.toString();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass())
            return false;
        Cube other = (Cube) object;
        return Arrays.deepEquals(this.sides, other.sides);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++ ) {
            str.append("Side ").append(i + 1).append(": ").append(Sides.values()[i]).append(" \r\n\n")
                    .append(sides[i]).append("\r\n");
        }
        return str.toString();
    }

    public String currentPosition(Sides side, boolean isLine, int num) {
        StringBuilder str = new StringBuilder();
        if (num < 0 && num > 2) throw new IndexOutOfBoundsException("Index should be within 0..2");
        if (isLine) {
            str.append("Side ").append(Arrays.toString(getSide(side).getLine(num)));
        } else {
            str.append("Side ").append(Arrays.toString(getSide(side).getRow(num)));
        }
        return str.toString();
    }
}
