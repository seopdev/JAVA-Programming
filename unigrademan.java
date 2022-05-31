import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class unigrademan extends JFrame{
	ArrayList<addgrade> mem = new ArrayList<addgrade>(); //���α׷� ������ �ӽ������.
	String Path = "c:\\Test\\db.txt"; //�⺻��� ����
	RandomAccessFile rf; //���Ͽ����� �̸� ����
	JTextField tf1, tf4, tf2, tf3, tf5;
	JButton a1 = new JButton("�˻�");
	JButton a2 = new JButton("���� ���");
	JTable table;
	JScrollPane scroll;
	String[] title = {"�й�","�����","����","���","����"};
	//���̺� Ÿ��Ʋ ����
	DefaultTableModel model = new DefaultTableModel(null,title){ 
		//���̺� ���� ���� �Ұ����ϰ� ó��.
		public boolean isCellEditable(int i, int c){ 
			return false; 
		} 
	}; 
	String regNum = "^[0-9]*$"; //���� ���Խ�.
	int check1 = 0; //���Ի� üũ �÷���.
	
	//������ ������� ȯ��.
	public static String Grade(String Grade) {
		String Score = "";
		switch(Grade){
			case "A+":
				Score = "100";
				break;
			case "A0":
				Score = "94";
				break;
			case "B+":
				Score = "89";
				break;
			case "B0":
				Score = "84";
				break;
			case "C+":
				Score = "79";
				break;
			case "C0":
				Score = "74";
				break;
			case "D+":
				Score = "69";
				break;
			case "D0":
				Score = "94";
				break;
			case "F":
				Score = "0";
				break;
		}
		return Score;
	}
	
	//������ �Ҽ������ ȯ��.
	public static String fGrade(String Grade) {
		String Score = "";
		switch(Grade){
			case "A+":
				Score = "4.5";
				break;
			case "A0":
				Score = "4.0";
				break;
			case "B+":
				Score = "3.5";
				break;
			case "B0":
				Score = "3.0";
				break;
			case "C+":
				Score = "2.5";
				break;
			case "C0":
				Score = "2.0";
				break;
			case "D+":
				Score = "1.5";
				break;
			case "D0":
				Score = "1.0";
				break;
			case "F":
				Score = "0";
				break;
		}
		return Score;
	}

	public unigrademan(){
		JFrame f=new JFrame("���� ���� ���α׷�");
		f.setLayout(new BorderLayout());
		tf1 = new JTextField(12);
		JPanel p1 = new JPanel(new FlowLayout());
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p3 = new JPanel(new GridLayout(2,8));
		// ���� �Է� ����
		try{
			rf = new RandomAccessFile(Path,"rw");
			while(true) {
				String buf = rf.readLine();			
				if(buf != null) { //���ۺ����� ������� �������.
					String kor = new String(buf.getBytes("ISO-8859-1"), "UTF-8"); //�ҷ��ö� �ѱ� ���� ����(UTF-8�� ���ڵ� ����)
					String tok[] = kor.split(","); //,������ split�ؼ� ��̸���Ʈ ���׸��� ����.
					mem.add(new addgrade(tok[0],tok[1],tok[2],tok[3],tok[4],tok[5])); //�ڸ� �������� ��̸���Ʈ�� �߰�.
				}
				else { //��������� while���� Ż���մϴ�.
					break;
				}
			}
		} 
		catch (IOException ioe) { 
			ioe.printStackTrace(); 
		} finally { //�Է��� �� ������ ����.(������ �ʿ�� �ٽ� ����ǹǷ� close)
			try { 
				if (rf != null) rf.close(); 
			} catch (IOException ioe) { 
				ioe.printStackTrace(); 
			} 
		}
		
		//��ܱ���
		JCheckBox tr = new JCheckBox("���Ի�",false);
		tr.addItemListener(new ItemListener() { //���Ի� üũ�ڽ� ������
			public void itemStateChanged(ItemEvent e) {
				//üũ�� �� ���
				if(e.getStateChange() == ItemEvent.SELECTED) {
					check1 = 1;
				}
				else {
					check1 = 0;
				}
			}			
		});
		
		p1.add(tr);
		p1.add(new JLabel("�й�"));
		p1.add(tf1);
		String[] syear1 = { "1", "2", "3", "4"};
		JComboBox sycombo1 = new JComboBox(syear1);
		p1.add(sycombo1);
		p1.add(new JLabel("�г�"));
		String[] semester1 = {"1", "2"};
		JComboBox smcombo1 = new JComboBox(semester1);
		p1.add(smcombo1);
		p1.add(new JLabel("�б�"));
		p1.add(a1);
		p1.add(a2);
		
		//�߰�����
		table = new JTable(model);
		JScrollPane sc = new JScrollPane(table);
		sc.setPreferredSize(new Dimension(470,250));
		table.getColumn("�����").setPreferredWidth(150);
		table.getColumn("���").setPreferredWidth(10);
		table.getColumn("����").setPreferredWidth(10);
		table.getColumn("����").setPreferredWidth(30);
		p2.add(sc);
		
		//�ϴܱ���
		p3.add(new JLabel("�������(100������)"));
		tf2	= new JTextField(12);
		p3.add(tf2);
		p3.add(new JLabel("�������б�"));
		tf3	= new JTextField(12);
		p3.add(tf3);
		p3.add(new JLabel("�����̼�����"));
		tf4	= new JTextField(12);
		p3.add(tf4);
		p3.add(new JLabel("������б�(����)"));
		tf5	= new JTextField(12);
		p3.add(tf5);
		
		//������ ����
		a2.addActionListener(new ActionListener() {
	            // ������� ��ư "�� â ����"�� ��ư�� �������� �߻��ϴ� �ൿ�� ����
	            public void actionPerformed(ActionEvent e) {
	                new newWindow(); // Ŭ���� newWindow�� ���� ������
	            }	            
	        });
		//�˻��� ���� ��� �� �հ� ����.
		a1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String cacheID = tf1.getText(); //�˻��� �׸� �ӽ÷� ����.
            	String Score; String fscore; String gp; String sgp;
            	double totalfScore = 0; double avgScore = 0; double avgtotalfscore = 0;
            	int totalGradePoint = 0; int totalScore = 0; int gradepoint; int totalsGradePoint = 0;
            	model.setRowCount(0);
            	//���Ի� üũ���δ� �ܼ��ϰ� 70�� 140������ ����
            	if(check1 == 1)
            		gradepoint = 70;            	
            	else
            		gradepoint = 140;
            	
            	//�й� �׸� ���� �̿ܿ��� �Է� ����
            	if(!cacheID.matches(regNum)) { 
					JOptionPane.showMessageDialog(f, "�й����� ���ڸ� �Է°����մϴ�.","WARNING",JOptionPane.WARNING_MESSAGE);
					tf1.setText("");tf2.setText("");tf3.setText("");tf4.setText("");tf5.setText("");					
				}
            	//�й��� ���� �Է±���
            	else if(cacheID.equals("")) {
					JOptionPane.showMessageDialog(f, "������ ������� �ʽ��ϴ�.","WARNING",JOptionPane.WARNING_MESSAGE);
				}
            	//����,���ڿ��� üũ�� ����
            	else {
            		int flag = 0; //�й� �÷���
            		int flag2 = 0; //�˻� ������ �÷���
            		for(int i=0; i<mem.size(); i++) {
            			//�й� �˻�
            			if(mem.get(i).getStudentID().equals(cacheID)) {
            				flag = 1;
            				//F���� ����ó��
            				if(!mem.get(i).getGrade().equals("F")) {
            					gp = mem.get(i).getGradePoint();
            					totalGradePoint += Integer.parseInt(mem.get(i).getGradePoint());
            				}
            				//�г� �б⿡ �ش��ϴ� �����Ͱ� �ִ� ��� ���
            				if(mem.get(i).getSyear().equals(sycombo1.getSelectedItem().toString()) && mem.get(i).getSemester().equals(smcombo1.getSelectedItem().toString())) {
            					flag2 = 1;
            					Vector<String> row = new Vector<String>();
            					row.addElement(mem.get(i).getStudentID());
            					row.addElement(mem.get(i).getSubject());
            					row.addElement(mem.get(i).getGradePoint());
            					row.addElement(mem.get(i).getGrade());
            					Score = Grade(mem.get(i).getGrade());
            					totalScore += Integer.parseInt(Score);
            					fscore = fGrade(mem.get(i).getGrade());
            					totalfScore += Double.parseDouble(fscore);
            					row.addElement(Score+" ("+fscore+")");
            					//�˻��� �ش��ϴ� ������ ���� ���.
            					if(!mem.get(i).getGrade().equals("F")) {
            						sgp = mem.get(i).getGradePoint();
            						totalsGradePoint += Integer.parseInt(mem.get(i).getGradePoint());
            					}
            					model.addRow(row);
            				}
            			}
            		}
            		//�˻���� �й��� �������� �ʴ°��
            		if(flag == 0) {
            			JOptionPane.showMessageDialog(f, cacheID+"�й��� �������� �ʽ��ϴ�.","�й��� �������� �ʽ��ϴ�.",JOptionPane.WARNING_MESSAGE);
    					tf1.setText("");tf2.setText("");tf3.setText("");tf4.setText("");tf5.setText("");
            		}
            		//�й��� �����ϸ� �˻��� �����Ͱ� �ִ°��
            		if(flag2 == 1 && flag == 1) {
    					int rows = table.getRowCount(); //������� ���̺� ǥ�õ� ���.
                    	avgScore = Math.round(((double)totalScore / (double)rows)*100)/100.0; //������ ��� ���
                    	avgtotalfscore = Math.round((totalfScore / (double)rows)*100)/100.0; //�Ҽ����� ��� ���
                    	gradepoint -= totalGradePoint;
                    	tf2.setText(avgScore +" ("+avgtotalfscore+")");
                    	tf4.setText(totalGradePoint+" ("+gradepoint+"����)");
                    	//�������б� ����(�ѱ�������� Ȩ������ ����)
                    	if(totalsGradePoint >= 12 && avgScore >= 80) 
                    		tf3.setText("����");
                    	else 
                    		tf3.setText("�Ұ���");
                    	//���� ������б� ����(�б� Ȩ������ ����)         	
                    	if(totalsGradePoint >= 14) {
                    		if(avgtotalfscore >= 3.75)
                    			tf5.setText("A�� ����(��������ʿ�)");
                    		else if(avgtotalfscore >= 3.5)
                    			tf5.setText("B�� ����(��������ʿ�)");
                    		else if(avgtotalfscore >= 3)
                    			tf5.setText("C�� ����(��������ʿ�)");
                    		else
                    			tf5.setText("�Ұ���");
                    	}
                    	else
                    		tf5.setText("�Ұ���");
    				}
            		//�й��� �����ϳ� �˻��� �����Ͱ� ���°��.
    				else if(flag == 1 && flag2 == 0){
    					JOptionPane.showMessageDialog(f, cacheID+"�й����� " + sycombo1.getSelectedItem().toString() + "�г� "+ smcombo1.getSelectedItem().toString() + "�б⿡ ���� ������ �������� �ʽ��ϴ�.",
    							"������ �������� �ʽ��ϴ�.",JOptionPane.WARNING_MESSAGE);
    					tf1.setText("");tf2.setText("");tf3.setText("");tf4.setText("");tf5.setText("");
    				}
                }                
            }
        });
		
		f.add(p1, BorderLayout.NORTH);
		f.add(p2, BorderLayout.CENTER);
		f.add(p3, BorderLayout.SOUTH);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setSize(600, 390);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
	}
	class newWindow extends JFrame {
	    // ��ư�� �������� ��������� �� â�� ������ Ŭ����
		JTextField tf6, tf7;
		JButton a3 = new JButton("���");
		JButton a4 = new JButton("���");
	    newWindow() {	      
	        // ����, ���⼭ setDefaultCloseOperation() ���Ǹ� ���� ���ƾ� �Ѵ�
	        // �����ϰ� �Ǹ� �� â�� ������ ��� â�� ���α׷��� ���ÿ� ������
	        JFrame f1=new JFrame("���� ���");
	        f1.setLayout(new GridLayout(3,1));
			tf6 = new JTextField(12);
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			JPanel p5 = new JPanel(new FlowLayout());
	   
			JLabel l2 = new JLabel("�й�");
			p3.add(l2);
			p3.add(tf6);
						
			String[] syear2 = {"1", "2", "3", "4"};
			JComboBox sycombo2 = new JComboBox(syear2);
			p3.add(sycombo2);
			p3.add(new JLabel("�г�"));
			
			String[] semester2 = {"1", "2"};
			JComboBox smcombo2 = new JComboBox(semester2);
			p3.add(smcombo2);
			p3.add(new JLabel("�б�"));
			
			String[] gp = {"1", "2", "3"};
			JComboBox gpcombo = new JComboBox(gp);
			p4.add(gpcombo);
			p4.add(new JLabel("����"));
						
			String[] fruit = {"A+", "A0", "B+", "B0", "C+", "C0", "D+", "D0", "F"};
			JComboBox strCombo = new JComboBox(fruit);
			p4.add(strCombo);
			p4.add(new JLabel("���"));
			
			//������ �Է� �� ���� ���� ������ ����
			a3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String a = tf6.getText(); //�й�
					String b = tf7.getText(); //�����
					String c = sycombo2.getSelectedItem().toString(); //�г�
					String d = smcombo2.getSelectedItem().toString(); //�б�
					String f = gpcombo.getSelectedItem().toString(); //����
					String h = strCombo.getSelectedItem().toString(); //���
					if(a.equals("") && b.equals("")) {
						JOptionPane.showMessageDialog(f1, "������ ������� �ʽ��ϴ�.","WARNING",JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(!a.matches(regNum)) {
							JOptionPane.showMessageDialog(f1, "�й����� ���ڸ� �Է°����մϴ�.","WARNING",JOptionPane.WARNING_MESSAGE);
							tf6.setText("");
						}
						else {
							int flag = 0; // �ش� �й��� �Է��� ����� ���� ������ �ִ��� �÷��� ����.
							for(int i=0; i<mem.size(); i++) { //�ϳ��ϳ� ���ƺ�.
			                	//�ش� �й��� �Է��� ����� ���� ������ �ִ°��.
								if(mem.get(i).getStudentID().equals(a) && mem.get(i).getSubject().equals(b)) {
			                		JOptionPane.showMessageDialog(f1, "�� �й��� ���������� �Է��� �Ұ����մϴ�.","WARNING",JOptionPane.WARNING_MESSAGE);
			                		flag = 1;//�÷��׸� 1�� �����ؼ� ������ �Է��� �ǳʶ�.
			                		tf7.setText("");//�ٽ� �Է��ؾ� �ϴ� �������� ����.
			                		break;//�� �̻� �˻��� �ʿ䰡 ������ �ݺ��� Ż��.
			                	}
			                }
							if(flag == 0) {
								//üũ�� ������ �Է�.
								mem.add(new addgrade(a,b,c,d,f,h));
								//���Ͽ� �� ���� ,������ �����ؼ� ���� �Է�.
								String buf = a+","+b+","+c+","+d+","+f+","+h+"\n";
								
								try {
									rf = new RandomAccessFile(Path,"rw");
									rf.seek(rf.length()); //�������κп� Ŀ���� �̵�
									rf.write(buf.getBytes("utf-8")); //������ �Է�(�ѱ� ���� ������ UTF-8 ���ڵ�)
								} catch (IOException ioe) { 
									ioe.printStackTrace(); 
								} finally { //�Է��� �� ������ ����.(�ʿ�� �ٽ� ����ǹǷ� close���)
									try { 
										if (rf != null) rf.close(); 
									} catch (IOException ioe) { 
										ioe.printStackTrace(); 
									} 
								} 
								JOptionPane.showMessageDialog(f1, "���������� �Է��� �Ϸ��߽��ϴ�.","�Է� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
								f1.dispose();//�Է� �Ϸ� �� �ش� ������ �ݱ�.
							}
						}
					}
				}				
			});
			//�ι�° â ��ҹ�ư ������
			a4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f1.dispose();
				}
			});
			
			tf7 = new JTextField(12);
			JLabel l3 = new JLabel("�����");
			p4.add(l3);
			p4.add(tf7);
			
			//�ϴ� ��ư
			p5.add(a3);
			p5.add(a4);
	      
			f1.add(p3);
			f1.add(p4);
			f1.add(p5);
	        f1.setSize(500,150);
	        f1.setResizable(false);
	        f1.setVisible(true);
	        f1.setLocationRelativeTo(null);
	    }
	}
	public static void main(String[] args) {
		new unigrademan();
	}
}