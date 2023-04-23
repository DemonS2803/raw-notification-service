package com.example.application;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.example.application.entities.Message;
import com.example.application.services.MessageDAOService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

@Service
public class MessageService {

//  @Value("${vapid.public.key}")
  private String publicKey = "BOCLxAb9XB_Xvl0kzbVwBqj7wjLZRc0EcwimrQMiONwGhMHh5r6u1QcyyTL8b6-nRAbx8XXnv5jHS1_NnifCToE";
//  @Value("${vapid.private.key}")
  private String privateKey = "Hzq9_J6wr-gqMPsK9WjZ2GZPZCz22loXTxp2EJQAWlM";

  private PushService pushService;
  private HashMap<Long, Subscription> subscriptions = new HashMap<>();
  @Autowired
  private MessageDAOService messageDAOService;

  @PostConstruct
  private void init() throws GeneralSecurityException {
    Security.addProvider(new BouncyCastleProvider());
    pushService = new PushService(publicKey, privateKey);
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void subscribe(Subscription subscription) {
    System.out.println("Subscribed to " + subscription.endpoint);
    this.subscriptions.put(629313756L, subscription);
  }

  public void unsubscribe(String endpoint) {
    System.out.println("Unsubscribed from " + endpoint);
    subscriptions.remove(629313756L);
//    subscriptions = subscriptions.stream().filter(s -> !endpoint.equals(s.endpoint))
//        .collect(Collectors.toList());
  }

  public void sendNotification(Subscription subscription, String messageJson) {
    try {
      pushService.send(new Notification(subscription, messageJson));
    } catch (GeneralSecurityException | IOException | JoseException | ExecutionException
        | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Scheduled(fixedRate = 5000)
  private void sendNotifications() {
    System.out.println("Sending notifications to all subscribers");
    System.out.println(subscriptions);
    System.out.println(messageDAOService.getAll());
    ArrayList<Message> userNewNotifications;
    for (Long chatId : subscriptions.keySet()) {
      userNewNotifications = messageDAOService.getAllNewByChatId(chatId);
      if (userNewNotifications.size() != 0) {
        for (int i = 0; i < userNewNotifications.size(); i++) {
          var json = """
                  {
                    "title": "WARNING %s",
                    "body": "%s"
                  }
                  """;

          sendNotification(subscriptions.get(chatId), String.format(json, userNewNotifications.get(i).getTitle(), userNewNotifications.get(i).getMessage()));
          messageDAOService.setMessageStatusSent(userNewNotifications.get(i).getId());
//          subscriptions.forEach(subscription -> {
//            sendNotification(subscription, String.format(json, LocalTime.now()));
//          });
        }
      }
    }

  }
}
