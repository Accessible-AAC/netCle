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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class SensorGroup {
    private final GroupID groupID;
    private final List<Sensor> members = new ArrayList<>();
    
    public SensorGroup(GroupID id) {
        this.groupID = id;
    }
    
    public void add(Sensor s) {
        members.add(s);
    }
    
    public String getName() {
        return groupID.toString();
    }
    
    public GroupID getID() {
        return groupID;
    }
    
    public List<Sensor> getMembers() {
        return members;
    }
}
