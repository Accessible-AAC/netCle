/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    Copyright (C) 2019 Andrew Hodgson

    This file is part of the netClé Configuration software.

    netClé Configuration software is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    netClé Configuration software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this netClé configuration software.  
    If not, see <https://www.gnu.org/licenses/>.   
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package lyricom.netCleConfig.model;

/**
 *
 * @author Andrew
 */
public class Sensor implements Comparable {
    private final int id;
    private final String name;
    private final int minval;
    private final int maxval;
    private final boolean continuous;
    private final int moveGroup;
    private int currentValue;
    private int level1;
    private int level2;
    private SensorGroup group;
    
    private SensorSignalLevelChangeListener listener;
    
    public Sensor(int id, String name, int min, int max, boolean cont, int move, SensorGroup g) {
        this.id = id;
        this.name = name;
        this.minval = min;
        this.maxval = max;
        this.continuous = cont;
        this.moveGroup = move;
        this.group = g;
        currentValue = 0;
        if (cont) {
            level1 = level2 = (min + max) / 2;
        }
    }
    
    public void setListener(SensorSignalLevelChangeListener l) {
        listener = l;
    }
    
    public void removeListener() {
        listener = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinval() {
        return minval;
    }

    public int getMaxval() {
        return maxval;
    }

    public boolean isContinuous() {
        return continuous;
    }
    
    public int getMoveGroup() {
        return moveGroup;
    }

    public int getCurrentValue() {
        return currentValue;
    }
    
    public SensorGroup getGroup() {
        return group;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        if (listener != null) {
            listener.newSensorValue(currentValue);
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void setLevels(int l1, int l2) {
        level1 = l1;
        level2 = l2;
    }
    
    public int getLevel1() {
        return level1;
    }
    
    public int getLevel2() {
        return level2;
    }
    
    public int getLevel(Trigger.Level level) {
        if (level == Trigger.Level.LEVEL1) {
            return getLevel1();
        } else {
            return getLevel2();
        }
    }
    
    public void setLevel(Trigger.Level level, int value) {
        if (level == Trigger.Level.LEVEL1) {
            level1 = value;
        } else {
            level2 = value;
        }
    }

    @Override
    public int compareTo(Object t) {
        Sensor other = (Sensor) t;
        return (this.name.compareTo(other.name));
    }
}
