import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import com.google.gson.Gson;

public class APIConsumer {
    
    public static void main(String[] args) {
    	System.out.println("Enter Country : ");
	    Scanner sc = new Scanner(System.in);
	    String country = sc.next();
        String apiUrl = "http://universities.hipolabs.com/search?country="+country;
        
        String url1 = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=APIProj;" +
                "encrypt=true;" +
                "trustServerCertificate=true";
        String user = "sa";
        String pass = "root";
        Connection conn1 = null;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
                        conn1 = DriverManager.getConnection(url1, user, pass);
                        
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();
            
            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            MyObject[] myObj = gson.fromJson(json.toString(), MyObject[].class);
            
            String insertSql = "INSERT INTO universities (name, domain, website, country, alpha_code) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement statementSQL = conn1.prepareStatement(insertSql);
            for (MyObject university : myObj) {
            	System.out.println("Name: " + university.getName());
            	System.out.println("Domain: " + university.getDomains());
            	System.out.println("Website: " + university.getWebPages());
            	System.out.println("Country: " + university.getCountry());
            	System.out.println("Alpha Code: " + university.getAlphaTwoCode());
            	statementSQL.setString(1, university.getName());
            	statementSQL.setString(2, university.getDomains().get(0));
            	statementSQL.setString(3, university.getWebPages().get(0));
            	statementSQL.setString(4, university.getCountry());
            	statementSQL.setString(5, university.getAlphaTwoCode());
            	statementSQL.addBatch();
            	System.out.println();
            	}
            statementSQL.executeBatch();
            statementSQL.close();
            conn1.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
}

