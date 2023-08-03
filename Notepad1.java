import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

class Notepad1 extends Frame implements WindowListener,ActionListener,ItemListener{

		MenuItem newI, openI, saveI, saveAsI, exitI, cutI, copyI, pasteI, findI, replaceI ;
		Button nxt, nxt1, sav, dSav, cncl,cncl1,cncl2,okB,rpl,rplAll;
		TextArea tA ;								TextField tf1,tf2,tf3,tf4 ;
		MenuBar mb ;								Menu m1, m2, m3, m4 ;
		Label l1, l2,l3,l4,notL ;					FileDialog fd,fd1 ;										
		CheckboxMenuItem boldCi, italicCi ;			Dimension d ;		
		Dialog findD,saveD,notFound,replaceD;		File file1 ;	
		Color clr1, clr2 ;							Panel pnl1 ;											
		int strt, searchIndex ;						boolean bool1 ;
		String sb ="", str1,str3, str2 = "",replacestr, searchStr, text, sourceStr, path1, name1,sourceName,sourceName1;
		  
		
		public Notepad1(){

			//MAIN FRAME.... 
			
			this.addWindowListener(this);				this.setSize(1800, 980);
			d = new Dimension() ;					d = this.getSize();
			
			mb = new MenuBar();
			m1 = new Menu("File");					m2 = new Menu("Edit");
			m3 = new Menu("Font");					m4 = new Menu("Others");
			
			newI = new MenuItem("New");				openI = new MenuItem("Open ");
			saveI = new MenuItem("Save ");			exitI = new MenuItem("Exit");
			findI = new MenuItem("Find");			replaceI = new MenuItem("Find & Replace");
			cutI = new MenuItem("Cut");				copyI = new MenuItem("Copy");
			pasteI = new MenuItem("Paste");			saveAsI = new MenuItem("Save As");
			
			newI.addActionListener(this);			openI.addActionListener(this);
			saveI.addActionListener(this);			exitI.addActionListener(this);
			findI.addActionListener(this);			replaceI.addActionListener(this);
			cutI.addActionListener(this);			copyI.addActionListener(this);
			pasteI.addActionListener(this);			saveAsI.addActionListener(this);	

			boldCi = new CheckboxMenuItem("Bold");		boldCi.addItemListener(this);
			italicCi = new CheckboxMenuItem("Italic");	italicCi.addItemListener(this);					
			
			m4.add(cutI);		m4.add(copyI);		m4.add(pasteI);
			m3.add(boldCi);		m3.add(italicCi);
			m2.add(findI);		m2.add(replaceI);	m2.add(m3);			m2.add(m4);
			m1.add(newI);		m1.add(openI);		m1.add(saveI);		m1.add(saveAsI);		m1.addSeparator();		m1.add(exitI);		
			mb.add(m1);			mb.add(m2);
			
			tA = new TextArea();					add(tA);		
			tA.setFont(new Font("Arial",Font.PLAIN+Font.BOLD,23));
			clr1 = new Color(255,255,255);				tA.setBackground(clr1);
			clr2 = new Color(0,0,0);			tA.setForeground(clr2);
			
			fd = new FileDialog(this,"Selection",FileDialog.SAVE);
			fd1 = new FileDialog(this,"SELECT",FileDialog.LOAD);				
			
			setMenuBar(mb);	setVisible(true);
			
		
			// SAVE-BOX FRAME...	
			
			saveD = new Dialog(this, "Find", false);        saveD.setLayout(new FlowLayout());
			saveD.setSize(300, 100);						saveD.setLocation(d.width/2-150,d.height/3);
			saveD.addWindowListener(this);
			
			sav = new Button("Save  ");						dSav = new Button("Don't Save");
			cncl = new Button("Cancel");					pnl1 = new Panel();
			l1 = new Label("    Do You Want To Save To Untitled ?");
			
			sav.addActionListener(this);					dSav.addActionListener(this);
			cncl.addActionListener(this);

			saveD.add(l1);									pnl1.add(sav);
			pnl1.add(dSav);									pnl1.add(cncl);		
			saveD.add(pnl1);
			
			
		
			//FIND DIALOG....
			 
			findD = new Dialog(this, "Find", false);       findD.setLayout(new FlowLayout());
			findD.setSize(300, 120);					   findD.setLocation(d.width/2-150,d.height/3);
			findD.addWindowListener(this);		     	   l2 = new Label("Find What :");
			nxt = new Button("Find Next"); 				   cncl1 = new Button("Cancel ");
			tf1 = new TextField(23);

			nxt.addActionListener(this);				   cncl1.addActionListener(this);

			findD.add(l2);								   findD.add(tf1);
			findD.add(nxt);							       findD.add(cncl1);
			
			
			//FIND-REPLACE DIALOG....
				 
			replaceD = new Dialog(this, "Find", false);    replaceD.setLayout(new FlowLayout());
			replaceD.setSize(350, 160);					   replaceD.setLocation(d.width/2-150,d.height/3);
			replaceD.addWindowListener(this);
			l3 = new Label("Find What :     ");			   tf3 = new TextField(23);
			l4 = new Label("Replace With :");		       tf4 = new TextField(25);
			nxt1 = new Button("Next ");				 	   rpl = new Button("Replace");
			rplAll = new Button("Replace All");		       cncl2 = new Button("Cancel  ");

			nxt1.addActionListener(this);				   rpl.addActionListener(this);
			rplAll.addActionListener(this);				   cncl2.addActionListener(this);

			replaceD.add(l3);							   replaceD.add(tf3);
			replaceD.add(l4);				   			   replaceD.add(tf4);
			replaceD.add(nxt1);							   replaceD.add(rpl);
			replaceD.add(rplAll);						   replaceD.add(cncl2);	
			

			//NOT FOUND DIALOG....
			
			notFound = new Dialog(this, "Text Not Found ", true);
			notFound.setLayout(new FlowLayout());          notFound.setSize(200, 100);
			notFound.addWindowListener(this);			   notFound.setLocation(d.width/2,d.height/2);
			okB = new Button("OK");					   	   okB.addActionListener(this); 
			notL = new Label("       Text not found....       ");               
				   
			notFound.add(notL);							   notFound.add(okB);
								
		}
	
		
		public void actionPerformed(ActionEvent e){	
			String o = e.getActionCommand();
			switch(o){
				case "New" :
									checkNew();									
									break;
								
				case "Open " :
									checkOpen();						
									break;
				
				case "Save " :
									saveFile();
									break;
								
				case "Save As" :						
									fileSave();
									break;
								
				case "Exit" :
									checkExit();									
									break;
												
				case "Save  " :		
									saveD.dispose();
									fileSave();									
									break;
								
				case "Don't Save" :	
								
									saveD.dispose();
									if(sourceName == "newI"){
										tA.setText("");
									}	
									if(sourceName == "openI"){
										fileOpen();
									}else
									if(sourceName == "exitI"){
										System.exit(0);
									}
									break;
								
				case "Cancel" :
								saveD.dispose();
								break;
								
				case "Find" :
								
								sourceStr = tA.getText();
								str3 = tA.getSelectedText();
								tf1.setText(str3);
								tA.setText(sourceStr);
								searchIndex = tA.getCaretPosition();
								findD.setVisible(true);
								break;
								
								
				case "Cut"	:
								str2 = tA.getSelectedText();
								tA.replaceText("",tA.getSelectionStart(),tA.getSelectionEnd());
								break;
				case "Copy"	:
								str2 = tA.getSelectedText();
								break;
								
				case "Paste" :
								int i = tA.getCaretPosition();
								if(str2.length() > 0)
								tA.insertText(str2,i);
								break;
								
				case "Find Next"	:
								nextSearch();
								break;
				
				case "Cancel " :
								findD.dispose();
								break;	
				
				case "OK"	   :
							    notFound.dispose();
								break;	
								
				case "Find & Replace" :
								sourceStr = tA.getText();
								str3 = tA.getSelectedText();
								tf3.setText(str3);
								tA.setText(sourceStr);
								searchIndex = tA.getCaretPosition();
								replaceD.setVisible(true);
								break;
				case "Next "	:
								nextSearch1();
								break;				
				
				case "Replace" :
								replaceWith();
								break;
				case "Replace All" :
								replaceAllWith();	
								break;
				case "Cancel  " :
								replaceD.dispose();
								break;					
								
			}
			
												
		}
		
