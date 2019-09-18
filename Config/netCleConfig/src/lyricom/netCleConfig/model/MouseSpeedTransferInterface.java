/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
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
    along with this netClé Arduino software.  
    If not, see <https://www.gnu.org/licenses/>.   
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package lyricom.netCleConfig.model;

/**
 * Interface for transfer of mouse speed data.
 * Transfers consist of 5 integers - 3 speeds and 2 intervals.
 * @author Andrew
 */
public interface MouseSpeedTransferInterface {

    public int[] getSpeeds();
    public void setSpeeds(int[] values);
}
