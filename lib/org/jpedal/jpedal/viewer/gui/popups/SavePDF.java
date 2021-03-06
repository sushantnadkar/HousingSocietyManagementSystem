/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/support/
 *
 * (C) Copyright 1997-2015 IDRsolutions and Contributors.
 *
 * This file is part of JPedal/JPDF2HTML5
 *
 
 *
 * ---------------
 * SavePDF.java
 * ---------------
 */
package org.jpedal.examples.viewer.gui.popups;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.print.attribute.standard.PageRanges;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jpedal.utils.LogWriter;
import org.jpedal.utils.Messages;
import org.jpedal.examples.viewer.gui.GUI;


public class SavePDF extends Save{
	
	JLabel OutputLabel = new JLabel();
	final ButtonGroup buttonGroup1 = new ButtonGroup();
	final ButtonGroup buttonGroup2 = new ButtonGroup();
	
	final JToggleButton jToggleButton3 = new JToggleButton();
	
	final JToggleButton jToggleButton2 = new JToggleButton();
	
	final JRadioButton printAll=new JRadioButton();
	final JRadioButton printCurrent=new JRadioButton();
	final JRadioButton printPages=new JRadioButton();
	
	final JTextField pagesBox=new JTextField();
	
	final JRadioButton exportSingle=new JRadioButton();
	final JRadioButton exportMultiple=new JRadioButton();
	
	public SavePDF( final String root_dir, final int end_page, final int currentPage ) {
		
		super(root_dir, end_page, currentPage);
		
		try{
			jbInit();
		}catch( final Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * get root dir
	 */
    public final int[] getExportPages(){
		
		int[] pagesToExport=null;
		
		if(printAll.isSelected()){
			pagesToExport=new int[end_page];
			for(int i=0;i<end_page;i++) {
                pagesToExport[i] = i + 1;
            }
			
		}else if( printCurrent.isSelected() ){
			pagesToExport=new int[1];
			pagesToExport[0]=currentPage;
			
		}else if( printPages.isSelected() ){
			
			try{
				final PageRanges pages=new PageRanges(pagesBox.getText());
				
				int count=0;
				int i = -1;
				while ((i = pages.next(i)) != -1) {
                    count++;
                }
				
				pagesToExport=new int[count];
				count=0;
				i = -1;
				while ((i = pages.next(i)) != -1){
					if(i > end_page){
                        if(GUI.showMessages) {
                            JOptionPane.showMessageDialog(this, Messages.getMessage("PdfViewerText.Page") + ' '
                                    + i + ' ' + Messages.getMessage("PdfViewerError.OutOfBounds") + ' ' +
                                    Messages.getMessage("PdfViewerText.PageCount") + ' ' + end_page);
                        }
						return null;
					}
					pagesToExport[count]=i;
					count++;
				}
			}catch (final IllegalArgumentException  e) {
				LogWriter.writeLog( "Exception " + e + " in exporting pdfs" );
                if(GUI.showMessages) {
                    JOptionPane.showMessageDialog(this, Messages.getMessage("PdfViewerError.InvalidSyntax"));
                }
			}
		}
		
		return pagesToExport;
		
	}
	
	public final boolean getExportType(){
		return exportMultiple.isSelected();
	}
	
	private void jbInit() throws Exception{
		
		rootFilesLabel.setBounds( new Rectangle( 13, 13, 400, 26 ) );
		
		rootDir.setBounds( new Rectangle( 23, 40, 232, 23 ) );
		
		changeButton.setBounds( new Rectangle( 272, 40, 101, 23 ) );

//		rootDir.setBounds( new Rectangle( 23, 39, 232, 23 ) );
//		changeButton.setBounds( new Rectangle( 272, 39, 101, 23 ) );
		
		pageRangeLabel.setBounds( new Rectangle( 13, 71, 300, 26 ) );
		
		printAll.setText(Messages.getMessage("PdfViewerRadioButton.All"));
		printAll.setBounds( new Rectangle( 23, 100, 75, 22 ) );
		
		printCurrent.setText(Messages.getMessage("PdfViewerRadioButton.CurrentPage"));
		printCurrent.setBounds( new Rectangle( 23, 120, 120, 22 ) );
		printCurrent.setSelected(true);
		
		printPages.setText(Messages.getMessage("PdfViewerRadioButton.Pages"));
		printPages.setBounds( new Rectangle( 23, 142, 70, 22 ) );
		
		pagesBox.setBounds( new Rectangle( 95, 142, 200, 22 ) );
		pagesBox.addKeyListener(new KeyListener(){
			@Override
            public void keyPressed(final KeyEvent arg0) {}
			
			@Override
            public void keyReleased(final KeyEvent arg0) {
				if(pagesBox.getText().isEmpty()) {
                    printCurrent.setSelected(true);
                } else {
                    printPages.setSelected(true);
                }
				
			}
			
			@Override
            public void keyTyped(final KeyEvent arg0) {}
		});
		
		final JTextArea pagesInfo=new JTextArea(Messages.getMessage("PdfViewerMessage.PageNumberOrRange")+ '\n' +
				Messages.getMessage("PdfViewerMessage.PageRangeExample"));
		pagesInfo.setBounds(new Rectangle(23,165,400,40));
		pagesInfo.setOpaque(false);
		
		optionsForFilesLabel.setBounds( new Rectangle( 13, 220, 400, 26 ) );
		
		exportMultiple.setText(Messages.getMessage("PdfViewerTitle.ExportMultiplePDFPages"));
		exportMultiple.setBounds( new Rectangle( 23, 250, 350, 22 ) );
		exportMultiple.setSelected(true);
		
		exportSingle.setText(Messages.getMessage("PdfViewerTitle.ExportSinglePDFPages"));
		exportSingle.setBounds( new Rectangle( 23, 270, 300, 22 ) );
		
		this.add( printAll, null );
		this.add( printCurrent, null );
		
		this.add( printPages, null );
		this.add( pagesBox, null );
		this.add( pagesInfo, null );
		
		this.add( optionsForFilesLabel, null );
		this.add( exportSingle, null );
		this.add( exportMultiple, null );
		
		this.add( rootDir, null );
		this.add( rootFilesLabel, null );
		this.add( changeButton, null );
		this.add( pageRangeLabel, null );
		
		this.add( jToggleButton2, null );
		this.add( jToggleButton3, null );
		
		buttonGroup1.add( printAll );
		buttonGroup1.add( printCurrent );
		buttonGroup1.add( printPages );
		
		buttonGroup2.add( exportMultiple );
		buttonGroup2.add( exportSingle );
		
		
	}
	
	
}
