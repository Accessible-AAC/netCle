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
package lyricom.netCleConfig.solutions;

import lyricom.netCleConfig.model.ActionType;
import lyricom.netCleConfig.model.Model;
import lyricom.netCleConfig.model.SaAction;
import lyricom.netCleConfig.model.SensorGroup;
import lyricom.netCleConfig.model.Trigger;
import lyricom.netCleConfig.model.Triggers;

/**
 *
 * @author Andrew
 */
public class ToggleMouse extends SolutionBase {
    private static final String UP_DOWN = SRes.getStr("TMW_UP_DOWN");
    private static final String LEFT_RIGHT = SRes.getStr("TMW_LEFT_RIGHT");
    private static final String[] ORIENTATION = {UP_DOWN, LEFT_RIGHT};
    private static final String YES = SRes.getStr("TMW_YES");
    private static final String NO = SRes.getStr("TMW_NO");
    private static final String[] YES_NO = {YES, NO};
    
    public ToggleMouse( SolutionsUI ui, SensorGroup sg ) {
        super(ui, sg);
    }
    
    @Override
    boolean doSolution() {
        Location btnLocHi = getButton();
        if (btnLocHi == null) return false;
                
        SaAction mouse = mouseSelection();
        if (cancelling) return false;
        
        String orientation = theUI.getOption(SRes.getStr("TMW_ORIENTATION"), ORIENTATION);
        if (cancelling) return false;

        int delay = theUI.getDelay(SRes.getStr("TWM_DELAY"), 500);
        if (cancelling) return false;
        
        String beep = theUI.getOption(SRes.getStr("TWM_SOUND"), YES_NO);
        if (cancelling) return false;
        
        btnLocHi.level = Trigger.Level.LEVEL1;        
        Location btnLocLo = btnLocHi.getReverse();        
        Triggers.getInstance().deleteTriggerSet(btnLocHi.sensor);

        int param1, param2;
        if (orientation.equals(UP_DOWN)) {
            param1 = Model.MOUSE_UP;
            param2 = Model.MOUSE_DOWN;
        } else {
            param1 = Model.MOUSE_LEFT;
            param2 = Model.MOUSE_RIGHT;
        }

        SaAction none = Model.getActionByType(ActionType.NONE);
        
        SaAction actionOnChange;
        int paramOnChange;
        if (beep.equals(YES)) {
            actionOnChange = Model.getActionByType(ActionType.BUZZER);
            paramOnChange = (200 << 16) + 100;
        } else {
            actionOnChange = none;
            paramOnChange = 0;
        }
        
        makeTrigger(1, btnLocHi,     0,           none,            0, 2);
        makeTrigger(2, btnLocHi,     0,          mouse,       param1, 2);
        makeTrigger(2, btnLocLo, delay, actionOnChange,paramOnChange, 3);
        makeTrigger(3, btnLocHi,     0,           none,            0, 4);
        makeTrigger(4, btnLocHi,     0,          mouse,       param2, 4);
        makeTrigger(4, btnLocLo, delay, actionOnChange,paramOnChange, 1);

        return true;
    }

}
