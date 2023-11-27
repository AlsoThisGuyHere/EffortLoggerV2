package effortloggerv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Hasan Shahid
public class SharePage {
	
	public void sharePage(List<PlanningPoker> planningPoker) {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task("Some task name", "2"));
		tasks.add(new Task("Another task", "4"));
		
		GridPane gridPane = new GridPane();
		
		Label senderLabel = new Label("Sender");
		Label receiverLabel = new Label("Receiver");
		
		TextField tfSender = new TextField();
		TextField tfReceiver = new TextField();
		
		Button sendButton = new Button("Send");
		
		Alert alert = new Alert(AlertType.ERROR);
		
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true); //TLS
        prop.put("mail.smtp.ssl.trust", "*");
		Session session = Session.getDefaultInstance(prop,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("username", "password");
					}
				});
		
		ObservableList<PlanningPoker> task = FXCollections.observableList(planningPoker);
		ListView<PlanningPoker> listView = new ListView<PlanningPoker>(task);
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent actionEvent) {
				if (checkTextInput(tfSender.getText()) || checkTextInput(tfReceiver.getText()) || listView.getSelectionModel().getSelectedItems().isEmpty()) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("All the fields are requried");
					alert.setTitle("Share Error");
					alert.show();
				}
				else {
					alert.setAlertType(AlertType.INFORMATION);
					alert.setContentText("Estimates shared successfully");
					alert.setTitle("Share Successful");
					alert.show();
//					try {
//						MimeMessage message = new MimeMessage(session);
//						message.setFrom(new InternetAddress(tfSender.getText()));
//						message.setRecipient(Message.RecipientType.TO, new InternetAddress(tfReceiver.getText()));
//						message.setSubject("Subject");
//						message.setText("Sample message");
//						Transport.send(message);
//						
//					} catch (MessagingException e) {
//						e.printStackTrace();
//						System.out.println("Error sending");
//					}
				}
			}
		});
		
		gridPane.addRow(0, senderLabel);
		gridPane.addRow(1, tfSender);
		gridPane.addRow(2, receiverLabel);
		gridPane.addRow(3, tfReceiver);
		gridPane.addRow(4, new Label("\n"));
		gridPane.addRow(5, listView);
		gridPane.addRow(6, sendButton);
		
		gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Share Page");
		stage.setScene(new Scene(gridPane, 400, 400));
		stage.show();
	}
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }

}
