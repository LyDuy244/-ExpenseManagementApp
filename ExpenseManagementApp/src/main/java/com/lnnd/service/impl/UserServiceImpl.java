/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.User;
import com.lnnd.repository.UserRepository;
import com.lnnd.service.UserService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service("userDetailsService")
@PropertySource("classpath:configs.properties")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public boolean addUser(User user) {
        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pass));
        user.setIsActive(false);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        if (!user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvartar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepository.addUser(user);
    }

    @Override
    public List<User> getUsers(String username) {
        return this.userRepository.getUsers(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.getUsers(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User does not exits");
        }

        User user = users.get(0);

        Set<GrantedAuthority> auth = new HashSet<>();

        auth.add(new SimpleGrantedAuthority(user.getUserRole()));
        return new MyUserDetails(user);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    @Override
    public boolean updateUser(int id, User user) {
        User u = this.getUserById(id);
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setGender(user.getGender());
        u.setBirthday(user.getBirthday());

        if (!user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvartar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepository.updateUser(id, u);
    }

    @Override
    public void sendMailWarning(User user, String text) throws MessagingException, UnsupportedEncodingException {
        final String fromEmail = this.env.getProperty("spring.mail.username");
        // Mat khai email cua ban
        final String password = this.env.getProperty("spring.mail.password");
        // dia chi email nguoi nhan
        final String toEmail = user.getEmail();

        final String subject = "Cảnh báo về các khoản thu chi";
        String senderName = "Website quản lý thu chi";
        String body = "<p>Kính gửi " + user.getFirstName() + " " + user.getLastName() + ",</p>";
        body += "<div>" + text + " </div>";
        body += "<div> Xin cảm ơn. </div>";

        Properties props = new Properties();
        props.put("mail.smtp.host", this.env.getProperty("spring.mail.host")); //SMTP Host
        props.put("mail.smtp.port", this.env.getProperty("spring.mail.port")); //TLS Port
        props.put("mail.smtp.auth", this.env.getProperty("spring.mail.properties.mail.smtp.auth")); //enable authentication
        props.put("mail.smtp.starttls.enable", this.env.getProperty("spring.mail.properties.mail.smtp.starttls.enable")); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/html; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setContent(body, "text/html; charset=UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
        System.out.println("Gui mail thanh cong");
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.getAllUser();
    }

    @Override
    public List<User> getAllUserWithoutCurrentUser(int id) {
        return this.userRepository.getAllUserWithoutCurrentUser(id);
    }

    @Override
    public void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        final String fromEmail = this.env.getProperty("spring.mail.username");
        // Mat khai email cua ban
        final String password = this.env.getProperty("spring.mail.password");
        // dia chi email nguoi nhan
        final String toEmail = user.getEmail();

        final String subject = "Xác thực tài khoản Email";
        String senderName = "Website quản lý thu chi";
        String body = "<p>Kính gửi " + user.getFirstName() + " " + user.getLastName() + ",</p>";
        body += "<div>Hãy nhấn vào đường dẫn bên dưới để xác nhận tài khoản đăng ký.</div>";
        String verifyUrl = "http://localhost:8080" + siteUrl + "/verify?code=" + user.getVerificationCode();
        body += "<h3><a href=" + verifyUrl + ">VERIFY</a></h3>";
        body += "<div> Xin cảm ơn. </div>";

        Properties props = new Properties();
        props.put("mail.smtp.host", this.env.getProperty("spring.mail.host")); //SMTP Host
        props.put("mail.smtp.port", this.env.getProperty("spring.mail.port")); //TLS Port
        props.put("mail.smtp.auth", this.env.getProperty("spring.mail.properties.mail.smtp.auth")); //enable authentication
        props.put("mail.smtp.starttls.enable", this.env.getProperty("spring.mail.properties.mail.smtp.starttls.enable")); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/html; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setContent(body, "text/html; charset=UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
        System.out.println("Gui mail thanh cong");
    }

    @Override
    public User getUserByVerificationCode(String string) {
        return this.userRepository.getUserByVerificationCode(string);
    }

    @Override
    public boolean isActive(int userId) {
        return this.userRepository.isActive(userId);
    }

    @Override
    public boolean verify(String verify) {
        User user = userRepository.getUserByVerificationCode(verify);
        if (user == null || user.getIsActive()) {
            return false;
        } else {
            userRepository.isActive(user.getId());
            return true;
        }
    }

    @Override
    public void sendInviteEmail(User userFrom, User userTo, GroupExpense group, String siteUrl) throws MessagingException, UnsupportedEncodingException {
         final String fromEmail = this.env.getProperty("spring.mail.username");
        // Mat khai email cua ban
        final String password = this.env.getProperty("spring.mail.password");
        // dia chi email nguoi nhan
        final String toEmail = userTo.getEmail();

        final String subject = "Thư mời tham gia nhóm";
        String senderName = "Website quản lý thu chi";
        String body = "<p>Kính gửi: " + userTo.getFirstName() + " " + userTo.getLastName() + ",</p>";
        body += "<div>Người dùng " + userFrom.getFirstName() + " " + userFrom.getLastName() + " gửi cho bạn lời mời vào nhóm " + group.getName() + "</div>";
        String acceptUrl = "http://localhost:8080" + siteUrl + "/invite?userId=" + userTo.getId() + "&groupId=" + group.getId();
        body += "<p>Nếu đồng ý hãy nhấn vào link sau: </p>";
        body += "<h3><a href=" + acceptUrl + ">ACCEPT</a></h3>";
        body += "<div> Xin cảm ơn. </div>";

        Properties props = new Properties();
        props.put("mail.smtp.host", this.env.getProperty("spring.mail.host")); //SMTP Host
        props.put("mail.smtp.port", this.env.getProperty("spring.mail.port")); //TLS Port
        props.put("mail.smtp.auth", this.env.getProperty("spring.mail.properties.mail.smtp.auth")); //enable authentication
        props.put("mail.smtp.starttls.enable", this.env.getProperty("spring.mail.properties.mail.smtp.starttls.enable")); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/html; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setContent(body, "text/html; charset=UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Transport.send(msg);
        System.out.println("Gui mail thanh cong");
    }

    @Override
    public boolean isBlock(int userId) {
        return this.userRepository.isBlock(userId);
    }

    @Override
    public List<User> getAllUserPagination(Map<String, String> params) {
        return this.userRepository.getAllUserPagination(params);
    }

    @Override
    public Long countUser() {
        return this.userRepository.countUser();
    }
}
