
package edu.jsu.mcis.cs310.tas_sp22;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class TAS {
    public static void main(String[] args){
         TASDatabase db = new TASDatabase("tasuser","War Room D", "localhost");
        
        if (db.isConnected()){
            System.err.println("Your Have Successfully Connected To The Database");
        }
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
        int totalMinutesWorked = 0;
        int totalWithLunch = 0;
        int startHours = 0;
        int startMinutes = 0;
        int stopHours = 0;
        int stopMinutes = 0;
        boolean pair = false;
        LocalDateTime punches;
        int lunchDuration = (int) shift.getLunchDuration();
        int calculations = 0;
        
        for (Punch p : dailypunchlist){
            if ( p.getPunchtype() == PunchType.CLOCK_IN || p.getPunchtype() == PunchType.CLOCK_OUT){
                
                if (p.getPunchtype() == PunchType.CLOCK_IN){
                    pair = false;
                }
                
                if (p.getPunchtype() == PunchType.CLOCK_OUT){
                    pair = true;
                }
            }
            
            if (pair == false){
                punches = p.getAdjustedtimestamp();
                startHours = punches.getHour();
                startMinutes = punches.getMinute();
                
            }
            
            else if (pair == true){ 
                punches = p.getAdjustedtimestamp();
                stopHours = punches.getHour();
                stopMinutes = punches.getMinute();
                totalWithLunch = ((stopHours - startHours) * 60) + (stopMinutes - startMinutes);
                
                if (totalWithLunch > shift.getlunchthreshold()){
                    calculations = totalWithLunch - lunchDuration;
                    totalMinutesWorked = totalMinutesWorked + calculations;
                }
                
                else if (totalWithLunch <= shift.getlunchthreshold()){
                calculations = ((stopHours - startHours) * 60) + (stopMinutes - startMinutes);
                totalMinutesWorked = totalMinutesWorked + calculations; 
                }
                
            }
        }
       
    return totalMinutesWorked;

}
}
