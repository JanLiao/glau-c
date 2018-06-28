package com.cvte.client.util;

import java.awt.BorderLayout;  
import java.awt.Container;  
  
import javax.swing.JFrame;  
  
//import com.hg.xdoc.XDocViewer;  
  
public class PDFReader {  
    /** 
     * XDOC阅读器测试 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            JFrame f = new JFrame("XDOC文档阅读器");  
            Container p = f.getContentPane();  
            //实例化XDoc阅读器  
            //XDocViewer v = new XDocViewer();  
            //加入到面板中  
            //p.add(v, BorderLayout.CENTER);  
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            f.setSize(800, 600);  
            f.setVisible(true);  
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);  
            //指定url打开文件  
            //v.open("c:/rtx.docx");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
