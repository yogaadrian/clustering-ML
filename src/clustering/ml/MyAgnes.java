/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import weka.clusterers.AbstractClusterer;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instances;

/**
 *
 * @author Moch Ginanjar Busiri
 */
public class MyAgnes extends AbstractClusterer{
    public static DistanceFunction distanceFunction = new EuclideanDistance();
    
    public enum LinkAge {
        SINGLE, COMPLETE
    }
    
    public static double getDistanceCluster(Cluster c1, Cluster c2, LinkAge linkage) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        
        for (int i = 0; i < c1.getCluster().size(); i++) {
            for (int j = 0; j < c2.getCluster().size(); j++) {
                double distance = distanceFunction.distance(c1.getCluster().get(i), c2.getCluster().get(j));
                if (distance < min) min = distance;
                if (distance > max) max = distance;
            }
        }
        if (linkage == LinkAge.SINGLE) 
            return min;
        else if (linkage == LinkAge.COMPLETE) 
            return max;
        
        return 0;
    }

    @Override
    public void buildClusterer(Instances i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numberOfClusters() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