		public void itemStateChanged(ItemEvent e){
			
			if(italicCi.getState() == true && boldCi.getState() == false){
				tA.setFont(new Font("Arial",Font.PLAIN+Font.ITALIC,27));
			
			}else
			if(italicCi.getState() == false && boldCi.getState() == false){
				tA.setFont(new Font("Arial",Font.PLAIN,27));
				
			}else
			if(italicCi.getState() == false && boldCi.getState() == true){
				tA.setFont(new Font("Arial",Font.PLAIN+Font.BOLD,27));

			}else
			if(italicCi.getState() == true && boldCi.getState() == true){
				tA.setFont(new Font("Arial",Font.PLAIN+Font.ITALIC+Font.BOLD,27));
			}
							
		}
		
		
		public void nextSearch(){
					
			text = tA.getText();
            searchStr = tf1.getText();
			if(searchStr.length() > 1){
				sourceStr = text.replace("\n","") ;
				strt = sourceStr.indexOf(searchStr, searchIndex);
				if (strt == -1) {
					notFound.setVisible(true);          
				} else {
					tA.requestFocus();
					tA.select(strt, strt + searchStr.length());
					searchIndex = tA.getCaretPosition() + 1;
				}	
			}	
    			
		}
		public void nextSearch1(){
					
			text = tA.getText();
            searchStr = tf3.getText();
            sourceStr = text.replace("\n","") ;
            strt = sourceStr.indexOf(searchStr,searchIndex);
            if (strt == -1) {
				notFound.setVisible(true);  
            } else {
                tA.requestFocus();
                tA.select(strt, strt + searchStr.length());
                searchIndex = strt + searchStr.length();
            }				
    			
		}
		
		
		
