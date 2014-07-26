/*******************************************************************************
* @ Year 2013
* This is the source code of the following papers. 
* 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
* 
* 
* Please contact the author Hien To, ubriela@gmail.com if you have any question.
*
* Contributors:
* Hien To - initial implementation
*******************************************************************************/

package org.geocrowd.setcover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class SetCoverGreedyCombineDeadline.
 * 
 * @author Luan
 */
public class SetCoverGreedy_CloseToDeadline extends SetCover {

     private int k=-4;
   
    /**
	 * Instantiates a new sets the cover greedy combine deadline.
	 * 
	 * @param container
	 *            the container
	 * @param current_time_instance
	 *            the current_time_instance
	 */
    public SetCoverGreedy_CloseToDeadline(ArrayList<HashMap<Integer, Integer>> container, Integer current_time_instance) {
        super(container, current_time_instance);
    }
    
    /* (non-Javadoc)
     * @see org.geocrowd.setcover.SetCoverGreedyWaitTillDeadline#minSetCover()
     */
    @Override
    public int minSetCover(){
        ArrayList<HashMap<Integer, Integer>> S = (ArrayList<HashMap<Integer, Integer>>) listOfSets.clone();
        HashSet<Integer> Q = (HashSet<Integer>) universe.clone();
        assignedTaskSet = new HashSet<Integer>();
       
        int set_size = S.size();

        while (!Q.isEmpty()) {
            HashMap<Integer, Integer> maxSet = null;
            double maxElem = -10000000;
            for (HashMap<Integer, Integer> s : S) {

                double newElem = weight(s, currentTimeInstance, assignedTaskSet);
                if (newElem > maxElem && newElem >=k)
                {
                    maxElem = newElem;
                    maxSet = s;
                }
            }
            if(maxSet == null)
                break;

            S.remove(maxSet);
            Q.removeAll(maxSet.keySet());
            assignedTaskSet.addAll(maxSet.keySet());
        }
        assignedTasks = assignedTaskSet.size();
        return set_size - S.size();
    }
    
    /**
	 * Weight.
	 * 
	 * @param s
	 *            the s
	 * @param current_time_instance
	 *            the current_time_instance
	 * @param C
	 *            the c
	 * @return the double
	 */
    private double weight(HashMap<Integer, Integer> s, int current_time_instance, 
            HashSet<Integer> C)
    {
        double w = 0;
        int numElem =0;
        double d =0;
        for(Integer t:s.keySet())
        {
            if(!C.contains(t))
            {
                if(s.get(t)-current_time_instance ==1)
                    return -1;
                numElem ++;
                d += s.get(t)-current_time_instance;
            }
        }
        return -d/numElem;
    }
}