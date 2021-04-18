package ru.stqua.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;
import ru.stqua.pft.addressbook.model.Groups;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddToGroupTest extends TestBase {

  private SessionFactory sessionFactory;

  @BeforeClass
  protected void setUpSession() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }

/*
  public ContactData ensurePreconditions() {
    boolean needToCreateGroup = true;
    ContactData contactAddGroup = null;
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> contactList = session.createQuery("from ContactData").list();
    List<GroupData> groupsList = session.createQuery("from GroupData").list();
    for (ContactData contact : contactList) {
      if (contact.getGroups().size() != groupsList.size()) {
        needToCreateGroup = false;
        contactAddGroup = contact;
        break;
      }
    }
    if (needToCreateGroup == true) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("newGroup").withHeader("newGroupHeader"));
      app.goTo().contactPage();
      Contacts c = app.db().contacts();
      contactAddGroup = c.iterator().next();
    }
    session.getTransaction().commit();
    session.close();
    return contactAddGroup;
  }*/

  @Test
  public void testContactAddToGroup() {
    boolean needToCreateGroup = true;
    ContactData contactAddGroup = null;
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> contactList = session.createQuery("from ContactData").list();
    List<GroupData> groupsList = session.createQuery("from GroupData").list();
    for (ContactData contact : contactList) {
      if (contact.getGroups().size() != groupsList.size()) {
        needToCreateGroup = false;
        contactAddGroup = contact;
        break;
      }
    }
    if (needToCreateGroup == true) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("newGroup").withHeader("newGroupHeader"));
      app.goTo().contactPage();
      Contacts c = app.db().contacts();
      contactAddGroup = c.iterator().next();
    }

    ContactData beforeContact = (ContactData) session.createQuery("from ContactData where id=" +
            contactAddGroup.getId()).getSingleResult();
    Groups beforeGroups = beforeContact.getGroups();
    System.out.println("Before " + beforeContact);
    System.out.println("groups linked before" + beforeContact.getGroups());
    session.getTransaction().commit();
    session.close();

    app.goTo().contactPage();
    app.contact().addGroup(contactAddGroup,beforeGroups);

    session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData afterContact = (ContactData) session.createQuery("from ContactData where id=" +
            contactAddGroup.getId()).getSingleResult();
    Groups afterGroups = beforeContact.getGroups();
    System.out.println("After " + afterContact);
    System.out.println("groups linked after" + afterContact.getGroups());
    session.getTransaction().commit();
    session.close();

    assertThat(afterContact, equalTo(beforeContact));
    assertThat(afterGroups.size(), equalTo(beforeGroups.size() + 1));
    verifyContactListInUI();
  }

}


