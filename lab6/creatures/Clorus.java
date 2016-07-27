package creatures;

import huglife.*;

import java.awt.Color;
import java.awt.image.DirectColorModel;
import java.util.List;
import java.util.Map;

/**
 * Created by varad on 7/27/16.
 */
public class Clorus extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy *= 0.5;
        Clorus offspring = new Clorus(energy);
        return offspring;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    private Direction getRandom(List<Direction> n) {
        int index = HugLifeUtils.randomInt(n.size() - 1);
        Direction random = n.get(index);
        return random;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> emptySpaces = getNeighborsOfType(neighbors, "empty");
        List<Direction> plipNeighbors = getNeighborsOfType(neighbors, "plip");

        if (emptySpaces.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipNeighbors.size() >= 1) {
            Direction attackDirection = getRandom(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, attackDirection);
        } else if (energy >= 1) {
            Direction replicateDirection = getRandom(emptySpaces);
            return new Action(Action.ActionType.REPLICATE, replicateDirection);
        }

        Direction moveDirection = getRandom(emptySpaces);
        return new Action(Action.ActionType.MOVE, moveDirection);
    }


}
