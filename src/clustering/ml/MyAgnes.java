/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import java.util.ArrayList;
import weka.clusterers.AbstractClusterer;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Moch Ginanjar Busiri
 */
public class MyAgnes extends AbstractClusterer {

    public static final int SINGLE = 0;
    public static final int COMPLETE = 1;
    public static DistanceFunction distanceFunction = new EuclideanDistance();
    public int numCluster;
    public ArrayList<Cluster> clusters = new ArrayList<>();
    public Instances instances;
    public int[] resultClustering = new int[instances.numInstances()];

    public int linkAge;

    public MyAgnes(int numCluster, int linkType) {
        this.numCluster = numCluster;
        linkAge = linkType;

    }

    public double getDistanceCluster(Cluster c1, Cluster c2) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < c1.getCluster().size(); i++) {
            for (int j = 0; j < c2.getCluster().size(); j++) {
                double distance = distanceFunction.distance(c1.getCluster().get(i), c2.getCluster().get(j));
                if (distance < min) {
                    min = distance;
                }
                if (distance > max) {
                    max = distance;
                }
            }
        }
        if (linkAge == SINGLE) {
            return min;
        } else if (linkAge == COMPLETE) {
            return max;
        }

        return 0;
    }

    @Override
    public void buildClusterer(Instances data) throws Exception {
        instances = data;
        int nInstance = instances.numInstances();
        if (nInstance == 0) {
            return;
        }
        for (int i = 0; i < nInstance; i++) {
            ArrayList<Instance> arrtemp = new ArrayList<>();
            arrtemp.add(instances.get(i));
            Cluster temp = new Cluster(arrtemp);
            clusters.add(temp);
        }
        for (int i = 0; i < nInstance - numCluster; i++) {
            double max = Double.MAX_VALUE;
            int c1 = -1;
            int c2 = -1;
            for (int j = 0; j < clusters.size(); j++) {
                for (int k = j + 1; k < clusters.size(); j++) {
                    double dist = getDistanceCluster(clusters.get(j), clusters.get(k));
                    if (max > dist) {
                        max = dist;
                        c1 = j;
                        c2 = k;
                    }

                }
            }
            clusters.get(c1).merge(clusters.get(c2));
            clusters.remove(c2);

        }

    }

    @Override
    public int numberOfClusters() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int clusterInstance(Instance instance) {
        double max=Double.MAX_VALUE;
        int res=-1;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = 0; j < clusters.get(i).getCluster().size(); j++) {
                double temp= distanceFunction.distance(instance,clusters.get(i).getCluster().get(j));
                if(max>temp){
                    max=temp;
                    res=i;
                }
            }
        }
        return res;
    }
}
