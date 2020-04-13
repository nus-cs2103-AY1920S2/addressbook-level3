package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.transaction.Loan;

/**
 * Represents a reminder, which reminds people on their unpaid loans through emails.
 */
public class Reminder {
    private User sender;
    private Person receiver;
    private MimeMessage message;

    private final String emailUsername = "noreply.loansharkie";
    private final String emailPassword = "cs2103t-w12-3";

    /**
     * Constructs a Reminder object.
     */
    public Reminder(User sender, Person receiver) {
        requireAllNonNull(sender, receiver);
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Sends an email to the receiver.
     * @throws MessagingException if error occurs while sending the email.
     */
    public void sendReminder() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        message = new MimeMessage(session);
        constructMessage();

        Transport.send(message);
    }

    /**
     * Constructs the content of the email.
     * @throws MessagingException if error occurs while sending the emails.
     */
    private void constructMessage() throws MessagingException {
        String content = "<h2><strong>[Sharkie] Reminder</strong></h2><p>Hello "
                + receiver.getName() + ",</p><p>You have yet settle your debt to <span style="
                + "\"background-color: #ffff00;\"> " + sender.getName() + "</span>.</p><p>Here is the "
                + "list of your debts:</p><ol>";
        for (Loan loan: this.receiver.getLoans()) {
            content += "<li>" + loan + "</li>";
        }
        content += "</ol><p style=\"padding-left: 30px;\"><span style=\"color: #000000; background-color: #ffff00;\">"
                + "Total :&nbsp;" + receiver.getLoans().getTotal() + "</span></p><p>For further details, contact "
                + sender.getName() + " via " + sender.getPhone() + " or " + sender.getEmail() + ".</p>"
                + "<p>Thank you.</p><p>&nbsp;</p><p>Regards,</p><p>Sharkie.</p>";

        message.setFrom(new InternetAddress("noreply.loansharkie@gmail.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(sender.getEmail().toString()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver.getEmail().toString()));
        message.setSubject("[Sharkie] Reminder from " + sender.getName() + " on unsettled debts!");
        message.setContent(content, "text/html; charset=utf-8");
    }

    public User getSender() {
        return this.sender;
    }

    public Person getReceiver() {
        return this.receiver;
    }

    /**
     * Returns true if both sender and receiver are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getSender().equals(getSender())
                && otherReminder.getReceiver().equals(getReceiver());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(sender, receiver);
    }
}
