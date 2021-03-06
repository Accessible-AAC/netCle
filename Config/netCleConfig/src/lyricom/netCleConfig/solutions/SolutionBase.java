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

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lyricom.netCleConfig.model.ActionType;
import lyricom.netCleConfig.model.Model;
import lyricom.netCleConfig.model.SaAction;
import lyricom.netCleConfig.model.SensorGroup;
import lyricom.netCleConfig.model.Trigger;
import lyricom.netCleConfig.model.Triggers;
import lyricom.netCleConfig.ui.MainFrame;
import lyricom.netCleConfig.ui.SensorPanel;

/**
 * Base class for all solutions
 * @author Andrew
 */
public abstract class SolutionBase implements Runnable {
    
    protected volatile boolean cancelling = false;
    protected SolutionsUI theUI;
    protected SensorGroup theGroup;
    private Thread solutionThread;
    private Calibrator calibrator;
    
    public SolutionBase(SolutionsUI ui, SensorGroup sg) {
        theUI = ui;
        theGroup = sg;
        calibrator = null;
    }
    
    // called from SolutionsUI on the Swing thread.
    void cancel() {
        cancelling = true;
        if (calibrator != null) {
            calibrator.cancel();
        }
        solutionThread.interrupt();
    }
    
    protected Calibrator getCalibrator() {
        calibrator = new Calibrator(theUI, theGroup);
        return calibrator;
    }
    
    @Override
    public void run() {
        solutionThread = Thread.currentThread();
        if (doSolution() == true) {
            // Success
            SensorPanel.reloadTriggers();
            theUI.solutionComplete();
        }
        if (!cancelling) {
            theUI.solutionComplete();
        }
        theUI = null;
    }
    
    // To be provided by sub-classes.
    // Returns true when solution is complete and new triggers have been made.
    // false under all error and cancel conditions.
    abstract boolean doSolution();
    
    // -------------------------------------------------------
    // Utility routines for common solution actions.
    //
    
    // For single-button targetted solutions, this gets the button.
    protected Location getButton() {
        Calibrator c = getCalibrator();
        c.startCalibration();
        c.getRestValues();
        
        Location btnLocHi = c.getLocation(SRes.getStr("SW_PRESS_BTN"));
        c.endCalibration();
        if (cancelling) return null;
        
        if (btnLocHi == null) {
            JOptionPane.showMessageDialog(theUI,
                    SRes.getStr("SW_BUTTON_FAIL_MSG"),
                    SRes.getStr("SW_SOLUTION_FAIL_TITLE"),
                    JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            if (Triggers.getInstance().isSensorUsed(btnLocHi.sensor)) {
                int result = JOptionPane.showConfirmDialog(null,
                    btnLocHi.sensor.getName() + " " + SRes.getStr("ALREADY_PROGRAMMED_TEXT"),
                    SRes.getStr("ALREADY_PROGRAMMED_TITLE"),
                    JOptionPane.YES_NO_OPTION); 
                if (result == JOptionPane.NO_OPTION) {
                    return null;
                }    
            }
            MainFrame.TheFrame.showGroupPanel(btnLocHi.sensor.getGroup());
            return btnLocHi;
        }       
    }
    
    private static final String HID_MOUSE = SRes.getStr("SW_HID_MOUSE");
    private static final String BT_MOUSE = SRes.getStr("SW_BT_MOUSE");
    private static final String[] MOUSE_OPTS = {HID_MOUSE, BT_MOUSE};
    
    // Get the type of mouse
    protected SaAction mouseSelection() {
        if (cancelling) return null;
        String option = theUI.getOption(SRes.getStr("SW_MOUSE_TYPE"), MOUSE_OPTS);
        if (option == null || cancelling) return null;
        if (option.equals(HID_MOUSE)) {
            return Model.getActionByType(ActionType.HID_MOUSE);
        } else {
            return Model.getActionByType(ActionType.BT_MOUSE);
        }
    }

    void makeTrigger(int startState, Location loc, int delay, 
            SaAction action, int actionParam, int finalState) {
                
        loc.sensor.setLevel( loc.level, loc.value );
        Trigger t = Triggers.getInstance().newTrigger(loc.sensor);
        t.setLevel(loc.level);
        t.setReqdState(startState);
        t.setCondition( loc.condition );
        t.setDelay(delay);
        t.setAction(action);
        t.setActionParam(actionParam);
        t.setActionState(finalState);
    }
}
