/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

/**
 *
 * @author Moch Ginanjar Busiri
 */
public class LoadData {
    public static Instances getData(String filepath) throws Exception {
        DataSource source = new DataSource(filepath);
        Instances data = source.getDataSet();
        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        return data;
    }
}
