import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class unigrademan extends JFrame{
	ArrayList<addgrade> mem = new ArrayList<addgrade>(); //프로그램 내부의 임시저장소.
	String Path = "c:\\Test\\db.txt"; //기본경로 설정
	RandomAccessFile rf; //파일엑세스 미리 선언
	JTextField tf1, tf4, tf2, tf3, tf5;
	JButton a1 = new JButton("검색");
	JButton a2 = new JButton("과목 등록");
	JTable table;
	JScrollPane scroll;
	String[] title = {"학번","과목명","학점","등급","점수"};
	//테이블 타이틀 설정
	DefaultTableModel model = new DefaultTableModel(null,title){ 
		//테이블 내용 수정 불가능하게 처리.
		public boolean isCellEditable(int i, int c){ 
			return false; 
		} 
	}; 
	String regNum = "^[0-9]*$"; //숫자 정규식.
	int check1 = 0; //편입생 체크 플래그.
	
	//학점을 백점대로 환산.
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
	
	//학점을 소수점대로 환산.
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
		JFrame f=new JFrame("성적 관리 프로그램");
		f.setLayout(new BorderLayout());
		tf1 = new JTextField(12);
		JPanel p1 = new JPanel(new FlowLayout());
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p3 = new JPanel(new GridLayout(2,8));
		// 파일 입력 구현
		try{
			rf = new RandomAccessFile(Path,"rw");
			while(true) {
				String buf = rf.readLine();			
				if(buf != null) { //버퍼변수가 비어있지 않은경우.
					String kor = new String(buf.getBytes("ISO-8859-1"), "UTF-8"); //불러올때 한글 깨짐 방지(UTF-8로 인코딩 설정)
					String tok[] = kor.split(","); //,단위로 split해서 어레이리스트 제네릭에 저장.
					mem.add(new addgrade(tok[0],tok[1],tok[2],tok[3],tok[4],tok[5])); //자른 변수들을 어레이리스트에 추가.
				}
				else { //비어있으면 while문을 탈출합니다.
					break;
				}
			}
		} 
		catch (IOException ioe) { 
			ioe.printStackTrace(); 
		} finally { //입력을 다 했으면 종료.(어차피 필요시 다시 실행되므로 close)
			try { 
				if (rf != null) rf.close(); 
			} catch (IOException ioe) { 
				ioe.printStackTrace(); 
			} 
		}
		
		//상단구역
		JCheckBox tr = new JCheckBox("편입생",false);
		tr.addItemListener(new ItemListener() { //편입생 체크박스 리스너
			public void itemStateChanged(ItemEvent e) {
				//체크를 한 경우
				if(e.getStateChange() == ItemEvent.SELECTED) {
					check1 = 1;
				}
				else {
					check1 = 0;
				}
			}			
		});
		
		p1.add(tr);
		p1.add(new JLabel("학번"));
		p1.add(tf1);
		String[] syear1 = { "1", "2", "3", "4"};
		JComboBox sycombo1 = new JComboBox(syear1);
		p1.add(sycombo1);
		p1.add(new JLabel("학년"));
		String[] semester1 = {"1", "2"};
		JComboBox smcombo1 = new JComboBox(semester1);
		p1.add(smcombo1);
		p1.add(new JLabel("학기"));
		p1.add(a1);
		p1.add(a2);
		
		//중간구역
		table = new JTable(model);
		JScrollPane sc = new JScrollPane(table);
		sc.setPreferredSize(new Dimension(470,250));
		table.getColumn("과목명").setPreferredWidth(150);
		table.getColumn("등급").setPreferredWidth(10);
		table.getColumn("학점").setPreferredWidth(10);
		table.getColumn("점수").setPreferredWidth(30);
		p2.add(sc);
		
		//하단구역
		p3.add(new JLabel("학점평균(100점단위)"));
		tf2	= new JTextField(12);
		p3.add(tf2);
		p3.add(new JLabel("국가장학금"));
		tf3	= new JTextField(12);
		p3.add(tf3);
		p3.add(new JLabel("졸업이수학점"));
		tf4	= new JTextField(12);
		p3.add(tf4);
		p3.add(new JLabel("우수장학금(교내)"));
		tf5	= new JTextField(12);
		p3.add(tf5);
		
		//리스너 구현
		a2.addActionListener(new ActionListener() {
	            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
	            public void actionPerformed(ActionEvent e) {
	                new newWindow(); // 클래스 newWindow를 새로 만들어낸다
	            }	            
	        });
		//검색을 통한 출력 및 합계 구현.
		a1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String cacheID = tf1.getText(); //검색한 항목 임시로 저장.
            	String Score; String fscore; String gp; String sgp;
            	double totalfScore = 0; double avgScore = 0; double avgtotalfscore = 0;
            	int totalGradePoint = 0; int totalScore = 0; int gradepoint; int totalsGradePoint = 0;
            	model.setRowCount(0);
            	//편입생 체크여부는 단순하게 70점 140점으로 구분
            	if(check1 == 1)
            		gradepoint = 70;            	
            	else
            		gradepoint = 140;
            	
            	//학번 항목에 숫자 이외에는 입력 금지
            	if(!cacheID.matches(regNum)) { 
					JOptionPane.showMessageDialog(f, "학번에는 숫자만 입력가능합니다.","WARNING",JOptionPane.WARNING_MESSAGE);
					tf1.setText("");tf2.setText("");tf3.setText("");tf4.setText("");tf5.setText("");					
				}
            	//학번에 공백 입력금지
            	else if(cacheID.equals("")) {
					JOptionPane.showMessageDialog(f, "공백은 허용하지 않습니다.","WARNING",JOptionPane.WARNING_MESSAGE);
				}
            	//공백,숫자여부 체크후 실행
            	else {
            		int flag = 0; //학번 플래그
            		int flag2 = 0; //검색 데이터 플래그
            		for(int i=0; i<mem.size(); i++) {
            			//학번 검사
            			if(mem.get(i).getStudentID().equals(cacheID)) {
            				flag = 1;
            				//F학점 예외처리
            				if(!mem.get(i).getGrade().equals("F")) {
            					gp = mem.get(i).getGradePoint();
            					totalGradePoint += Integer.parseInt(mem.get(i).getGradePoint());
            				}
            				//학년 학기에 해당하는 데이터가 있는 경우 출력
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
            					//검색에 해당하는 학점만 따로 계산.
            					if(!mem.get(i).getGrade().equals("F")) {
            						sgp = mem.get(i).getGradePoint();
            						totalsGradePoint += Integer.parseInt(mem.get(i).getGradePoint());
            					}
            					model.addRow(row);
            				}
            			}
            		}
            		//검색결과 학번이 존재하지 않는경우
            		if(flag == 0) {
            			JOptionPane.showMessageDialog(f, cacheID+"학번은 존재하지 않습니다.","학번이 존재하지 않습니다.",JOptionPane.WARNING_MESSAGE);
    					tf1.setText("");tf2.setText("");tf3.setText("");tf4.setText("");tf5.setText("");
            		}
            		//학번이 존재하며 검색된 데이터가 있는경우
            		if(flag2 == 1 && flag == 1) {
    					int rows = table.getRowCount(); //과목수는 테이블에 표시된 행수.
                    	avgScore = Math.round(((double)totalScore / (double)rows)*100)/100.0; //백점대 평균 계산
                    	avgtotalfscore = Math.round((totalfScore / (double)rows)*100)/100.0; //소수점대 평균 계산
                    	gradepoint -= totalGradePoint;
                    	tf2.setText(avgScore +" ("+avgtotalfscore+")");
                    	tf4.setText(totalGradePoint+" ("+gradepoint+"남음)");
                    	//국가장학금 기준(한국장학재단 홈페이지 참고)
                    	if(totalsGradePoint >= 12 && avgScore >= 80) 
                    		tf3.setText("가능");
                    	else 
                    		tf3.setText("불가능");
                    	//교내 우수장학금 기준(학교 홈페이지 참고)         	
                    	if(totalsGradePoint >= 14) {
                    		if(avgtotalfscore >= 3.75)
                    			tf5.setText("A급 가능(봉사실적필요)");
                    		else if(avgtotalfscore >= 3.5)
                    			tf5.setText("B급 가능(봉사실적필요)");
                    		else if(avgtotalfscore >= 3)
                    			tf5.setText("C급 가능(봉사실적필요)");
                    		else
                    			tf5.setText("불가능");
                    	}
                    	else
                    		tf5.setText("불가능");
    				}
            		//학번은 존재하나 검색된 데이터가 없는경우.
    				else if(flag == 1 && flag2 == 0){
    					JOptionPane.showMessageDialog(f, cacheID+"학번에는 " + sycombo1.getSelectedItem().toString() + "학년 "+ smcombo1.getSelectedItem().toString() + "학기에 대한 정보가 존재하지 않습니다.",
    							"정보가 존재하지 않습니다.",JOptionPane.WARNING_MESSAGE);
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
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
		JTextField tf6, tf7;
		JButton a3 = new JButton("등록");
		JButton a4 = new JButton("취소");
	    newWindow() {	      
	        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
	        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
	        JFrame f1=new JFrame("성적 등록");
	        f1.setLayout(new GridLayout(3,1));
			tf6 = new JTextField(12);
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			JPanel p5 = new JPanel(new FlowLayout());
	   
			JLabel l2 = new JLabel("학번");
			p3.add(l2);
			p3.add(tf6);
						
			String[] syear2 = {"1", "2", "3", "4"};
			JComboBox sycombo2 = new JComboBox(syear2);
			p3.add(sycombo2);
			p3.add(new JLabel("학년"));
			
			String[] semester2 = {"1", "2"};
			JComboBox smcombo2 = new JComboBox(semester2);
			p3.add(smcombo2);
			p3.add(new JLabel("학기"));
			
			String[] gp = {"1", "2", "3"};
			JComboBox gpcombo = new JComboBox(gp);
			p4.add(gpcombo);
			p4.add(new JLabel("학점"));
						
			String[] fruit = {"A+", "A0", "B+", "B0", "C+", "C0", "D+", "D0", "F"};
			JComboBox strCombo = new JComboBox(fruit);
			p4.add(strCombo);
			p4.add(new JLabel("등급"));
			
			//데이터 입력 및 파일 저장 리스너 구현
			a3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String a = tf6.getText(); //학번
					String b = tf7.getText(); //과목명
					String c = sycombo2.getSelectedItem().toString(); //학년
					String d = smcombo2.getSelectedItem().toString(); //학기
					String f = gpcombo.getSelectedItem().toString(); //학점
					String h = strCombo.getSelectedItem().toString(); //등급
					if(a.equals("") && b.equals("")) {
						JOptionPane.showMessageDialog(f1, "공백은 허용하지 않습니다.","WARNING",JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(!a.matches(regNum)) {
							JOptionPane.showMessageDialog(f1, "학번에는 숫자만 입력가능합니다.","WARNING",JOptionPane.WARNING_MESSAGE);
							tf6.setText("");
						}
						else {
							int flag = 0; // 해당 학번에 입력한 과목과 같은 과목이 있는지 플래그 설정.
							for(int i=0; i<mem.size(); i++) { //하나하나 돌아봄.
			                	//해당 학번에 입력한 과목과 같은 과목이 있는경우.
								if(mem.get(i).getStudentID().equals(a) && mem.get(i).getSubject().equals(b)) {
			                		JOptionPane.showMessageDialog(f1, "한 학번에 같은과목은 입력이 불가능합니다.","WARNING",JOptionPane.WARNING_MESSAGE);
			                		flag = 1;//플래그를 1로 설정해서 데이터 입력을 건너뜀.
			                		tf7.setText("");//다시 입력해야 하니 공백으로 설정.
			                		break;//더 이상 검색할 필요가 없으니 반복문 탈출.
			                	}
			                }
							if(flag == 0) {
								//체크후 데이터 입력.
								mem.add(new addgrade(a,b,c,d,f,h));
								//파일에 쓸 때는 ,단위로 구분해서 한줄 입력.
								String buf = a+","+b+","+c+","+d+","+f+","+h+"\n";
								
								try {
									rf = new RandomAccessFile(Path,"rw");
									rf.seek(rf.length()); //마지막부분에 커서를 이동
									rf.write(buf.getBytes("utf-8")); //데이터 입력(한글 깨짐 방지로 UTF-8 인코딩)
								} catch (IOException ioe) { 
									ioe.printStackTrace(); 
								} finally { //입력을 다 했으면 종료.(필요시 다시 실행되므로 close사용)
									try { 
										if (rf != null) rf.close(); 
									} catch (IOException ioe) { 
										ioe.printStackTrace(); 
									} 
								} 
								JOptionPane.showMessageDialog(f1, "성공적으로 입력을 완료했습니다.","입력 완료", JOptionPane.INFORMATION_MESSAGE);
								f1.dispose();//입력 완료 후 해당 프레임 닫기.
							}
						}
					}
				}				
			});
			//두번째 창 취소버튼 리스너
			a4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f1.dispose();
				}
			});
			
			tf7 = new JTextField(12);
			JLabel l3 = new JLabel("과목명");
			p4.add(l3);
			p4.add(tf7);
			
			//하단 버튼
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