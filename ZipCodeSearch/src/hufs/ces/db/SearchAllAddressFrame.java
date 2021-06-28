/**
 * 
 * @author cskim -- hufs.ac.kr 
 * 2017. 11. 13.
 * Copy Right -- Free for Educational Purpose
 *
 */
package hufs.ces.db;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import hufs.ces.dbutils.DBConn;

/**
 * @author cskim
 *
 */
public class SearchAllAddressFrame extends JFrame {

	private SearchAllAddressFrame thisClass = this;
	
	private JPanel contentPane;
	private JButton btnConnect;
	private JLabel lblConnStatus;
	private JLabel lblSido;
	private JLabel lblSigungu;
	private JLabel lblEub;
	private JLabel lblDoromyung;
	private JTextField txtFSido;
	private JTextField txtFSigungu;
	private JTextField txtFEubmyundong;
	private JTextField txtFDoromyung;
	private JButton btnGetZipCode_Eubmyundong;
	private JButton btnGetZipCode_Doromyung;
	
	private Connection conn = null;
	private PreparedStatement p = null;
	
	public SearchAllAddressFrame() {
		
		initialize();
	}
	
	void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 928);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		btnConnect = new JButton("     Connect      ");
		btnConnect.setBounds(17, 820, 167, 37);
		contentPane.add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connDBAction();
			}
		});
		
		lblConnStatus = new JLabel("");
		lblConnStatus.setBounds(201, 820, 692, 37);
		contentPane.add(lblConnStatus);
		
		lblSido = new JLabel("Sido");
		lblSido.setBounds(17, 254, 130, 37);
		contentPane.add(lblSido);
		
		lblSigungu = new JLabel("Sigunju");
		lblSigungu.setBounds(17, 306, 130, 37);
		contentPane.add(lblSigungu);
		
		lblEub = new JLabel("Eubmyundong");
		lblEub.setBounds(17, 358, 130, 37);
		contentPane.add(lblEub);
		
		lblDoromyung = new JLabel("Doromyung");
		lblDoromyung.setBounds(17, 410, 130, 37);
		contentPane.add(lblDoromyung);
		
		txtFSido = new JTextField();
		txtFSido.setColumns(10);
		txtFSido.setBounds(164, 254, 729, 37);
		contentPane.add(txtFSido);
		
		txtFSigungu = new JTextField();
		txtFSigungu.setColumns(10);
		txtFSigungu.setBounds(164, 306, 729, 37);
		contentPane.add(txtFSigungu);
		
		txtFEubmyundong = new JTextField();
		txtFEubmyundong.setColumns(10);
		txtFEubmyundong.setBounds(164, 358, 729, 37);
		contentPane.add(txtFEubmyundong);
		
		txtFDoromyung = new JTextField();
		txtFDoromyung.setColumns(10);
		txtFDoromyung.setBounds(164, 410, 729, 37);
		contentPane.add(txtFDoromyung);
		
		btnGetZipCode_Eubmyundong = new JButton("Get ZipCode using Eubmyundong");
		btnGetZipCode_Eubmyundong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sido = txtFSido.getText();
				String sigungu = txtFSigungu.getText();
				String eubmyundong = txtFEubmyundong.getText();
				boolean found = false;  // found: zipcode를 찾았으면 true, 아니면 false 값을 갖는 변수
				
				for(int i=1; i<=3; i++) {
					String str = "";
					
					// eubmyundong 이 없으면 gov_dong_name 을 이용해 zipcode를 찾고,
					// gov_dong_name 도 없으면 law_dong_name 을 이용해 zipcode를 찾음
					if(i == 1)
						str = "eubmyundong";
					else if(i == 2)
						str = "gov_dong_name";
					else if(i == 3)
						str = "law_dong_name";
					
					String query = "SELECT zipcode FROM zipcode_kor2017 WHERE "
							+ "sido = '" + sido + "'"
							+ " AND sigungu = '" + sigungu + "'"
							+ " AND " + str + " = '" + eubmyundong + "'";
					
					//System.out.println(query);
					
					if(ShowZipCode(query)) {  // zipcode를 찾았을 경우 loop를 빠져나옴
						found = true;
						break;
					}
				}
				
				if(!found)  // zipcode를 찾지 못했을 경우 에러 메시지 출력
					JOptionPane.showMessageDialog(thisClass, "Error: ZipCode Not Found", "ZipCode", JOptionPane.INFORMATION_MESSAGE);
			
				resetDBAction();
			}
		});
		btnGetZipCode_Eubmyundong.setBounds(17, 498, 876, 50);
		contentPane.add(btnGetZipCode_Eubmyundong);
		
		btnGetZipCode_Doromyung = new JButton("Get ZipCode using Doromyung");
		btnGetZipCode_Doromyung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sido = txtFSido.getText();
				String sigungu = txtFSigungu.getText();
				String doromyung = txtFDoromyung.getText();
				
				String query = "SELECT zipcode FROM zipcode_kor2017 WHERE "
						+ "sido = '" + sido + "'"
						+ " AND sigungu = '" + sigungu + "'"
						+ " AND doromyung = '" + doromyung + "'";
				
				//System.out.println(query);
				
				if(!ShowZipCode(query))  // zipcode를 찾지 못했을 경우 에러 메시지 출력
					JOptionPane.showMessageDialog(thisClass, "Error: ZipCode Not Found", "ZipCode", JOptionPane.INFORMATION_MESSAGE);
			
				resetDBAction();
			}
		});
		btnGetZipCode_Doromyung.setBounds(17, 563, 876, 50);
		contentPane.add(btnGetZipCode_Doromyung);
	}
	/**
	 * 
	 */
	protected void connDBAction() {
		try {
			lblConnStatus.setText("");
			DBConn dbconn = new DBConn();
			conn = dbconn.getConnection();
			
			String connMsg = " Connected to "
					+ dbconn.getServerIP() + " "
					+ dbconn.getDBname();
			lblConnStatus.setText(connMsg);
			
			//System.out.println("Successfully" + connMsg);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean ShowZipCode(String query){
		
		ResultSet result = null;
		boolean exist = false;  // exist: zipcode가 있으면 true, 없으면 false 값을 갖는 변수
		
		try {
			p = conn.prepareStatement(query);
			result = p.executeQuery();
			
			if(result.next()) {
				JOptionPane.showMessageDialog(this, "ZipCode: " + result.getString(1), "ZipCode", JOptionPane.INFORMATION_MESSAGE);
				exist = true;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exist;
	}
	
	public void resetDBAction() {  // DB Connection을 해제했다가 다시 설정함
		closeDBAction();
		connDBAction();
	}
	
	public void closeDBAction() {  // DB Connection을 해제함
		try {
			p.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
