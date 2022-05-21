/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eltayeb
 */
public class ReportViewer {
    
    private final String reportsPath = "C:\\Users\\eltayeb\\Documents\\NetBeansProjects\\Invoice\\src\\reports\\";
    
    
    public void showReport(String reportName, Map parameters, List reportFieldsDataList )
    {
        String reportURL = reportsPath + reportName ;
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportFieldsDataList);
        
        try
        {
          JasperReport report = JasperCompileManager.compileReport(reportURL);
          JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
          JasperViewer.viewReport(print, false);
        } 
        catch(Exception ex)
        {
            AlertMaker.showErrorAlert(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