		public void replaceWith(){
					
			text = tA.getText();
            searchStr = tf3.getText();
			replacestr = tf4.getText();
            sourceStr = text.replace("\n","") ;			
            strt = sourceStr.indexOf(searchStr, strt+1);
            if (strt == -1) {
				notFound.setVisible(true); 
            } else {
                tA.requestFocus();
				tA.replaceRange(replacestr,strt,strt+searchStr.length());		
                tA.select(strt,strt+replacestr.length());
				strt = strt + searchStr.length();

            }				
    			
		}
		
		public void replaceAllWith(){
			text = tA.getText();
			searchStr = tf3.getText();
			replacestr = tf4.getText();
			sourceStr = text.replace("\n","") ;			
			strt = sourceStr.indexOf(searchStr, strt+1);
			if(strt == -1){
				notFound.setVisible(true);
			}else{
			text = tA.getText();
            searchStr = tf3.getText();
			replacestr = tf4.getText();
            String newText = text.replace(searchStr,replacestr);
			tA.setText(newText);
			}
			
		}
	
		
		public void fileSave(){
			
			fd.setVisible(true);
			name1 = fd.getFile();
			path1 = fd.getDirectory();
			if(name1 != null && path1 != null){
			
				try{
					File file = new File(path1,name1);
					FileOutputStream fos = new FileOutputStream(file);
					
					BufferedOutputStream bos = new BufferedOutputStream(fos); 
					str1 = tA.getText();
					bos.write(str1.getBytes());
					bos.close();
					bool1 = true ;
				}
				catch(IOException e1){
					System.out.println(e1.getMessage());
				}
				
				if(sourceName == "openI" && bool1){
					fd.dispose();
					fileOpen();
				}else
				if(sourceName == "exitI" && bool1){
					fd.dispose();
					System.exit(0);
				}	
			}
			
			
		}
		
		
		public void saveFile(){
			if(tA.getText().length() < 1){
					fileSave();
				}else
			
			if( tA.getText().length() != sb.length() && sourceName1 == "openI"){
				
				try{
					
					FileOutputStream fos = new FileOutputStream(file1);
					BufferedOutputStream bos = new BufferedOutputStream(fos); 
					str1 = tA.getText();
					bos.write(str1.getBytes());
					bos.close();
				}
				catch(IOException e1){
					System.out.println(e1.getMessage());
				}
				
				sb = tA.getText();
			}else
			if(tA.getText().length() > 1){
					fileSave();
				
			}
				
		}
		
		public void fileOpen(){
			fd1.setVisible(true);
				name1 = fd1.getFile();
				path1 = fd1.getDirectory();
			if(name1 != null && path1 != null){
				try{
					File file = new File(path1,name1);
					file1 = new File(path1,name1);
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis); 
					tA.setText("");
					int ch;
					sb = "";
					while((ch = bis.read()) != -1){
						char ch1 = (char)ch;
						sb += ch1;						
					}
					tA.setText(sb);
					sourceName1 = "openI";
				}
				catch(IOException e1){
					System.out.println(e1.getMessage());
				}
			}		
		}
		
		
		public void checkNew(){
			str1 = tA.getText();
			sourceName = "newI";
			if(sourceName1 == "openI" && str1.equals(sb)){
				tA.setText("");
				sourceName1 = "";
			}else
			if(str1.length() > 0){
				saveD.setLocation(d.width/2-150,d.height/2);
				saveD.setVisible(true);							
			}else{
				tA.setText("");
			}
		}
		
		
		public void checkOpen(){
			str1 = tA.getText();
			if(str1.length() < 1){
				fileOpen();
			}else
			if(str1.equals(sb)){
				fileOpen();
			}else
			if(str1.length() > 0 ){
				saveD.setLocation(d.width/2-150,d.height/2);
				sourceName = "openI";
				saveD.setVisible(true);	
			}else
			if(!str1.equals(sb)){
				saveD.setLocation(d.width/2-150,d.height/2);
				sourceName = "openI";
				saveD.setVisible(true);							
			}
		}
		
		
		public void checkExit(){				
			str1 = tA.getText();
			sourceName = "exitI";
			if(sourceName1 == "openI" && str1.equals(sb)){
				System.exit(0);
			}else
			if(str1.length() > 0){
				saveD.setLocation(d.width/2-150,d.height/2);
				saveD.setVisible(true);							
			}else{
				System.exit(0);
			}
		}
		
		
		public void windowClosing(WindowEvent e){
			Window w = e.getWindow();
			if(e.getSource() == this){
				checkExit();	
			}
			w.setVisible(false);
			w.dispose();
		}
		public void  windowDeactivated(WindowEvent e){
			
		}
		public void  windowActivated(WindowEvent e){
			
		}
		public void  windowDeiconified(WindowEvent e){
			
		}
		public void  windowIconified(WindowEvent e){
			
		}
		public void  windowClosed(WindowEvent e){
			
		}
		public void  windowOpened(WindowEvent e){
			
		}
		
		
		public static void main(String args[]){
			Notepad1 np = new Notepad1();
			
		}
}