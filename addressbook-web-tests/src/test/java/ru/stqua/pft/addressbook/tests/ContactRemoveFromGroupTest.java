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
import ru.stqua.pft.addressbook.model.GroupData;
import ru.stqua.pft.addressbook.model.Groups;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTest extends TestBase {

  private SessionFactory sessionFactory;
  private ContactData contactToRemoveGroup;
  private GroupData removalGroup;

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

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }

    Session session = openSessionDb();
    List<ContactData> contactList = session.createQuery("from ContactData").list();
    List<GroupData> groupList = session.createQuery("from GroupData").list();

    for (ContactData contact : contactList) {
      if (contact.getGroups().size() != 0) {
        contactToRemoveGroup = contact;
        removalGroup = contactToRemoveGroup.getGroups().iterator().next();
        break;
      }
    }

    if (contactToRemoveGroup==null) {
      contactToRemoveGroup = contactList.iterator().next();
      removalGroup = groupList.iterator().next();
      app.goTo().homePage();
      app.contact().addContactToGroup(contactToRemoveGroup,  removalGroup);
    }
    closeSession(session);
  }

  public void closeSession(Session session) {
    session.getTransaction().commit();
    session.close();
  }

  public Session openSessionDb() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    return session;
  }

  public ContactData contactFromDbById(Session session) {
    return (ContactData) session.createQuery("from ContactData where id=" +
            contactToRemoveGroup.getId()).getSingleResult();
  }


  @Test
  public void testContactRemoveGroup() {
    Session session = openSessionDb();
    ContactData beforeContact = contactFromDbById(session);
    Groups beforeGroups = beforeContact.getGroups();
    closeSession(session);

    app.goTo().homePage();
    app.contact().removeContactFromGroup(contactToRemoveGroup, removalGroup);

    session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData afterContact = contactFromDbById(session);
    Groups afterGroups = afterContact.getGroups();
    closeSession(session);

    assertThat(afterContact, equalTo(beforeContact));
    assertThat(afterGroups.size(), equalTo(beforeGroups.size() - 1));
    verifyContactListInUI();
  }


}




