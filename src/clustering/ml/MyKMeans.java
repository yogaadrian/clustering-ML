/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.ml;

import static clustering.ml.MyAgnes.distanceFunction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import weka.clusterers.AbstractClusterer;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.DenseInstance;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

/**
 *
 * @author Azwar
 */
public class MyKMeans extends AbstractClusterer{
    private int numCluster = -1;
    private int numIteration = -1;
    private static DistanceFunction distanceFunction = new EuclideanDistance();
    private Instances instances;
    private Instance[] centroids;
    private ArrayList<Integer>[] clusters;
    
    public MyKMeans(int cluster, int iteration){
        numCluster = cluster;
        numIteration = iteration;
    }
    
    public void printCluster() {
        for (int i = 0; i < centroids.length; i++) {
            System.out.println("cluster  " + i);

            for (int j = 0; j < clusters[i].size(); j++) {
                System.out.println("anggota : "+clusters[i].get(j).toString());
            }
        }
    }

    @Override
    public int clusterInstance(Instance instance) throws Exception {
        double min = Double.MAX_VALUE;
        int cluster = 0;
        for(int i = 0; i < numCluster; i++){
            double dist = distanceFunction.distance(centroids[i], instance);
            if(dist < min){
                min = dist;
                cluster = i;
            }
        }
        return cluster;
    }
    
    @Override
    public void buildClusterer(Instances data) throws Exception {
        instances = data;
//        getCapabilities().testWithFail(instances);
        distanceFunction.setInstances(instances);
        
        if(data.size() == 0){
            throw new RuntimeException("The dataset should not be empty");
        }
        if(numCluster == 0){
            throw new RuntimeException("There should be at least one cluster");
        }
        
        // initialize centroid
        int instanceLength = instances.get(0).numAttributes();
        centroids = new Instance[numCluster];
        int numInstance = instances.size();
        Random rand = new Random(10);
        
        for(int i = 0; i < numCluster; i++){
            int random = rand.nextInt(numInstance);
            centroids[i] = instances.get(random);
        }
        
        int iterationCount = 0;
        boolean isConvergen = false;
        int[] prevAssign = new int[numInstance];
        for(int i = 0; i < numInstance; i++){
            prevAssign[i] = -1;
        }
        ArrayList<Integer>[] tmpClusters = new ArrayList[numCluster];
        for(int i = 0; i < numCluster; i++){
            tmpClusters[i] = new ArrayList<>();
        }
        while(!isConvergen && iterationCount < numIteration){
            iterationCount++;
            isConvergen = true;
            
            for(int i = 0; i < numInstance; i++){
                int clusterResult = clusterInstance(instances.get(i));
                if(prevAssign[i] != clusterResult){
                    isConvergen = false;
                    prevAssign[i] = clusterResult;
                }
                tmpClusters[clusterResult].add(i);
            }
            
            // update centroids
            for(int i = 0; i < numCluster; i++){
                Instances tmpInstances = new Instances(instances, 0);
                int clusterLength = tmpClusters[i].size();
                
                for(int j = 0; j < clusterLength; j++){
                    tmpInstances.add(instances.get(tmpClusters[i].get(j)));
                }
                centroids[i] = moveCentroid(tmpInstances);
            }
        }
        clusters = new ArrayList[numCluster];
        for(int i = 0; i < numCluster; i++){
            clusters[i] = new ArrayList<>();
        }
        for(int i = 0; i < numCluster; i++){
            for(int member: tmpClusters[i]){
                clusters[i].add(member);
            }
        }
    }

    @Override
    public int numberOfClusters() throws Exception {
        return numCluster;
    }
    
    
    public Instance moveCentroid(Instances members){
        double[] vals = new double[members.numAttributes()];
        double[][] nominalDists = new double[members.numAttributes()][];
        double[] weightMissing = new double[members.numAttributes()];
        double[] weightNonMissing = new double[members.numAttributes()];
        
        for (int j = 0; j < members.numAttributes(); j++) {
            if (members.attribute(j).isNominal()) {
                nominalDists[j] = new double[members.attribute(j).numValues()];
            }
        }
        for (int i = 0; i < members.numInstances(); ++i) {
            Instance inst = members.instance(i);
            for (int j = 0; j < members.numAttributes(); j++) {
                if (inst.isMissing(j)) {
                    weightMissing[j] += inst.weight(); 
                }
                else {
                    weightNonMissing[j] += inst.weight();
                    if (members.attribute(j).isNumeric())
                        vals[j] += inst.weight() * inst.value(j);
                    else
                        nominalDists[j][(int)inst.value(j)] += inst.weight();                    
                }
            }      
        }
        for (int i = 0; i < members.numAttributes(); i++) {
            if (members.attribute(i).isNumeric()) {
                if  (weightNonMissing[i] > 0) {
                    vals[i] /= weightNonMissing[i];
                } else {
                    vals[i] = Utils.missingValue();
                }
            }
            else {
                double max = -Double.MAX_VALUE;
                double maxIndex = -1;
                for (int j = 0; j < nominalDists[i].length; j++) {
                    if (nominalDists[i][j] > max) {
                        max = nominalDists[i][j];
                        maxIndex = j;
                    }
                    vals[i] = max < weightMissing[i] ? Utils.missingValue() : maxIndex;                    
                }
            }
        }
        
        return new DenseInstance(1.0, vals);
    }
    
    public Capabilities getCapabilities() {
      Capabilities result = super.getCapabilities();
      result.disableAll();
      result.enable(Capability.NO_CLASS);

      // attributes
      result.enable(Capability.NOMINAL_ATTRIBUTES);
      result.enable(Capability.NUMERIC_ATTRIBUTES);
      result.enable(Capability.MISSING_VALUES);

      return result;
    }

}
